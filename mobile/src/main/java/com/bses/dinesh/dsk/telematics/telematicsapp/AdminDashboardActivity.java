package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;




import com.bses.dinesh.dsk.R;

import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.LocationService;
import com.bses.dinesh.dsk.telematics.utils.RouteDistanceText;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;


import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AdminDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Location currentLocation;
    private int GPS_CHECK_STATUS = 1001;
    private List<Users> usersList;
    private TextView tvName, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                AdminDashboardActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
        if (SharedPreferenceManager.with(this).isUserLoggedIn()) {
            if (tvName != null)
                tvName.setText(SharedPreferenceManager.with(this).getLoggedInUser().getName() + "");
            if (tvEmail != null)
                tvEmail.setText(SharedPreferenceManager.with(this).getLoggedInUser().getUserEmailId() + "");
        }
    }


    private void getUserDataList() {

//        FetchUserLocTimerTask myTask = new FetchUserLocTimerTask();
//        fetchUserLocTimer = new Timer();
//
//        fetchUserLocTimer.schedule(myTask, 0, AppConstants.TWO_MINUTES);
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
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        locationManager.requestLocationUpdates(provider, minTime, minDistance, (LocationListener) AdminDashboardActivity.this);
        // Initialize the location fields
        if (currentLocation != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(currentLocation);
        }

        // startLocationService();
    }

    private void checkRequestPermissions() {
        String[] perms = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, perms, 102);
        //Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_user_list) {
            Intent i = new Intent(this, DivisionListActivity.class);
            i.putExtra("RequiredDetails", AppConstants.USER_LIST_MENU);
            startActivity(i);
        } else if (id == R.id.nav_orders_requests) {
            Intent i = new Intent(this, DivisionListActivity.class);
            i.putExtra("RequiredDetails", AppConstants.ORDER_MENU);
            startActivity(i);
        } else*/ if (id == R.id.nav_user_loc_menu) {
            // Handle the camera action
            Intent i = new Intent(this, DivisionListActivity.class);
            i.putExtra("RequiredDetails", AppConstants.USER_LOC_MENU);
            startActivity(i);

        } /*else if (id == R.id.nav_logout) {
            SharedPreferenceManager.with(this).deletePref();
            finish();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937, 78.9629), 5));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
        getUserDataList();
        setUserLocMarkerData();


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String strID = (String) marker.getTag();
//                Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);
//                String title = marker.getTitle();
//                intent1.putExtra("userid", strID);
//                startActivity(intent1);
                selectOptions(strID);

            }
        });
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

    private void setUserLocMarkerData() {
        if (mMap != null) mMap.clear();

        if (mMap != null && usersList != null && usersList.size() > 0) {
            List<LatLng> latlngList = new ArrayList<>();
            for (Users users : usersList) {
                if (users.getLat() != null && users.getLng() != null && !users.getLat().equalsIgnoreCase("") && !users.getLng().equalsIgnoreCase("")) {
                    latlngList.add(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng())));
                    // mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_walk)).position(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng()))).title(users.getName())).setTag(users.getId());
                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_marker_walk)).position(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng()))));
                    marker.setTitle(users.getName());
                    marker.setTag(users.getId());
                    new RouteDistanceText(this, marker);
                }
            }
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
                            status.startResolutionForResult(AdminDashboardActivity.this, GPS_CHECK_STATUS);
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

    private boolean isReadStorageAllowed() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return
                FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        SecondPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void startLocationService() {
        Intent locIntent = new Intent(AdminDashboardActivity.this, LocationService.class);
        startService(locIntent);

    }

    private void selectOptions(final String strUserId) {
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

                startActivity(new Intent(getApplicationContext(), OrderListByDivisionActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
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
                startActivity(new Intent(getApplicationContext(), MapsActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
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

    // In this class you'd define an instance of your `AsyncTask`
    class FetchUserLocTimerTask extends TimerTask {
        FetchUserLocAsyncTask mFetchUserLocAsyncTask;

        public void run() {
            mFetchUserLocAsyncTask = new FetchUserLocAsyncTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserLocAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserLocAsyncTask.execute((Void) null);
        }

        final class FetchUserLocAsyncTask extends AsyncTask<Void, Void, Boolean> {

            public FetchUserLocAsyncTask() {
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                Boolean status = false;
                try {
                    AppClient client = AppClient.getAppClient(getApplicationContext());
                    usersList = client.getAllUserDetailByDivision("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return status;
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                mFetchUserLocAsyncTask = null;
                setUserLocMarkerData();
            }

            @Override
            protected void onCancelled() {
                mFetchUserLocAsyncTask = null;
            }
        }
    }

}
