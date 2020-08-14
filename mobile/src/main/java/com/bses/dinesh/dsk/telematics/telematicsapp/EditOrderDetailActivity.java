package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

/*
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jhamobi.brplapp.data.Order;
import com.jhamobi.brplapp.databinding.EditOrderDetailLayoutBinding;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.utils.GPSTracker;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.databinding.EditOrderDetailLayoutBinding;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.GPSTracker;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditOrderDetailActivity extends AppCompatActivity
{
    EditOrderDetailLayoutBinding binding;
    String order_num;
    String currentTime;
    private GPSTracker gps;
    public double currentlatitude = 0;
    public double currentlongitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.edit_order_detail_layout);
        order_num = getIntent().getExtras().getString("order_num");
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        getCurrentLocation();
        new UpdateListing().execute();
    }

    private class UpdateListing extends AsyncTask<String, Integer, Boolean>
    {
        private ProgressDialog pd;
        List<Order> orderList;
        UpdateListing(){
            orderList= new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(EditOrderDetailActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean success = false;
            try {
                AppClient client = AppClient.getAppClient(EditOrderDetailActivity.this);
                orderList = client.fetchOrderDetailsTask(order_num);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(!orderList.isEmpty())
            {
                binding.tvOrderNum.setText(orderList.get(0).getOrderNum());
                binding.tvAppointmentDate.setText((orderList.get(0).getEntry_date_1()));
                binding.tvReAppointmentDate.setText((orderList.get(0).getEntry_date_1()));
                binding.tvName.setText(orderList.get(0).getFirstName()+" "+orderList.get(0).getLastName());
                binding.tvGender.setText(orderList.get(0).getGender());
                binding.tvFNameOrHName.setText(orderList.get(0).getFather_name());
                binding.tvMName.setText(orderList.get(0).getMother_name());
                binding.tvAddress.setText(orderList.get(0).getHouse_no()+" "+orderList.get(0).getBuilding_name()+" "+
                        orderList.get(0).getStreet()+" "+orderList.get(0).getArea()+" "+orderList.get(0).getPin());
                binding.tvMobileNum.setText(orderList.get(0).getMobile_no());
                binding.tvEmail.setText(orderList.get(0).getEmail());

                binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            AppClient client = AppClient.getAppClient(getApplicationContext());
                            String endtTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            client.insertUserTrackingTime(SharedPreferenceManager.with(EditOrderDetailActivity.this)
                                    .getLoggedInUser().getId(),
                                    binding.tvName.getText().toString(),
                                    binding.tvAddress.getText().toString(),
                                    orderList.get(0).getEntry_date_1(),
                                    order_num,
                                    currentTime,
                                    endtTime,
                                    String.valueOf(currentlatitude),
                                    String.valueOf(currentlongitude),
                                    "submitted");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else
            {///
            }
            pd.dismiss();
        }
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
