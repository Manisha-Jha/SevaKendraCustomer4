package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

/*import androidx.appcompat.app.AppCompatActivity;*/

import androidx.appcompat.app.AppCompatActivity;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.HistoryTasksInfoWindowAdapter;
import com.bses.dinesh.dsk.telematics.adapter.UserLiveInfoWindowAdapter;
import com.bses.dinesh.dsk.telematics.data.OrderInformation;
import com.bses.dinesh.dsk.telematics.data.User;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.interfaces.DirectionPointListener;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.GetPathFromLocation;
import com.bses.dinesh.dsk.telematics.utils.GetSnappedLocOnRoad;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
/*import com.jhamobi.brplapp.data.UserLocationData;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.utils.CommonUtilities;*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  FieldUserLiveTrackingActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private static final int POLYLINE_STROKE_WIDTH_PX = 10;
    private GoogleMap mMap;
    private List<UserLocationData> LocList;
    //private List<List<UserLocationData>> userLocLists;
    private List<OrderInformation> TasksList;
    private String userID;
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_tracking_layout);

        LocList = new ArrayList<>();
      //  userLocLists = new ArrayList<List<UserLocationData>>();
        TasksList = new ArrayList<>();
        userID = getIntent().getStringExtra("userid");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        getUserLocation(userID,CommonUtilities.todayDate());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        setPolylineData1();
    }

    private void setPolylineData1()
    {
        if (mMap != null) mMap.clear();

        if (mMap != null && LocList != null && LocList.size() > 0)
        {
            List<LatLng> latlngList = new ArrayList<>();

            int position = 0;

            for (UserLocationData tasksData : LocList)
            {
                if (tasksData.getLat() != null && tasksData.getLng() != null
                        && !tasksData.getLat().equalsIgnoreCase("")
                        && !tasksData.getLng().equalsIgnoreCase("")
                        && (tasksData.getStatus().equals("start")
                        || tasksData.getStatus().equals("running")))
                {
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                            Double.parseDouble(tasksData.getLng())));
                    position++;
                    if(position==LocList.size())
                    {
                        latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                                Double.parseDouble(tasksData.getLng())));
                        setPolylineFromLatLng(latlngList);
                        latlngList.clear();
                    }

                }
                else if(tasksData.getStatus().equals("finish"))
                {
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()),
                            Double.parseDouble(tasksData.getLng())));
                    setPolylineFromLatLng(latlngList);
                    latlngList.clear();
                    position++;
                }
            }
        }
        else
        {
            Log.e("TAG Size","No data");
        }
    }

    private void setPolylineFromLatLng(List<LatLng> latlngList)
    {
        String waypoints = "";
        if(latlngList.size()>10)
        {
            for(int i=2;i<latlngList.size();i++)
            {
                LatLng point  = latlngList.get(i);
                if(i==2)
                    waypoints = "waypoints=";
                waypoints += point.latitude + "," + point.longitude + "|";
            }
        }
        new GetPathFromLocation(latlngList.get(0), latlngList.get(latlngList.size()-1),waypoints,
                new DirectionPointListener()
                {
                    @Override
                    public void onPath(PolylineOptions polyLine)
                    {
                        mMap.addPolyline(polyLine);
                    }
                }).execute();

/*
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions().clickable(true).geodesic(true).addAll(latlngList));

        if (latlngList != null && latlngList.size() > 0)
        {
            if (!isFirst)
            {
                if (latlngList.size() > 2)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(Math.round(latlngList.size() / 2)), 16));
                else
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 10));
            }
            isFirst = true;
        }

        polyline1.setEndCap(new RoundCap());
        polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline1.setColor(Color.RED);
        polyline1.setJointType(JointType.ROUND);
        polyline1.setGeodesic(true);
*/

        String distance = "Distance: " + CommonUtilities.getKmFromLatLongList(latlngList) + "km";

        try{
            for(int i=0;i<LocList.size();i++)
            {
                if(LocList.get(i).getStatus().equalsIgnoreCase("start"))
                {        // Set Start Location
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_start))
                            .position(latlngList.get(0))
                            .title("START"));
                }
                else if(LocList.get(i).getStatus().equalsIgnoreCase("finish"))
                {
                    // Set End Location
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_end))
                            .position(latlngList.get(latlngList.size()-1))
                            .title("END")
                            .snippet(distance));
                }
            }
        }catch (Exception e){}
    }

    private void setMarkerData()
    {
        if (mMap != null && TasksList != null && TasksList.size() > 0)
        {
            mMap.setInfoWindowAdapter(new HistoryTasksInfoWindowAdapter(this, TasksList)); // Added custom marker window
            for (OrderInformation tasksData : TasksList)
            {
                if (tasksData.getORDER_SUBMIT_LAT() != null && tasksData.getORDER_SUBMIT_LNG() != null &&
                        !tasksData.getORDER_SUBMIT_LAT().equalsIgnoreCase("") &&
                        !tasksData.getORDER_SUBMIT_LNG().equalsIgnoreCase(""))
                {
                    GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(
                            tasksData.getORDER_SUBMIT_LAT() + "," + tasksData.getORDER_SUBMIT_LNG());
                    HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
                    String snappedLatLongi = snappedLocs.get("0");
                    String snappedLati = null;
                    String snappedLongi = null;
                    if (snappedLatLongi != null)
                    {
                        snappedLati = snappedLatLongi.split(",")[0];
                        snappedLongi = snappedLatLongi.split(",")[1];

                        if (snappedLati.equals(null) && snappedLongi.equals(null))
                        {
                            snappedLati = tasksData.getORDER_SUBMIT_LAT();
                            snappedLongi = tasksData.getORDER_SUBMIT_LNG();
                        }
                    }
                    else
                    {
                        snappedLati = tasksData.getORDER_SUBMIT_LAT();
                        snappedLongi = tasksData.getORDER_SUBMIT_LNG();
                    }

                    String strAddress = CommonUtilities.getAddressStringFromLatLng(this,
                            Double.parseDouble(snappedLati),
                            Double.parseDouble(snappedLongi));


                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.ic_marker_done))
                            .position(new LatLng(Double.parseDouble(snappedLati),
                                    Double.parseDouble(snappedLongi)))
                            .title(tasksData.getORDER_NO())
                            .snippet(tasksData.getCUSTOMER_ADDRESS() + " (" + strAddress + " )"))
                            .setTag(tasksData.getORDER_NO());
                }
            }
        }
        else
        {
            Log.e("TAG","Tasklist size "+TasksList.size());
        }
    }

    private void getUserLocation(final String userId, final String date)
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

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(FieldUserLiveTrackingActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("User History");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getUserLocationDateBased(userId,date);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("User Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    List<UserLocationData> userLocs = jsonResponse.getUserLocationBasedOnDate();

                    if(userLocs.size() != 0)
                    {
                        LocList = jsonResponse.getUserLocationBasedOnDate();
                        setPolylineData1();
                    }
                    else
                    {
                        mMap.clear();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                    }
                }
                else
                {
                    mMap.clear();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
                    Toast.makeText(FieldUserLiveTrackingActivity.this, "Server down", Toast.LENGTH_SHORT).show();
                }
                getUserOrderLocation(userId,date,progressDoalog);
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }

    private void getUserOrderLocation(String userId, String date,ProgressDialog progressDialog)
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
        Call<RetrofitResponse> call = request.getOrderLocationDetail(userId,date);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("Order Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getUser_loc().size() != 0)
                    {
                        progressDialog.dismiss();
                        TasksList = jsonResponse.getUser_loc();
                        setMarkerData();
                    }
                    else
                    {
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(FieldUserLiveTrackingActivity.this, "Server down", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDialog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}


