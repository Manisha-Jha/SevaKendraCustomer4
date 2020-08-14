package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;


import androidx.appcompat.app.AppCompatActivity;

import com.bses.dinesh.dsk.R;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        CountDownTimer downTimer = new CountDownTimer(3000, 500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                try {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        downTimer.start();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onBackPressed();
    }
}
