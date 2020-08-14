package com.bses.dinesh.dsk.telematics.utils;

/**
 * Created by MUDHSA-CONT on 12/5/2017.
 */

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_FIELD_LOCATION;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_USERS;

/*import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_FIELD_LOCATION;
import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_USERS;*/

public class LocationService extends Service implements LocationListener {
    public static MyTimer timer;
    Location locationUser;
    private String provider;
    private LiveLocationUpdate locationUpdate;
    private DatabaseReference mFirebaseDatabase, userDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    public LocationService(LiveLocationUpdate locationUpdate) {
        this.locationUpdate = locationUpdate;
    }

    public LocationService() {
    }
    // private PreferenceHelper preferenceHelper;

    public static void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        // Toast.makeText(this, "Tracking Started", Toast.LENGTH_LONG).show();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        //location = locationManager.getLastKnownLocation(provider);
        timer = new MyTimer(60000, 30000);
        timer.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return START_NOT_STICKY;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 15, this);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 15, this);
        }
        /*else{
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 25, this);
        }*/

        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, this);

        // Get the location manager
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "Repeat After 10 Sec", Toast.LENGTH_LONG).show();

       /* LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        // Get the location manager
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
         location = locationManager.getLastKnownLocation(provider);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        timer = new MyTimer(20000, 10000);
        timer.start();*/

    }

    @Override
    public void onLocationChanged(Location location) {
        //  Toast.makeText(this,"Location changed"+location,Toast.LENGTH_SHORT).show();
        this.locationUser = location;
        if (location != null) {
            SharedPreferenceManager.with(this).setLatitude(location.getLatitude() + "");
            SharedPreferenceManager.with(this).setLongitude(location.getLongitude() + "");
            //if (locationUpdate!=null)
            //locationUpdate.onLocation(locationUser);

            /*if (SharedPreferenceManager.with(getApplicationContext()).isUserLoggedIn() && SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUsertype().equalsIgnoreCase("team"))
                updateUser();*/
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void addLocationJSONObject() {
        if (locationUser != null) {
            sendLocation();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        // Toast.makeText(getApplicationContext(),"Destroy called",Toast.LENGTH_LONG).show();
    }

    public void sendLocation() {
        // updateUser();
    }

    private void updateUser() {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        String time = DateFormat.format("HH:mm:ss", cal).toString();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        //mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        /*
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                //iterating through all the nodes
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    Users users = postSnapshot12.getValue(Users.class);
                    if (SharedPreferenceManager.with(getApplicationContext()).isUserLoggedIn() && users.getUserEmailId().equalsIgnoreCase(SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUserEmailId())){
                        //updateUserLatLng(postSnapshot12.getKey());
                        mFirebaseDatabase.child(postSnapshot12.getKey()).child("lat").setValue(locationUser.getLatitude()+"");
                        mFirebaseDatabase.child(postSnapshot12.getKey()).child("lng").setValue(locationUser.getLongitude()+"");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_FIELD_LOCATION);
        //String key = mFirebaseDatabase.push().getKey();
        Map map = new HashMap();
        map.put("lat", SharedPreferenceManager.with(this).getLatitude());
        map.put("lng", SharedPreferenceManager.with(this).getLongitude());
        map.put("userid", SharedPreferenceManager.with(this).getLoggedInUser().getId());
        map.put("timestamp", ServerValue.TIMESTAMP);
        map.put("date", date);
        map.put("time", date);
        mFirebaseDatabase.push().setValue(map);

        updateUserLatLng();
    }

    private void updateUserLatLng() {
        userDatabaseReference = mFirebaseInstance.getReference(TABLE_USERS);
        // if (SharedPreferenceManager.with(getApplicationContext()).isUserLoggedIn())
        // updating the user via child nodes
        userDatabaseReference.orderByChild("username").equalTo(SharedPreferenceManager.with(this).getLoggedInUser().getUserEmailId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    Log.e("===postSnapshot12====", "=====postSnapshot12======" + postSnapshot12.toString());
                    //Users users = postSnapshot12.getValue(Users.class);
                    //if (SharedPreferenceManager.with(getApplicationContext()).isUserLoggedIn() && users.getUserEmailId().equalsIgnoreCase(SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUserEmailId())) {
                    userDatabaseReference.child(postSnapshot12.getKey()).child("lat").setValue(locationUser.getLatitude() + "");
                    userDatabaseReference.child(postSnapshot12.getKey()).child("lng").setValue(locationUser.getLongitude() + "");
                    break;
                    // }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUserLatLng(String key) {
        // updating the user via child nodes
        mFirebaseDatabase.child(key).child("lat").setValue("18.6322");
        mFirebaseDatabase.child(key).child("lng").setValue("73.8435");
    }

    class MyTimer extends CountDownTimer {

        // constructor for timer class
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        // this method called when timer is finished
        @Override
        public void onFinish() {
            // Toast.makeText(getApplicationContext(), "Timer finished", Toast.LENGTH_LONG).show();
            timer.start();
        }

        // this method is called for every iteration of time interval
        @Override
        public void onTick(long millisUntilFinished) {
            //display toast here
            //Toast.makeText(getApplicationContext(),""+locationVehicle,Toast.LENGTH_SHORT).show();
            // saveTripCoordinates();
            if (locationUser != null) {
                addLocationJSONObject();
                // Toast.makeText(getApplicationContext(), "Timer ticked"+locationUser, Toast.LENGTH_LONG).show();
            }
        }
    }

}
