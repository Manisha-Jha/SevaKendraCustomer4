package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;



import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.databinding.ActivityOrderDetailActivityBinding;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.server.AppClient;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    ActivityOrderDetailActivityBinding binding;
    String order_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail_activity);

        order_num = getIntent().getExtras().getString("order_num");
        new UpdateListing().execute();
    }

    private class UpdateListing extends AsyncTask<String, Integer, Boolean> {
        List<Order> orderList;
        private ProgressDialog pd;

        UpdateListing() {
            orderList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(OrderDetailActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean success = false;
            try {
                AppClient client = AppClient.getAppClient(OrderDetailActivity.this);
                orderList = client.fetchOrderDetailsTask(order_num);
                Log.e("ATG", "Size" + orderList.size());
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!orderList.isEmpty()) {
                binding.tvOrderNum.setText(orderList.get(0).getOrderNum());
                binding.tvAppointmentDate.setText(orderList.get(0).getEntry_date_1());
                binding.tvReAppointmentDate.setText(orderList.get(0).getEntry_date_1());
                binding.tvName.setText(orderList.get(0).getFirstName() + " " + orderList.get(0).getLastName());
                binding.tvGender.setText(orderList.get(0).getGender());
                binding.tvFNameOrHName.setText(orderList.get(0).getFather_name());
                binding.tvMName.setText(orderList.get(0).getMother_name());
                binding.tvAddress.setText(orderList.get(0).getHouse_no() + " " + orderList.get(0).getBuilding_name() + " " +
                        orderList.get(0).getStreet() + " " + orderList.get(0).getArea() + " " + orderList.get(0).getPin());
                binding.tvMobileNum.setText(orderList.get(0).getMobile_no());
                binding.tvEmail.setText(orderList.get(0).getEmail());
            } else {

            }
            pd.dismiss();
        }
    }
}
