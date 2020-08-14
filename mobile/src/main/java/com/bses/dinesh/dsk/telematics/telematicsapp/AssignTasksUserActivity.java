package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
/*
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;*/

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.AssignTaskAdapter;
import com.bses.dinesh.dsk.telematics.adapter.HistoryTaskListAdapter;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.data.TasksDataModel;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.interfaces.ClickEventPosValue;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.DateEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*import com.jhamobi.brplapp.adapter.AssignTaskAdapter;
import com.jhamobi.brplapp.adapter.HistoryTaskListAdapter;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.data.TasksDataModel;
import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.interfaces.ClickEventPosValue;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.utils.CommonUtilities;
import com.jhamobi.brplapp.utils.DateEditText;*/

import java.util.ArrayList;
import java.util.List;

public class AssignTasksUserActivity extends BaseActivity implements View.OnClickListener, DateEditText.OnDateSelectedListener, ClickEventPosValue {
    FetchTaskListAsyncTask mFetchTaskListAsyncTask;
    FetchUserListAsyncTask mFetchUserListAsyncTask;
    AssignTaskAsyncTask mAssignTaskAsyncTask;
    List<TasksData> tasksDataList;
    private RecyclerView recyclerViewTask;
    private List<TasksData> TaskList;
    private List<TasksDataModel> taskList;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Context mContext;
    private String strDate, userId;
    private TextView tvDate;
    private List<Users> UsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        mContext = AssignTasksUserActivity.this;
        UsersList = new ArrayList<>();
        TaskList = new ArrayList<>();
        taskList = new ArrayList<>();
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        //findViewById(R.id.llDate).setVisibility(View.VISIBLE);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        strDate = CommonUtilities.todayDate();
        tvDate.setText(strDate);

        //callTaskData();

        mFetchTaskListAsyncTask = new FetchTaskListAsyncTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mFetchTaskListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        else
            mFetchTaskListAsyncTask.execute((Void) null);


    }

    /*private void callTaskData() {
       // initialiseDatabase();
        //getUserData();
    }*/

    private void setData() {
        recyclerViewTask.removeAllViews();
        tasksDataList = convertData(taskList);
        if (tasksDataList != null && tasksDataList.size() > 0) {
            AssignTaskAdapter assignTaskAdapter = new AssignTaskAdapter(this, tasksDataList, this);
            recyclerViewTask.setAdapter(assignTaskAdapter);
        }

    }

    private List<TasksData> convertData(List<TasksDataModel> tasksDataModelList) {
        List<TasksData> tasksDataList = new ArrayList<>();


        for (int ii = 0; ii < tasksDataModelList.size(); ii++) {
            TasksData task = new TasksData();

            task.setId(tasksDataModelList.get(ii).getId());
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

    private void setDataAdmin() {
        recyclerViewTask.removeAllViews();
        if (TaskList != null && TaskList.size() > 0) {
            HistoryTaskListAdapter customerListAdapter = new HistoryTaskListAdapter(this, TaskList);
            recyclerViewTask.setAdapter(customerListAdapter);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDate:
                new DateEditText((Activity) mContext, tvDate, mContext);
                break;

        }
    }

    /*private void getUserData() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_USERS);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UsersList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    Users users = postSnapshot12.getValue(Users.class);
                    if (!users.getUsertype().equalsIgnoreCase("admin"))
                        UsersList.add(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/

    @Override
    public void OnDateSelected(TextView mTextView, String newDate) {
        if (!strDate.equalsIgnoreCase(newDate)) {
            strDate = newDate;
            mTextView.setText(newDate);
            //callTaskData();

        }
    }

    private void setUserData(AppCompatSpinner spinEngineer) {
        if (UsersList != null && UsersList.size() > 0) {
            String[] userList = new String[UsersList.size() + 1];
            userList[0] = "Select";
            int pos = 0;
            for (Users users : UsersList) {
                userList[pos + 1] = users.getName();
                pos++;
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinEngineer.setAdapter(adapter);
        }
    }

    private void selectOptions(final String data) {
        final AppCompatDialog dialog = new AppCompatDialog(this, R.style.dialog_light);
        dialog.setContentView(R.layout.layout_dialog_assign_task);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        AppCompatSpinner spinEngineer = dialog.findViewById(R.id.spinEngineer);
        setUserData(spinEngineer);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userId))
                    Toast.makeText(getApplicationContext(), "Select user.", Toast.LENGTH_SHORT).show();
                else {
                    submitTasks(userId, data);
                    dialog.cancel();
                }
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        spinEngineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                switch (adapterView.getId()) {
                    case R.id.spinEngineer:
                        if (pos != 0) {
                            userId = UsersList.get(pos - 1).getId();
                        } else {
                            userId = "";
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClickPosValue(String value, int pos) {
        mFetchUserListAsyncTask = new FetchUserListAsyncTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mFetchUserListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        else
            mFetchUserListAsyncTask.execute((Void) null);

        value = tasksDataList.get(pos).getId();
        selectOptions(value);
    }

    private void submitTasks(final String userID, final String value) {
/*        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        final String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        //final String time = DateFormat.format("HH:mm:ss", cal).toString();
        Map map = new HashMap();
        map.put("userid", userID);
        map.put("assignedDate", date);*/

        mAssignTaskAsyncTask = new AssignTaskAsyncTask(value, userID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mAssignTaskAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        else
            mAssignTaskAsyncTask.execute((Void) null);

/*        final DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.child(value).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

            }
        });*/
//        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
//                    //getting artist
//                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
//                    // Log.e("===tasksData====", "=====tasksData======" + tasksData.getAddress());
//                    if (data.getId().equalsIgnoreCase(tasksData.getId())) {
//                        Map map = new HashMap();
//                        map.put("userid", userID);
//                        map.put("assignedDate", date);
//                        mFirebaseDatabase.child(postSnapshot12.getKey()).updateChildren(map);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }

    /*private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                TaskList.clear();
                //Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                //iterating through all the nodes
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    //Log.e("===postSnapshot12====", "=====postSnapshot12======" + postSnapshot12.toString());
                    //getting artist
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    //Log.e("===Users====", "=====Users======" + tasksData.getAddress());
                    //adding artist to the list
                    if ((tasksData.getUserid() == null || tasksData.getUserid().equalsIgnoreCase("")) && !tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TaskList.add(tasksData);
                }

                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("==databaseError==", "==databaseError==" + databaseError.toString());
            }
        });
    }

    private void initialiseDatabaseAdmin() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                TaskList.clear();
                //Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                //iterating through all the nodes
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    //Log.e("===postSnapshot12====", "=====postSnapshot12======" + postSnapshot12.toString());
                    //getting artist
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    //Log.e("===Users====", "=====Users======" + tasksData.getAddress());
                    //adding artist to the list
                    if (tasksData.getCreateOn().equalsIgnoreCase(strDate) && getIntent().getStringExtra("userid").equalsIgnoreCase(tasksData.getUserid()) && !tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TaskList.add(tasksData);
                }

                setDataAdmin();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
*/
    final class FetchTaskListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoader;

        public FetchTaskListAsyncTask() {
            pdLoader = new ProgressDialog(AssignTasksUserActivity.this);
        }

        @Override
        protected void onPreExecute() {
            pdLoader.setMessage("Waiting ...");
            pdLoader.show();
            pdLoader.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                taskList = client.fetchTaskList();
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFetchTaskListAsyncTask = null;
            pdLoader.dismiss();
            if (success == true) {
                setData();
            }
        }

        @Override
        protected void onCancelled() {
            mFetchTaskListAsyncTask = null;
            pdLoader.dismiss();
        }
    }

    final class FetchUserListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoader;

        public FetchUserListAsyncTask() {
            pdLoader = new ProgressDialog(AssignTasksUserActivity.this);
        }

        @Override
        protected void onPreExecute() {
            pdLoader.setMessage("Waiting ...");
            pdLoader.show();
            pdLoader.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                UsersList = client.getAllUserDetailByDivision("");
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFetchUserListAsyncTask = null;
            pdLoader.dismiss();
            if (success == true) {
                setData();
            }
        }

        @Override
        protected void onCancelled() {
            mFetchUserListAsyncTask = null;
            pdLoader.dismiss();
        }
    }

    final class AssignTaskAsyncTask extends AsyncTask<Void, Void, Boolean> {
        String mTaskId;
        String mUserId;
        private ProgressDialog pdLoader;

        public AssignTaskAsyncTask(String taskId, String userId) {
            pdLoader = new ProgressDialog(AssignTasksUserActivity.this);
            mTaskId = taskId;
            mUserId = userId;
        }

        @Override
        protected void onPreExecute() {
            pdLoader.setMessage("Waiting ...");
            pdLoader.show();
            pdLoader.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                client.assignTask(mTaskId, mUserId);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFetchTaskListAsyncTask = null;
            pdLoader.dismiss();
            if (success == true) {
                setData();
            }
        }

        @Override
        protected void onCancelled() {
            mFetchTaskListAsyncTask = null;
            pdLoader.dismiss();
        }
    }
}
