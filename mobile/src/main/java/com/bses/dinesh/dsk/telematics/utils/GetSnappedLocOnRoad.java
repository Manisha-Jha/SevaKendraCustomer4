package com.bses.dinesh.dsk.telematics.utils;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class GetSnappedLocOnRoad {
    static Context mContext;
    static String startWayPtLatiLongi;
    static String endWayPtLatiLongi;
    static String mLocLongiList;

    public GetSnappedLocOnRoad(String locLongiList)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        //mContext = context;
        mLocLongiList = locLongiList;

    }

    private static StringBuilder GetSnappedLocHTTPRequest() {
        //StringBuilder sb = new StringBuilder("https://roads.googleapis.com/v1/nearestRoads?");
        StringBuilder sb = new StringBuilder("https://roads.googleapis.com/v1/snapToRoads?");
        sb.append("path=" + mLocLongiList);
        //sb.append("&key=AIzaSyCRjEtZ3lPQLQGBVQrhlPaO09tF_a1L3Hw");
        //sb.append("&key=AIzaSyAaOECEr6zUZodfEceVzQ2Ogq6W1soR-q8");
        sb.append("&key=AIzaSyC3US8ADVe4nOqCoDerq9mYBZxu1p6b6X8");

        Log.e("SB",sb.toString());
        return sb;

    }


    public HashMap<String, String> getSnappedLocs() {

        HashMap<String, String> snappedPointLocsMap = new HashMap<String, String>();
        JSONArray snappedPointJSONArray = null;


        try {

            JSONObject jsonObj = ParserJson.getJSONfromURL(GetSnappedLocHTTPRequest().toString());
            if (jsonObj != null) {
                snappedPointJSONArray = jsonObj.getJSONArray("snappedPoints");
                for (int i = 0; i < snappedPointJSONArray.length(); i++) {
                    JSONObject snappedPointJSONObject = snappedPointJSONArray.getJSONObject(i);
                    JSONObject snappedPointLocJSONObject = snappedPointJSONObject.getJSONObject("location");

                    String snappedPointLocLati = snappedPointLocJSONObject.getString("latitude");
                    String snappedPointLocLongi = snappedPointLocJSONObject.getString("longitude");

                    String snappedPointIndex = snappedPointJSONObject.getString("originalIndex");
                    snappedPointLocsMap.put(snappedPointIndex, snappedPointLocLati + "," + snappedPointLocLongi);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return snappedPointLocsMap;
    }

    public static class ParserJson {
        public static JSONObject getJSONfromURL(String url) {

            String result = "";
            JSONObject jObject = null;

            // convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObject = new JSONObject(result);
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            return jObject;
        }

    }

}
