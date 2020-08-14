package com.bses.dinesh.dsk.telematics.utils;

import android.content.Context;
import android.widget.TextView;

import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class RouteDistanceText {
    private Context mContext;
    private TextView mTextView;
    private String strDate, userID;
    private Marker marker;

    public RouteDistanceText(Context mContext, String strDate, String userID, TextView mTextView) {
        this.mContext = mContext;
        this.strDate = strDate;
        this.userID = userID;
        this.mTextView = mTextView;
        initialiseDatabase();
    }

    public RouteDistanceText(Context mContext, Marker marker) {
        this.mContext = mContext;
        this.strDate = CommonUtilities.todayDate();
        this.userID = marker.getTag().toString();
        this.marker = marker;
        initialiseDatabase();
    }


    private void initialiseDatabase() {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("fieldLocation");
        mFirebaseDatabase.orderByChild("userid").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                //LocList.clear();
                List<LatLng> latlngList = new ArrayList<>();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    UserLocationData tasksData = postSnapshot12.getValue(UserLocationData.class);
                    // if (userID.equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
                    if (tasksData.getDate() != null && tasksData.getDate().equalsIgnoreCase(strDate))
                        if (tasksData.getLat() != null && tasksData.getLng() != null && !tasksData.getLat().equalsIgnoreCase("") && !tasksData.getLng().equalsIgnoreCase(""))
                            latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()), Double.parseDouble(tasksData.getLng())));

                }
                setDistanceData(latlngList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setDistanceData(List<LatLng> latlngList) {
        if (latlngList != null && latlngList.size() > 1) {
            String distance = "Distance: " + CommonUtilities.getKmFromLatLongList(latlngList) + "km";
            if (mTextView != null)
                mTextView.setText(distance);
            if (marker != null)
                marker.setSnippet(distance);
        }
    }

}
