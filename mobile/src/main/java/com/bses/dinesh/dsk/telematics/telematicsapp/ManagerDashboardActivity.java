package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.UserLiveInfoWindowAdapter;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
/*import com.google.android.material.navigation.NavigationView;
import com.jhamobi.brplapp.adapter.UserLiveInfoWindowAdapter;
import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.remote.RetrofitInterface;
import com.jhamobi.brplapp.remote.RetrofitResponse;
import com.jhamobi.brplapp.server.AppConstants;
import com.jhamobi.brplapp.utils.LocationService;
import com.jhamobi.brplapp.utils.RouteDistanceText;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;*/

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ManagerDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    Timer fetchUserLocTimer;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Location currentLocation;
    private int GPS_CHECK_STATUS = 1001;
    private List<Users> usersList;
    private TextView tvName, tvEmail, tvEmpid, tvMobileNum, tvDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        InitMenuHeaderView();
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

    private void getUserDataList()
    {
        getUserLocation(SharedPreferenceManager.with(this).getLoggedInUser().getId());
    }

    private void checkLocation()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        long minTime = 1000;
        float minDistance = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        currentLocation = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, minTime, minDistance, (LocationListener) ManagerDashboardActivity.this);
        if (currentLocation != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(currentLocation);
        }
    }

    private void checkRequestPermissions() {
        String[] perms = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, perms, 102);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // checkLocation();
                startGPS();
            } else {
                checkRequestPermissions();
            }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_user_loc_menu) {
            startActivity(new Intent(getApplicationContext(), DivisionListActivity.class)
                    .putExtra("RequiredDetails", AppConstants.USER_LOC_MENU));
        }
        else if (id == R.id.nav_tracking_history) {
            startActivity(new Intent(getApplicationContext(), DivisionListActivity.class)
                    .putExtra("RequiredDetails", AppConstants.TRACKING_HISTORY_MENU));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
        getUserDataList();
        setUserLocMarkerData();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String strID = (String) marker.getTag();
                selectOptions(strID);

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkRequestPermissions();
            } else {
                startGPS();
            }
        } else {
            startGPS();
        }
    }

    private void setUserLocMarkerData()
    {
        if (mMap != null) mMap.clear();

        if (mMap != null && usersList != null && usersList.size() > 0)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            List<LatLng> latlngList = new ArrayList<>();
            mMap.setInfoWindowAdapter(new UserLiveInfoWindowAdapter(this, usersList)); // Added custom marker window
            for (Users users : usersList)
            {
                if  (users.getLat() == null && users.getLng() == null || users.getStatus() == null   )
                {
                    continue;
                }
                else if (users.getLat() != null && users.getLng() != null
                        && !users.getLat().equalsIgnoreCase("") && !users.getLng().equalsIgnoreCase(""))
                {
                    latlngList.add(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng())));
                    Marker marker;
                    if(users.getStatus().equalsIgnoreCase("running"))
                    {
                        marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_marker_running))
                                .position(new LatLng(Double.parseDouble(users.getLat()),
                                        Double.parseDouble(users.getLng()))));
                    }
                    else
                    {
                        marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_marker_walk))
                                .position(new LatLng(Double.parseDouble(users.getLat()),
                                        Double.parseDouble(users.getLng()))));
                    }
                    marker.setSnippet(users.getName());
                    marker.setTag(users.getId());

                    builder.include(marker.getPosition());
                   // new RouteDistanceText(this, marker);
                } else{
                    continue;
                }
            }
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.30); // offset from edges of the map 10% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
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
                            status.startResolutionForResult(ManagerDashboardActivity.this, GPS_CHECK_STATUS);
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

    private void selectOptions(final String strUserId)
    {
        final AppCompatDialog dialog = new AppCompatDialog(this);
        dialog.setContentView(R.layout.layout_dialog_user_options);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvTaskList = dialog.findViewById(R.id.tvTaskList);
        TextView tvTaskListHistory = dialog.findViewById(R.id.tvTaskListHistory);
        TextView tvTrackHistory = dialog.findViewById(R.id.tvTrackHistory);
        TextView tvCompletedTask = dialog.findViewById(R.id.tvCompletedTask);
        tvTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AllOrderListByUserActivity.class).
                        putExtra("userid", strUserId));
                dialog.cancel();
            }
        });
        tvTaskListHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryMapsActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        tvTrackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FieldUserLiveTrackingActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        tvCompletedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CompletedTasksActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void getUserLocation(final String userId)
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
        progressDoalog = new ProgressDialog(ManagerDashboardActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("Users");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getAllUserDetail(userId);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("User Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getUsersDetailList().size() != 0)
                    {
                        progressDoalog.dismiss();
                        usersList = jsonResponse.getUsersDetailList();
                        setUserLocMarkerData();
                    }
                    else
                    {
                        progressDoalog.dismiss();
                        mMap.clear();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                    }
                }
                else
                {
                    progressDoalog.dismiss();
                    mMap.clear();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}
