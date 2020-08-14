package com.bses.dinesh.dsk.sevakendra;

/**
 * Created by Krishna on 4/28/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.utils.UserPreferences;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private int SPLASH_DISPLAY_LENGTH = 2000;
    Animation animFadein;
    UserPreferences pref;
    private Intent mainIntent;
    private TextView splashTitle;

    public static final int RequestPermissionCode = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        pref = UserPreferences.getInstance(SplashScreen.this);



        if (Build.VERSION.SDK_INT >= 23){

            if(checkPermission()){
                Toast.makeText(SplashScreen.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

                CallHandler();
            }
            else {
                requestPermission();
            }
        }else{
            CallHandler();
        }

    }

    private void CallHandler() {

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

               if(pref.getUserid()!=null && pref.getPassword()!=null && pref.getUser_role()!=null){

                  /*  if(db.getRowsCount()>0) {
                        if(!ApplicationUtil.getInstance().isMyServiceRunning(MyService.class, SplashScreen.this)){
                            SplashScreen.this.startService(new Intent(SplashScreen.this, MyService.class));
                        }
                        startService(new Intent(SplashScreen.this, MyService.class));
                    }
                    else {
                        //stopping service
                        stopService(new Intent(SplashScreen.this, MyService.class));
                    }

                    if (pref.getUser_role().equalsIgnoreCase("I")) {
                        //Navigate to Home Screen
                        mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                        mainIntent.putExtra("empType", "admin");
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }

                    else {
                        mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                        mainIntent.putExtra("empType", "employee");
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }*/
                   mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                   mainIntent.putExtra("empType", "admin");
                   SplashScreen.this.startActivity(mainIntent);
                   SplashScreen.this.finish();

                }

                else{

                    mainIntent = new Intent(SplashScreen.this,LoginScreen.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }




            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(SplashScreen.this, new String[]
                {
                        CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        READ_PHONE_STATE,
                        ACCESS_FINE_LOCATION,
                        WRITE_CONTACTS
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean PhoneStorageStatePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessLocationPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadContactsPermission && ReadPhoneStatePermission && PhoneStorageStatePermission && AccessLocationPermission) {

                        Toast.makeText(SplashScreen.this, "Permission Granted", Toast.LENGTH_LONG).show();

                        CallHandler();
                    }
                    else {
                        Toast.makeText(SplashScreen.this,"Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int FourPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int FivePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FivePermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }



   /* @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // check for fade in animation
        if (animation == animFadein) {
            *//*Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();*//*
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }*/
}