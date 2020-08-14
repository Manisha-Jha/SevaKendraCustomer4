package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.HistoryTasksInfoWindowAdapter;
import com.bses.dinesh.dsk.telematics.data.OrderInformation;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.interfaces.DirectionPointListener;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.utils.BackgroundLocationService;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.GPSTracker;
import com.bses.dinesh.dsk.telematics.utils.GetPathFromLocation;
import com.bses.dinesh.dsk.telematics.utils.GetSnappedLocOnRoad;
import com.bses.dinesh.dsk.telematics.utils.LiveLocationUpdate;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FieldUserDashboardActivity extends AppCompatActivity
        implements LiveLocationUpdate, NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Location currentLocation;
    private int GPS_CHECK_STATUS = 1001;
    private TextView tvName, tvEmail, tvEmpid, tvMobileNum, tvDesignation;
    private Context mContext;

    private GPSTracker gps;
    public double currentlatitude = 0;
    public double currentlongitude = 0;
    private FloatingActionButton fab;

    TelephonyManager telephonyManager;
    String imei_number;

    private static final int POLYLINE_STROKE_WIDTH_PX = 10;
    private boolean isFirst = false;
    private List<OrderInformation> TasksList;

   // private List<List<UserLocationData>> userLocLists;

    private List<UserLocationData> LocList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = FieldUserDashboardActivity.this;

        TasksList = new ArrayList<>();

        //userLocLists = new ArrayList<List<UserLocationData>>();
        LocList = new ArrayList<>();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (SharedPreferenceManager.with(mContext).isLocationStarted())
        {
            fab.setImageResource(R.drawable.ic_finish_round);
        } else {
            fab.setImageResource(R.drawable.ic_start_round);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        InitMenuHeaderView();

        SupportMapFragment mapFragment =  (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserLocation(SharedPreferenceManager.with(this).getLoggedInUser().getId(),CommonUtilities.todayDate());

        deviceId();
    }

    private void deviceId()
    {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        else
        {
            imei_number = telephonyManager.getDeviceId();

        }
    }

    private void InitMenuHeaderView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        tvName = navHeader.findViewById(R.id.tv_name);
        tvEmail = navHeader.findViewById(R.id.tv_email);
        tvEmpid = navHeader.findViewById(R.id.tv_empid);
        tvMobileNum = navHeader.findViewById(R.id.tv_phonenumber);
        tvDesignation = navHeader.findViewById(R.id.tv_designation);
        if (SharedPreferenceManager.with(this).isUserLoggedIn()) {
            if (tvName != null)
                tvName.setText(SharedPreferenceManager.with(this).getLoggedInUser().getName() + "");
            else
                tvName.setVisibility(View.GONE);

            if (tvEmail != null)
                tvEmail.setText(SharedPreferenceManager.with(this).getLoggedInUser().getUserEmailId() + "");
            else
                tvEmail.setVisibility(View.GONE);

            if (tvEmpid != null)
                tvEmpid.setText(SharedPreferenceManager.with(this).getLoggedInUser().getId() + "");
            else
                tvEmpid.setVisibility(View.GONE);

            if (tvMobileNum != null)
                tvMobileNum.setText(SharedPreferenceManager.with(this).getLoggedInUser().getPhoneNum() + "");
            else
                tvMobileNum.setVisibility(View.GONE);

            if (tvDesignation != null)
                tvDesignation.setText(SharedPreferenceManager.with(this).getLoggedInUser().getUserDesignation() + "");
            else
                tvDesignation.setVisibility(View.GONE);
        }
    }


    private void setPolylineData1()
    {
        if (mMap != null) mMap.clear();

        if (mMap != null && LocList != null && LocList.size() > 0)
        {
            List<LatLng> latlngList = new ArrayList<>();

            int position = 0;
            for (UserLocationData tasksData : LocList)
            {
                if (tasksData.getLat() != null && tasksData.getLng() != null
                        && !tasksData.getLat().equalsIgnoreCase("")
                        && !tasksData.getLng().equalsIgnoreCase("")
                        && (tasksData.getStatus().equals("start")
                        || tasksData.getStatus().equals("running")))
                {
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                            Double.parseDouble(tasksData.getLng())));
                    position++;
                    if(position==LocList.size())
                    {
                        latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                                Double.parseDouble(tasksData.getLng())));
                        setPolylineFromLatLng(latlngList);
                        latlngList.clear();
                    }
                }
                else if(tasksData.getStatus().equals("finish"))
                {
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                            Double.parseDouble(tasksData.getLng())));
                    setPolylineFromLatLng(latlngList);
                    latlngList.clear();
                    position++;
                }
            }
        }
        else
        {
            Log.e("TAG Size","No data");
        }
    }

    private void setPolylineFromLatLng(List<LatLng> latlngList)
    {
        String waypoints = "";
        if(latlngList.size()>10)
        {
            for(int i=2;i<latlngList.size();i++)
            {
                LatLng point  = latlngList.get(i);
                if(i==2)
                    waypoints = "waypoints=";
                waypoints += point.latitude + "," + point.longitude + "|";
            }
        }
        new GetPathFromLocation(latlngList.get(0), latlngList.get(latlngList.size()-1),waypoints,
                new DirectionPointListener()
                {
                    @Override
                    public void onPath(PolylineOptions polyLine)
                    {
                        mMap.addPolyline(polyLine);
                    }
                }).execute();

/*
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions().clickable(true).geodesic(true).addAll(latlngList));

        if (latlngList != null && latlngList.size() > 0)
        {
            if (!isFirst)
            {
                if (latlngList.size() > 2)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(Math.round(latlngList.size() / 2)), 16));
                else
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 10));
            }
            isFirst = true;
        }

        polyline1.setEndCap(new RoundCap());
        polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline1.setColor(Color.RED);
        polyline1.setJointType(JointType.ROUND);
        polyline1.setGeodesic(true);
*/

        String distance = "Distance: " + CommonUtilities.getKmFromLatLongList(latlngList) + "km";

        try{
            for(int i=0;i<LocList.size();i++)
            {
                if(LocList.get(i).getStatus().equalsIgnoreCase("start"))
                {        // Set Start Location
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_start))
                            .position(latlngList.get(0))
                            .title("START"));
                }
                else if(LocList.get(i).getStatus().equalsIgnoreCase("finish"))
                {
                    // Set End Location
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_end))
                            .position(latlngList.get(latlngList.size()-1))
                            .title("END")
                            .snippet(distance));
                }
            }
        }catch (Exception e){}
    }

    private void setMarkerData()
    {
        if (mMap != null && TasksList != null && TasksList.size() > 0)
        {
            mMap.setInfoWindowAdapter(new HistoryTasksInfoWindowAdapter(this, TasksList)); // Added custom marker window
            for (OrderInformation tasksData : TasksList)
            {
                if (tasksData.getORDER_SUBMIT_LAT() != null && tasksData.getORDER_SUBMIT_LNG() != null &&
                        !tasksData.getORDER_SUBMIT_LAT().equalsIgnoreCase("") &&
                        !tasksData.getORDER_SUBMIT_LNG().equalsIgnoreCase(""))
                {
                    GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(
                            tasksData.getORDER_SUBMIT_LAT() + "," + tasksData.getORDER_SUBMIT_LNG());
                    HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
                    String snappedLatLongi = snappedLocs.get("0");
                    String snappedLati = null;
                    String snappedLongi = null;
                    if (snappedLatLongi != null)
                    {
                        snappedLati = snappedLatLongi.split(",")[0];
                        snappedLongi = snappedLatLongi.split(",")[1];

                        if (snappedLati.equals(null) && snappedLongi.equals(null))
                        {
                            snappedLati = tasksData.getORDER_SUBMIT_LAT();
                            snappedLongi = tasksData.getORDER_SUBMIT_LNG();
                        }
                    }
                    else
                    {
                        snappedLati = tasksData.getORDER_SUBMIT_LAT();
                        snappedLongi = tasksData.getORDER_SUBMIT_LNG();
                    }

                    String strAddress = CommonUtilities.getAddressStringFromLatLng(this,
                            Double.parseDouble(snappedLati),
                            Double.parseDouble(snappedLongi));


                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.ic_marker_done))
                            .position(new LatLng(Double.parseDouble(snappedLati),
                                    Double.parseDouble(snappedLongi)))
                            .title(tasksData.getORDER_NO())
                            .snippet(tasksData.getCUSTOMER_ADDRESS() + " (" + strAddress + " )"))
                            .setTag(tasksData.getORDER_NO());
                }
            }
        }
        else
        {
            Log.e("TAG","Tasklist size "+TasksList.size());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();//method call
        setUserData();
        if (SharedPreferenceManager.with(mContext).isLocationStarted())
        {
            fab.setImageResource(R.drawable.ic_finish_round);
        } else {
            fab.setImageResource(R.drawable.ic_start_round);
        }

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (SharedPreferenceManager.with(mContext).isLocationStarted())//already started
                {
                    Toast.makeText(mContext, "Stopped", Toast.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.ic_start_round);
                    SharedPreferenceManager.with(mContext).setLocationStarted(false);
                    insertUserStatus("finish");
                    recreate();
                }
                else//Need to start
                {
                    Toast.makeText(mContext, "Started", Toast.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.ic_finish_round);
                    SharedPreferenceManager.with(mContext).setLocationStarted(true);
                    insertUserStatus("start");
                    recreate();
                }
            }
        });
    }

    private void getCurrentLocation()
    {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            currentlatitude = gps.getLatitude();
            currentlongitude = gps.getLongitude();

            Log.e("Lat",currentlatitude+"");
            Log.e("Lng",currentlongitude+"");

        } else {
            gps.showSettingsAlert();
        }
    }

    private void setUserData() {
        if (SharedPreferenceManager.with(this).isUserLoggedIn()) {
            if (tvName != null)
                tvName.setText(SharedPreferenceManager.with(this).getLoggedInUser().getName() + "");
            if (tvEmail != null)
                tvEmail.setText(SharedPreferenceManager.with(this).getLoggedInUser().getUserEmailId() + "");
        }
    }

    private void checkLocation() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        long minTime = 1000;
        float minDistance = 1;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        currentLocation = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, minTime, minDistance, (LocationListener) FieldUserDashboardActivity.this);
        // Initialize the location fields
        if (currentLocation != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(currentLocation);
        }

        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mMap.getMyLocation()));
        if (mMap.getMyLocation() != null)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 13));
        else if (currentLocation != null)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));

        startLocationService();
    }

    private void checkRequestPermissions() {
        String[] perms = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, perms, 102);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    imei_number = telephonyManager.getDeviceId();
                    //Toast.makeText(FieldUserDashboardActivity.this,imeiNumber,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FieldUserDashboardActivity.this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            case 102:
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // checkLocation();
                    startGPS();
                } else {
                    //checkRequestPermissions();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

       /* if (id == R.id.nav_tasks) {
            startActivity(new Intent(getApplicationContext(), OrderListByFieldUserActivity.class)
                    .putExtra("userid", SharedPreferenceManager.with(this).getLoggedInUser().getId()));
        } else */if (id == R.id.nav_history) {
            startActivity(new Intent(getApplicationContext(), HistoryMapsActivity.class)
                    .putExtra("userid", SharedPreferenceManager.with(this).getLoggedInUser().getId())
                    .putExtra("admin", "admin"));
        } /*else if (id == R.id.nav_completed) {
            startActivity(new Intent(getApplicationContext(), CompletedTasksActivity.class)
                    .putExtra("userid", SharedPreferenceManager.with(this).getLoggedInUser().getId())
                    .putExtra("admin", "admin"));
        }
        else if (id == R.id.nav_testing) {
            startActivity(new Intent(getApplicationContext(), OrderTestingActivity.class)
                    .putExtra("userid", SharedPreferenceManager.with(this).getLoggedInUser().getId())
                    .putExtra("admin", "admin"));
        }*/
       /* else if (id == R.id.nav_logout) {
            SharedPreferenceManager.with(this).deletePref();
            finish();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5946, 78.3391), 5));
        mMap.setMyLocationEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkRequestPermissions();
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkRequestPermissions();
            } else {
                //   checkLocation();
                startGPS();
            }
        } else {
            // checkLocation();
            startGPS();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        if (location != null) {
//            if (currentPosMarker != null) {
//                currentPosMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
//            } else {
//                currentPosMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Current Position"));
//            }
//        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void setOnGPS() {
        // if (mGoogleApiClient == null) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).setAlwaysShow(true);


        // **************************
        builder.setAlwaysShow(true); // this is the key ingredient
        // **************************

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result
                        .getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here.
                        checkLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            // status.startResolutionForResult(DrawermenuActivity.this, 1000);
                            status.startResolutionForResult(FieldUserDashboardActivity.this, GPS_CHECK_STATUS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        startGPS();
                        break;
                }
            }
        });
        // }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void startGPS() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setOnGPS();
        } else {
            checkLocation();
        }
    }

    private void startLocationService() {
        Intent locIntent = new Intent(FieldUserDashboardActivity.this, BackgroundLocationService.class);
        startService(locIntent);
    }

    @Override
    public void onLocation(Location location) {

    }

    @Override
    protected void onRestart() {
        //if (currentLocation != null && mMap!=null)
        //    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));

        super.onRestart();
    }

    private void insertUserStatus(String status)
    {
        if(status.equalsIgnoreCase("finish"))
        {
            GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(
                    currentlatitude + "," + currentlongitude);
            HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
            String snappedLatLongi = snappedLocs.get("0");
            String snappedLati = null;
            String snappedLongi = null;
            if (snappedLatLongi != null)
            {
                snappedLati = snappedLatLongi.split(",")[0];
                snappedLongi = snappedLatLongi.split(",")[1];

                if (snappedLati.equals(null) && snappedLongi.equals(null))
                {
                    snappedLati = String.valueOf(currentlatitude);
                    snappedLongi = String.valueOf(currentlongitude);
                }
            }
            else
            {
                snappedLati = String.valueOf(currentlatitude);
                snappedLongi = String.valueOf(currentlongitude);
            }

            insertUserLocation(SharedPreferenceManager.with(this).getLoggedInUser().getId(),
                    snappedLati, snappedLongi, imei_number,status,
                    String.valueOf(System.currentTimeMillis()), CommonUtilities.todayDate(),
                    CommonUtilities.currentTime());

        }
        else if(status.equalsIgnoreCase("start"))
        {
            insertUserLocation(SharedPreferenceManager.with(this).getLoggedInUser().getId(), String.valueOf(currentlatitude),
                    String.valueOf(currentlongitude), imei_number,status,String.valueOf(System.currentTimeMillis()),
                    CommonUtilities.todayDate(), CommonUtilities.currentTime());
        }
    }

    private void insertUserLocation(String user_id,String lat, String lng,String imei_no,String status,String time_stamp,
                                    String entry_date, String entry_time)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://115.249.67.72:9880/DSK_telematic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.insertUserLocation(user_id,lat,lng,imei_no,status,
                time_stamp,entry_date,entry_time);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("INSERT",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.Key.equals("Success"))
                    {
                        Toast.makeText(FieldUserDashboardActivity.this, jsonResponse.Key, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(FieldUserDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(FieldUserDashboardActivity.this, "Server down", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }


    private void getUserLocation(final String userId, final String date)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://115.249.67.72:9880/DSK_telematic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(FieldUserDashboardActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("User History");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getUserLocationDateBased(userId,date);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("User Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    List<UserLocationData> userLocs = jsonResponse.getUserLocationBasedOnDate();
                    if(userLocs.size() != 0)
                    {
                        LocList = jsonResponse.getUserLocationBasedOnDate();
                        setPolylineData1();
                    }
                    else
                    {
                        mMap.clear();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                    }
                }
                else
                {
                    mMap.clear();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                    Toast.makeText(FieldUserDashboardActivity.this,"Server down",Toast.LENGTH_SHORT).show();
                }
                getUserOrderLocation(userId,date,progressDoalog);
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }

    private void getUserOrderLocation(String userId, String date,ProgressDialog progressDialog)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://115.249.67.72:9880/DSK_telematic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getOrderLocationDetail(userId,date);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("Order Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getUser_loc().size() != 0)
                    {
                        progressDialog.dismiss();
                        TasksList = jsonResponse.getUser_loc();
                        setMarkerData();
                    }
                    else
                    {
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(FieldUserDashboardActivity.this, "Server down", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDialog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}
