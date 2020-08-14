package com.bses.dinesh.dsk.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by krishna on 17/12/15.
 */
public class UserPreferences {

    private static UserPreferences instance;
    private SharedPreferences preferences;
    String isLogoutfromApp="NO";
    boolean isLocalDatacacheSaved;
    boolean isFbLoggedin;
    boolean isGooglePlusloggedin;


    private UserPreferences(Context context) {
        if (context != null) {
            preferences = context.getSharedPreferences(
                    "user_prefrences", Context.MODE_PRIVATE);
        }
    }
    public static synchronized UserPreferences getInstance(Context context) {
        return instance==null?instance = new UserPreferences(context):instance;
    }


    public synchronized String getModuleCode() {
        return preferences.getString(ApplicationConstants.DEVICE_FCM_ID, null);
    }
    public synchronized void setModuleCode(String device_fcm_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DEVICE_FCM_ID, device_fcm_id);
        editor.commit();
    }


    public synchronized String getDevice_imei_id() {
        return preferences.getString(ApplicationConstants.DEVICE_IMEI_ID, null);
    }

    public synchronized void setDevice_imei_id(String device_imei_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DEVICE_IMEI_ID, device_imei_id);
        editor.commit();
    }



    public synchronized String getCompCode(){
        return preferences.getString(ApplicationConstants.USER_ADD,null);
    }
    public synchronized void setCompCode(String add){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.USER_ADD, add);
        editor.commit();
    }




    public synchronized String getFirst_name(){
        return preferences.getString(ApplicationConstants.NAME,null);
    }
    public synchronized void setFirst_name(String first_name){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.NAME, first_name);
        editor.commit();
    }


    public synchronized String getCANumber(){
        return preferences.getString(ApplicationConstants.CANUM,null);
    }
    public synchronized void setCANumber(String CA){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANUM, CA);
        editor.commit();
    }



    public synchronized String getProfile_image(){
        return preferences.getString(ApplicationConstants.PROFILE_IMAGE,null);
    }
    public synchronized void setProfile_image(String profile_image){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PROFILE_IMAGE, profile_image);
        editor.commit();
    }



    public synchronized String getMobile(){
        return preferences.getString(ApplicationConstants.MOBILE,null);
    }
    public synchronized void setMobile(String mobile){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.MOBILE, mobile);
        editor.commit();
    }




    public synchronized String getEmail(){
        return preferences.getString(ApplicationConstants.EMAIL,null);
    }
    public synchronized void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.EMAIL, email);
        editor.commit();
    }




    public synchronized String getPassword(){
        return preferences.getString(ApplicationConstants.PASSWORD,null);
    }
    public synchronized void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PASSWORD, password);
        editor.commit();
    }




    public synchronized String getUser_role(){
        return preferences.getString(ApplicationConstants.USER_ROLE,null);
    }
    public synchronized void setUser_role(String user_role){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.USER_ROLE, user_role);
        editor.commit();
    }



    public synchronized String getCurrent_login(){
        return preferences.getString(ApplicationConstants.CURRENT_LOGIN,null);
    }
    public synchronized void setCurrent_login(String current_login){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CURRENT_LOGIN, current_login);
        editor.commit();
    }


    public synchronized String getDivision(){
        return preferences.getString(ApplicationConstants.DIVISION,null);
    }
    public synchronized void setDivision(String division){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DIVISION, division);
        editor.commit();
    }



    public synchronized String getLast_login(){
        return preferences.getString(ApplicationConstants.LAST_LOGIN,null);
    }
    public synchronized void setLast_login(String last_login){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.LAST_LOGIN, last_login);
        editor.commit();
    }




    public synchronized String getStatus(){
        return preferences.getString(ApplicationConstants.STATUS,null);
    }
    public synchronized void setStatus(String status){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.STATUS, status);
        editor.commit();
    }




    public synchronized String getRoleDesc(){
        return preferences.getString(ApplicationConstants.DIVISION,null);
    }
    public synchronized void setRoleDesc(String division){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DIVISION, division);
        editor.commit();
    }


    /*public synchronized boolean getgoogleloggedinflag(){
        return preferences.getBoolean(ApplicationConstants.googleloggedinflag, false);
    }
    public synchronized void setgoogleloggedinflag(Boolean googlefalg){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ApplicationConstants.googleloggedinflag, googlefalg);
        editor.commit();
    }


    public synchronized boolean getfbloggedinflag(){
        return preferences.getBoolean(ApplicationConstants.facebookloggedinflag, false);
    }
    public synchronized void setfbloggedinflag(Boolean fbfalg){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ApplicationConstants.facebookloggedinflag, fbfalg);
        editor.commit();
    }*/

    public synchronized String getUserid(){
        return preferences.getString(ApplicationConstants.USER_ID, null);
    }
    public synchronized void setUserid(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.USER_ID, user_id);
        editor.commit();
    }

    public synchronized String getNotiActivity(){
        return preferences.getString(ApplicationConstants.NOTI_ACV, "no");
    }
    public synchronized void setNotiActivity(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.NOTI_ACV, user_id);
        editor.commit();
    }



    public synchronized String getIdImage(){
        return preferences.getString(ApplicationConstants.ID_IMAGE, null);
    }
    public synchronized void setIdImage(String id_image){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ID_IMAGE, id_image);
        editor.commit();
    }

    public synchronized String getLocalMsgId(){
        return preferences.getString(ApplicationConstants.LocalMsgId, "4");
    }
    public synchronized void setLocalMsgId(String LocalMsgId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.LocalMsgId, LocalMsgId);
        editor.commit();
    }


    public synchronized String getLat(){
        return preferences.getString(ApplicationConstants.LAT, "0.0");
    }
    public synchronized void setLat(String lat){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.LAT, lat);
        editor.commit();
    }

    public synchronized String getLong(){
        return preferences.getString(ApplicationConstants.LONGG, "0.0");
    }
    public synchronized void setLong(String longg){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.LONGG, longg);
        editor.commit();
    }


    public synchronized String getAppVersionWeb(){
        return preferences.getString(ApplicationConstants.APPVERSIONWEB,null);
    }
    public synchronized void setAppVersionWeb(String appversion){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.APPVERSIONWEB, appversion);
        editor.commit();
    }

    // client lat long preferences
   /* public synchronized String getClientSourceLat(){
        return preferences.getString("getClientSourceLat", null);
    }

    public synchronized void setClientSourceLat(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientSourceLat", user_id);
        editor.commit();
    }



    public synchronized String getClientSourceLong(){
        return preferences.getString("getClientSourceLong", null);
    }

    public synchronized void setClientSourceLong(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientSourceLong", user_id);
        editor.commit();
    }*/


    /*
    public synchronized String getClientDestiLat(){
        return preferences.getString("getClientDestiLat", null);
    }

    public synchronized void setClientDestiLat(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientDestiLat", user_id);
        editor.commit();
    }



    public synchronized String getClientDestiLong(){
        return preferences.getString("getClientDestiLong", null);
    }

    public synchronized void setClientDestiLong(String user_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientDestiLong", user_id);
        editor.commit();
    }



    public synchronized String getNumberofseats(){
        return preferences.getString("getNumberofseats", null);
    }

    public synchronized void setNumberofseats(String user_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getNumberofseats", user_id);
        editor.commit();
    }*/

}
