package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.User;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.interfaces.ClickEventValue;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FieldUserCurrentLocationActivity  extends BaseActivity implements OnMapReadyCallback {

    List<Users> userCurrentLoc;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        String userid = getIntent().getExtras().getString("userid");
        fetchDivisionData(userid);
    }

    private void fetchDivisionData(String userid)
    {
        getUserCurrentLocation(userid);
    }

    public void InitMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5946, 77.3391), 9));
        setUserLocMarkerData();
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String strID = (String) marker.getTag();
//                Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);
//                String title = marker.getTitle();
//                intent1.putExtra("userid", strID);
//                startActivity(intent1);
                //selectOptions(strID);

            }
        });

    }

    private void setUserLocMarkerData() {
        if (mMap != null) mMap.clear();

        if (mMap != null && userCurrentLoc != null && userCurrentLoc.size() > 0)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            List<LatLng> latlngList = new ArrayList<>();
            for (Users users : userCurrentLoc)
            {
                if (users.getLat() != null && users.getLng() != null && !users.getLat().equalsIgnoreCase("") && !users.getLng().equalsIgnoreCase("")) {
                    latlngList.add(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng())));
                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_marker_walk)).position(new LatLng(Double.parseDouble(users.getLat()), Double.parseDouble(users.getLng()))));
                    marker.setTitle(users.getName());
                    marker.setTag(users.getId());

                    builder.include(marker.getPosition());

                   // new RouteDistanceText(this, marker);
                }
            }
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.30); // offset from edges of the map 10% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
        }
    }

    private void getUserCurrentLocation(String userId)
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
        progressDoalog = new ProgressDialog(FieldUserCurrentLocationActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("Current Location");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getUserCurrentLocation(userId);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("Current Loc-",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getCurrent_loc().size() != 0)
                    {
                        progressDoalog.dismiss();
                        userCurrentLoc = jsonResponse.getCurrent_loc();

                        if (userCurrentLoc != null) {
                            InitMapView();
                            setUserLocMarkerData();
                        }
                    }
                    else
                    {
                        progressDoalog.dismiss();
                    }
                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(FieldUserCurrentLocationActivity.this, "Server down", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}
