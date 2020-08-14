package com.bses.dinesh.dsk.telematics.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*import com.jhamobi.brplapp.remote.RetrofitInterface;
import com.jhamobi.brplapp.remote.RetrofitResponse;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.server.AppConstants;*/

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bses.dinesh.dsk.telematics.utils.Constant.BackgroundService.FASTEST_INTERVAL;
import static com.bses.dinesh.dsk.telematics.utils.Constant.BackgroundService.UPDATE_INTERVAL;

public class BackgroundLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    IBinder mBinder = new LocalBinder();
    private GoogleApiClient mGoogleApiClient;
    private PowerManager.WakeLock mWakeLock;
    private LocationRequest mLocationRequest;
    private boolean mInProgress;
    private Boolean servicesAvailable = false;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInProgress = false;
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        servicesAvailable = servicesConnected();
        setUpLocationClientIfNeeded();
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {

            return true;
        } else {

            return false;
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        PowerManager mgr = (PowerManager) getSystemService(Context.POWER_SERVICE);

        /*
        WakeLock is reference counted so we don't want to create multiple WakeLocks. So do a check before initializing and acquiring.

        This will fix the "java.lang.Exception: WakeLock finalized while still held: MyWakeLock" error that you may find.
        */
        if (this.mWakeLock == null) { //**Added this
            this.mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        }

        if (!this.mWakeLock.isHeld()) { //**Added this
            this.mWakeLock.acquire();
        }

        if (!servicesAvailable || mGoogleApiClient.isConnected() || mInProgress)
            return START_STICKY;

        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting() && !mInProgress) {
            mInProgress = true;
            mGoogleApiClient.connect();
        }

        return START_STICKY;
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            buildGoogleApiClient();
    }

    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location)
    {
        // Report to the UI that the location was updated
        String msg = Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Log.d("debug", msg);
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (location != null) {
            boolean isUserLoggedIn = SharedPreferenceManager.with(getApplicationContext()).isUserLoggedIn();
            boolean isFieldUser = SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUserRoleID().equalsIgnoreCase(AppConstants.FIELD_USER_ROLE);

            if (isUserLoggedIn && isFieldUser)
            {
                SharedPreferenceManager.with(this).setLatitude(location.getLatitude() + "");
                SharedPreferenceManager.with(this).setLongitude(location.getLongitude() + "");
                if (SharedPreferenceManager.with(this).isLocationStarted())
                    updateUser();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        // Turn off the request flag
        this.mInProgress = false;

        if (this.servicesAvailable && this.mGoogleApiClient != null) {
            this.mGoogleApiClient.unregisterConnectionCallbacks(this);
            this.mGoogleApiClient.unregisterConnectionFailedListener(this);
            this.mGoogleApiClient.disconnect();
            // Destroy the current location client
            this.mGoogleApiClient = null;
        }
        // Display the connection status
        // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ":
        // Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();

        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }

        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient,
                mLocationRequest, (com.google.android.gms.location.LocationListener) this); // This is the changed line.

    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        // Turn off the request flag
        mInProgress = false;
        // Destroy the current location client
        mGoogleApiClient = null;
        // Display the connection status
        // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ": Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            // If no resolution is available, display an error dialog
        } else {

        }
    }

    private void updateUser()
    {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        String imeiNumber = telephonyManager.getDeviceId();

        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        String time = DateFormat.format("HH:mm:ss", cal).toString();

        String originalLat = SharedPreferenceManager.with(this).getLatitude();
        String originalLongi = SharedPreferenceManager.with(this).getLongitude();
        GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(originalLat + "," + originalLongi);
        HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
        String snappedLatLongi = snappedLocs.get("0");
        String snappedLati = null;
        String snappedLongi = null;
        if (snappedLatLongi != null)
        {
            snappedLati = snappedLatLongi.split(",")[0];
            snappedLongi = snappedLatLongi.split(",")[1];

            Log.e("LaT:",snappedLati);
            Log.e("LnG:",snappedLongi);

            if (snappedLati.equals(null) && snappedLongi.equals(null))
            {
                snappedLati = originalLat;
                snappedLongi = originalLongi;

                Log.e("S-Lat:",snappedLati);
                Log.e("S-Lng:",snappedLongi);
            }
        }
        else
        {
            snappedLati = SharedPreferenceManager.with(this).getLatitude();
            snappedLongi = SharedPreferenceManager.with(this).getLongitude();
            Log.e("S1-Lat:",snappedLati);
            Log.e("S1-Lng:",snappedLongi);
        }
        updateFieldLocationData(SharedPreferenceManager.with(this).getLoggedInUser().getId(), snappedLati, snappedLongi, imeiNumber);
    }

    private void updateFieldLocationData(final String userID, final String lat, final String lng, final String imeiNumber)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    insertUserLocation(userID, lat, lng,
                            imeiNumber,"running",String.valueOf(System.currentTimeMillis()),
                            CommonUtilities.todayDate(),CommonUtilities.currentTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public class LocalBinder extends Binder {
        public BackgroundLocationService getServerInstance() {
            return BackgroundLocationService.this;
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
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}