package com.bses.dinesh.dsk.telematics.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.telematicsapp.LoginActivity;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
/*import com.google.gson.reflect.TypeToken;
import com.jhamobi.brplapp.LoginActivity;
import com.jhamobi.brplapp.data.Users;*/

import java.lang.reflect.Type;

public class SharedPreferenceManager {
    private static SharedPreferenceManager INSTANCE;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunchBRPL";

    /**
     * Private constructor for instantiating the singleton.
     */
    private SharedPreferenceManager(Context mContext) {
        //It is important to store the application context
        //in order to avoid memory leaks.
        this.mContext = mContext.getApplicationContext();
        this.sharedPreferences = mContext.getSharedPreferences(Constant.Common.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public static SharedPreferenceManager with(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferenceManager(mContext);
        }
        return INSTANCE;
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, false);
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        editor.putBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, isUserLoggedIn);
        editor.commit();
    }

    public void updateLoggedInUser(Users user) {
        if (user == null) {
            editor.remove(Constant.SharedPreferences.LOGGED_IN_USER);
        } else {
            editor.putString(Constant.SharedPreferences.LOGGED_IN_USER, new Gson().toJson(user));
        }
        editor.commit();
    }

    public Users getLoggedInUser() {
        String json = sharedPreferences.getString(Constant.SharedPreferences.LOGGED_IN_USER, null);
        if (json == null) {
            return null;
        }

        Type objType = new TypeToken<Users>() {
        }.getType();
        Users user = new Gson().fromJson(json, objType);
        return user;
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public String getLatitude() {
        return sharedPreferences.getString(Constant.SharedPreferences.USER_LOGGED_IN_LAT, "");
    }

    public void setLatitude(String strLat) {
        editor.putString(Constant.SharedPreferences.USER_LOGGED_IN_LAT, strLat);
        editor.commit();
    }

    public String getLongitude() {
        return sharedPreferences.getString(Constant.SharedPreferences.USER_LOGGED_IN_LONG, "");
    }

    public void setLongitude(String strLng) {
        editor.putString(Constant.SharedPreferences.USER_LOGGED_IN_LONG, strLng);
        editor.commit();
    }

    public void deletePref()
    {
        boolean status = sharedPreferences.getBoolean(Constant.SharedPreferences.IS_USER_LOCATION_STARTED, false);

        sharedPreferences.edit().clear().commit();

        SharedPreferenceManager.with(mContext).setLocationStarted(status);

        Intent logOut = new Intent(mContext, LoginActivity.class);
        logOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(logOut);
    }

//    // Added by SM
//    public void setTimeForAlertMobile(long value) {
//        editor.putLong(Constant.SharedPreferences.ALERT_ADD_MOBILE_NUMBER, value);
//        editor.commit();
//    }
//
//    // Added by SM
//    public long getTimeForAlertMobile() {
//        return sharedPreferences.getLong(Constant.SharedPreferences.ALERT_ADD_MOBILE_NUMBER, 0);
//    }

    public boolean isLocationStarted() {
        return sharedPreferences.getBoolean(Constant.SharedPreferences.IS_USER_LOCATION_STARTED, false);
    }

    public void setLocationStarted(boolean isUserLoggedIn) {
        editor.putBoolean(Constant.SharedPreferences.IS_USER_LOCATION_STARTED, isUserLoggedIn);
        editor.commit();
    }

}
