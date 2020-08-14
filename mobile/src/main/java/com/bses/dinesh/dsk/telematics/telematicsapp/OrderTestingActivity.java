package com.bses.dinesh.dsk.telematics.telematicsapp;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;



import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.databinding.ActivityOrderTestingBinding;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.utils.GPSTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderTestingActivity extends BaseActivity
{
    String userID;
    String order_no="1234567";
    public double currentlatitude = 0;
    public double currentlongitude = 0;
    private GPSTracker gps;

    ActivityOrderTestingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_testing);

        userID = getIntent().getStringExtra("userid");

        getCurrentLocation();

        binding.btnOpenOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String current_time= new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                insertOrderOpenTime(userID,order_no,current_time);
            }
        });

        binding.btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String current_time= new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String current_date = dateFormat.format(date);

                updateOrderSubmitTime(userID,current_date,String.valueOf(currentlatitude),String.valueOf(currentlongitude),
                        current_time, "Noida sec-62",order_no);
            }
        });
    }

    private void insertOrderOpenTime(String user_id,String order_num,String open_time)
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
        Call<RetrofitResponse> call = request.insert_order_open_time(order_num,user_id,open_time);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("INSERT",response.raw()+"");
                RetrofitResponse jsonResponse = response.body();
                if(jsonResponse.Key.equals("Success"))
                {
                    Toast.makeText(OrderTestingActivity.this, jsonResponse.Key, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(OrderTestingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }

    private void updateOrderSubmitTime(String user_id,String order_submit_date, String lat,
                                       String lng, String submit_time,String address, String order_num)
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
        Call<RetrofitResponse> call = request.update_order_submit_time(user_id,order_submit_date,lat,lng,
                submit_time,address,order_num);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("INSERT",response.raw()+"");
                RetrofitResponse jsonResponse = response.body();
                if(jsonResponse.Key.equals("Success"))
                {
                    Toast.makeText(OrderTestingActivity.this, jsonResponse.Key, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(OrderTestingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }

    private void getCurrentLocation()
    {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            currentlatitude = gps.getLatitude();
            currentlongitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
    }

}
