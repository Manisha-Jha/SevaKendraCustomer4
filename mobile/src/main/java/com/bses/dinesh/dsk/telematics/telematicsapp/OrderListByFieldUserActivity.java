package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Customer;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class OrderListByFieldUserActivity extends BaseActivity {
    private RecyclerView recyclerViewTask;
    private FetchUserTaskListAsyncTask mFetchUserTaskListAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        retrieveOrdersData();

    }

    private void retrieveOrdersData() {

            mFetchUserTaskListAsyncTask = new FetchUserTaskListAsyncTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserTaskListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserTaskListAsyncTask.execute((Void) null);

    }


    public class FetchUserTaskListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoadQuestion;
        List<Customer> custDataList;

        public FetchUserTaskListAsyncTask() {
            pdLoadQuestion = new ProgressDialog(OrderListByFieldUserActivity.this);
            custDataList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            pdLoadQuestion.setMessage("Waiting ...");
            pdLoadQuestion.show();
            pdLoadQuestion.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(OrderListByFieldUserActivity.this);
                String userId = SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getId();
                custDataList = client.fetchFieldUserOrderList(userId,"Revert");
                status=true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pdLoadQuestion.dismiss();
            mFetchUserTaskListAsyncTask = null;
            recyclerViewTask.removeAllViews();

            if (custDataList != null && custDataList.size() > 0) {
                OrderListAdapter customerListAdapter = new OrderListAdapter(OrderListByFieldUserActivity.this, custDataList);
                recyclerViewTask.setAdapter(customerListAdapter);
            }
        }

        @Override
        protected void onCancelled() {
            pdLoadQuestion.dismiss();
            mFetchUserTaskListAsyncTask = null;
        }
    }
    public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
        private List<Customer> mItems;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            LinearLayout llOrderListItem;
            TextView tvCustomerName,tvMobileNumVal,tvCustomerAddress,tvApartmentAddress;
            LinearLayout llItem;

            private ViewHolder(View itemView) {
                super(itemView);
                llOrderListItem = itemView.findViewById(R.id.llOrderListItem);
                llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
                tvCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
                tvApartmentAddress = (TextView) itemView.findViewById(R.id.tvApartmentAddress);
                tvMobileNumVal = (TextView) itemView.findViewById(R.id.tvMobileNumVal);
                tvCustomerAddress = (TextView) itemView.findViewById(R.id.tvCustomerAddress);
            }
        }

        public OrderListAdapter(Context context, List<Customer> custDataList) {
            mItems = custDataList;
        }

        @Override
        public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pending_order_detail_listitem, parent, false);

            OrderListAdapter.ViewHolder viewHolder = new OrderListAdapter.ViewHolder(view);
            view.setTag(viewHolder);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final OrderListAdapter.ViewHolder holder,
                                     final int position) {
            final Customer item = mItems.get(position);
            holder.tvCustomerName.setText(item.getFirstName() + " " + item.getLastName());
            holder.tvMobileNumVal.setText(item.getOrderNum());
            holder.tvApartmentAddress.setText(item.getStreet()+" "+item.getArea());
            holder.tvCustomerAddress.setText(item.getHouse_no()+" "+item.getBuilding_name()+" "+item.getStreet()+
                    " "+item.getArea()+" "+item.getPin());

            holder.llOrderListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (SharedPreferenceManager.with(OrderListByFieldUserActivity.this).getLoggedInUser().getUserRoleID()
                            .equals(AppConstants.FIELD_USER_ROLE)
                            && SharedPreferenceManager.with(OrderListByFieldUserActivity.this).getLoggedInUser().getActiveStatus()
                            .equalsIgnoreCase(AppConstants.USER_ACTIVE)) {
                        Intent intent = new Intent(OrderListByFieldUserActivity.this,EditOrderDetailActivity.class);
                        intent.putExtra("order_num", item.getOrderNum());
                        startActivity(intent);
                        //startActivity(new Intent(getApplicationContext(), EditOrderDetailActivity.class));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

}
