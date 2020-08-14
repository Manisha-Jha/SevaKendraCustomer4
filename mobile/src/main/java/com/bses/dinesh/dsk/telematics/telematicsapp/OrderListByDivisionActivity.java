package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.data.TasksDataModel;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.DateEditText;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class OrderListByDivisionActivity extends BaseActivity implements View.OnClickListener, DateEditText.OnDateSelectedListener {
    private RecyclerView recyclerViewTask;
    private Context mContext;
    private String strDate;
    private TextView tvDate;
    private FetchUserTaskListAsyncTask mFetchUserTaskListAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        mContext = OrderListByDivisionActivity.this;
        // orderList = new ArrayList<>();
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        findViewById(R.id.llDate).setVisibility(View.VISIBLE);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        strDate = CommonUtilities.todayDate();
        tvDate.setText(strDate);

        callTaskData();


    }

    private void callTaskData() {
        if (getIntent().hasExtra("admin")) {
            //initialiseDatabaseAdmin();
            mFetchUserTaskListAsyncTask = new FetchUserTaskListAsyncTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserTaskListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserTaskListAsyncTask.execute((Void) null);
        } else {
            mFetchUserTaskListAsyncTask = new FetchUserTaskListAsyncTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserTaskListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserTaskListAsyncTask.execute((Void) null);
        }
    }

    private List<TasksData> convertData(List<TasksDataModel> tasksDataModelList) {
        List<TasksData> tasksDataList = new ArrayList<>();


        for (int ii = 0; ii < tasksDataModelList.size(); ii++) {
            TasksData task = new TasksData();

            task.setName(tasksDataModelList.get(ii).getName());
            task.setAddress(tasksDataModelList.get(ii).getAddress());
            if (tasksDataModelList.get(ii).getCreateOn() != null) {
                task.setCreateOn(tasksDataModelList.get(ii).getCreateOn().toString());
            } else {
                task.setCreateOn("");
            }
            if (tasksDataModelList.get(ii).getDate() != null) {
                task.setDate(tasksDataModelList.get(ii).getDate().toString());
            } else {
                task.setCreateOn("");
            }

            if (tasksDataModelList.get(ii).getTime() != null) {
                task.setTime(tasksDataModelList.get(ii).getTime().toString());
            } else {
                task.setTime("");
            }

            if (tasksDataModelList.get(ii).getAssignedDate() != null) {
                task.setAssignedDate(tasksDataModelList.get(ii).getAssignedDate());
            } else {
                task.setAssignedDate("");
            }

            tasksDataList.add(task);
        }
        return tasksDataList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDate:
                new DateEditText((Activity) mContext, tvDate, mContext);
                break;

        }
    }

    @Override
    public void OnDateSelected(TextView mTextView, String newDate) {
        if (!strDate.equalsIgnoreCase(newDate)) {
            strDate = newDate;
            mTextView.setText(newDate);
            callTaskData();
        }
    }


/*    private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                orderList.clear();
                //Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                //iterating through all the nodes
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    //Log.e("===postSnapshot12====", "=====postSnapshot12======" + postSnapshot12.toString());
                    //getting artist
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    //Log.e("===Users====", "=====Users======" + tasksData.getAddress());
                    //adding artist to the list
                    if (tasksData.getCreateOn().equalsIgnoreCase(strDate) && SharedPreferenceManager.with(mContext).getLoggedInUser().getId().equalsIgnoreCase(tasksData.getUserid())&& !tasksData.getStatus().equalsIgnoreCase("Yes"))
                        orderList.add(tasksData);
                }

                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/

    public class FetchUserTaskListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        List<Order> custDataList;
        private ProgressDialog pdLoadQuestion;

        public FetchUserTaskListAsyncTask() {
            pdLoadQuestion = new ProgressDialog(OrderListByDivisionActivity.this);
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
                AppClient client = AppClient.getAppClient(OrderListByDivisionActivity.this);
                String userId = SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getId();
                //custDataList = client.fetchUserTaskList(getIntent().getStringExtra("userid"));
                custDataList = client.fetchUserTaskList("test", "");
                status = true;

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
                OrderListAdapter customerListAdapter = new OrderListAdapter(OrderListByDivisionActivity.this, custDataList);
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
        private List<Order> mItems;
        private Context mContext;

        public OrderListAdapter(Context context, List<Order> custDataList) {
            mItems = custDataList;
            mContext = context;
        }

        @Override
        public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.history_order_detail_listitem, parent, false);

            OrderListAdapter.ViewHolder viewHolder = new OrderListAdapter.ViewHolder(view);
            view.setTag(viewHolder);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final OrderListAdapter.ViewHolder holder, final int position) {
            final Order item = mItems.get(position);
            holder.tvName.setText(item.getFirstName() + " " + item.getLastName());
            holder.tvApartmentAddress.setText(item.getHouse_no() + " " + item.getBuilding_name() + " " + item.getStreet() +
                    " " + item.getArea() + " " + item.getPin());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName, tvApartmentAddress;

            private ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvCustomerName);
                tvApartmentAddress = itemView.findViewById(R.id.tvApartmentAddress);
            }
        }
    }
}
