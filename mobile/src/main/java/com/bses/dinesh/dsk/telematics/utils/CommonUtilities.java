package com.bses.dinesh.dsk.telematics.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.telematicsapp.MapsActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
/*import com.jhamobi.brplapp.MapsActivity;
import com.jhamobi.brplapp.R;*/

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CommonUtilities {
    public static int getRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(max) + 1;
    }

    public static String getAppVersionName(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Version " + version;
    }

    public static void showToastOnMainThread(final Context mContext, final String msg) {
        if (mContext == null) {
            return;
        }
        final Context appContext = mContext.getApplicationContext();
        if (appContext == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void openFragmentContainerActivityWithDelay(final Context mContext,
                                                              final int navViewId,
                                                              final int navItemIndex) {
        Intent intent = new Intent(mContext, MapsActivity.class);
        // intent.putExtra(Constant.IntentExtras.NAV_VIEW_ID, navViewId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    public static void openFragmentContainerActivityWithDelay(final Context mContext,
                                                              final int navViewId,
                                                              final int navItemIndex, String data) {
        Intent intent = new Intent(mContext, MapsActivity.class);
        // intent.putExtra(Constant.IntentExtras.NAV_VIEW_ID, navViewId);
        //  intent.putExtra(Constant.IntentExtras.DATA, data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ProgressDialog getDefaultLoader(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.loading));
        return progressDialog;
    }

    public static String convertDateFormat(String strDate, String strInputDateFormat, String strOutputDateFormat) {
        String formattedDate;
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat(strInputDateFormat, Locale.US);
            SimpleDateFormat sdfOutput = new SimpleDateFormat(strOutputDateFormat, Locale.US);
            formattedDate = sdfOutput.format(sdfInput.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = strDate;
        }
        Log.e("==formattedDate==", formattedDate + "===strInputDateFormat===" + strInputDateFormat + "===strOutputDateFormat===" + strOutputDateFormat);
        return formattedDate;
    }

    public static String todayDate() {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        //String time = DateFormat.format("HH:mm:ss", cal).toString();
        return date;
    }

    public static String currentTime()
    {
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        return currentTime;
    }



    public static String yesterdayDate() {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DATE, -1);
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        //String time = DateFormat.format("HH:mm:ss", cal).toString();
        return date;
    }

    public static String getDateFromTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(time);
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        //String time = DateFormat.format("HH:mm:ss", cal).toString();
        return date;
    }

    public static String getAddressStringFromLatLng(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("LatLng address", strReturnedAddress.toString());
            } else {
                Log.e("LatLng address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LatLng address", "Canont get Address!");
        }
        return strAdd;
    }

    public static void defaultMapLocation(GoogleMap map) {
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(53, 2));
        // moves camera to coordinates
        map.moveCamera(point);
        // animates camera to coordinates
        map.animateCamera(point);
    }

    public static double calculateVisibleRadius(GoogleMap map) {
        float[] distanceWidth = new float[1];
        VisibleRegion visibleRegion = map.getProjection().getVisibleRegion();
        LatLng farRight = visibleRegion.farRight;
        LatLng farLeft = visibleRegion.farLeft;
        LatLng nearRight = visibleRegion.nearRight;
        LatLng nearLeft = visibleRegion.nearLeft;
        //calculate the distance between left <-> right of map on screen
        Location.distanceBetween((farLeft.latitude + nearLeft.latitude) / 2, farLeft.longitude, (farRight.latitude + nearRight.latitude) / 2, farRight.longitude, distanceWidth);
        // visible radius is / 2  and /1000 in Km:
        return distanceWidth[0] / 2 / 1000;
    }

    public static String getKmFromLatLong(double lat1, double lng1, double lat2, double lng2) {
        String strValue = "";
        try {
            Location loc1 = new Location("");
            loc1.setLatitude(lat1);
            loc1.setLongitude(lng1);
            Location loc2 = new Location("");
            loc2.setLatitude(lat2);
            loc2.setLongitude(lng2);
            //double distanceInMeters = loc1.distanceTo(loc2);
            double distanceIKM = loc1.distanceTo(loc2) / 1000;
            strValue = String.format("%.3f", distanceIKM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strValue;
    }

    public static String getKmFromLatLongList(List<LatLng> latlngList) {
        String strValue = "";
        double distanceIKM = 0;
        int lPos = 0;
        try {
            if (latlngList != null && latlngList.size() > 1)
                for (int i = 1; i < latlngList.size() - 1; i++) {
                    Location loc1 = new Location("");
                    loc1.setLatitude(latlngList.get(lPos).latitude);
                    loc1.setLongitude(latlngList.get(lPos).longitude);
                    Location loc2 = new Location("");
                    loc2.setLatitude(latlngList.get(i).latitude);
                    loc2.setLongitude(latlngList.get(i).longitude);
                    //double distanceInMeters = loc1.distanceTo(loc2);
                    distanceIKM = distanceIKM + Math.abs((loc1.distanceTo(loc2) / 1000));
                    lPos++;
                }
            strValue = String.format("%.3f", distanceIKM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strValue;
    }

    public static String DateToFormattedString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static String TimeToFormattedString(Time time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String strTime = dateFormat.format(time);
        return strTime;
    }
}
