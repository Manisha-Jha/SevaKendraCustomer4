package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
/*
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;*/

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.CompletedTasksAdapter;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.DateEditText;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.jhamobi.brplapp.adapter.CompletedTasksAdapter;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.utils.CommonUtilities;
import com.jhamobi.brplapp.utils.DateEditText;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;*/

import java.util.ArrayList;
import java.util.List;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;
/*
import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;*/

public class CompletedTasksActivity extends BaseActivity implements View.OnClickListener, DateEditText.OnDateSelectedListener {
    private RecyclerView recyclerViewTask;
    private List<TasksData> TaskList;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Context mContext;
    private String userID, strDate;
    private View llDate;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_task_list);
        mContext = CompletedTasksActivity.this;
        TaskList = new ArrayList<>();
        userID = getIntent().getStringExtra("userid");
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        strDate = CommonUtilities.todayDate();
        findViewById(R.id.llDate).setVisibility(View.VISIBLE);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvDate.setText(strDate);
        //if (getIntent().hasExtra("admin")){
        initialiseDatabaseAdmin();
        // }
        //else initialiseDatabase();
    }

    private void setData() {
        recyclerViewTask.removeAllViews();
        if (TaskList != null && TaskList.size() > 0) {
            // TaskListAdapter customerListAdapter = new TaskListAdapter(this, TaskList);
            //  recyclerViewTask.setAdapter(customerListAdapter);
        }

    }

    private void initialiseDatabase() {
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
                    if (SharedPreferenceManager.with(mContext).getLoggedInUser().getId().equalsIgnoreCase(tasksData.getUserid()) && !tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TaskList.add(tasksData);
                }

                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initialiseDatabaseAdmin() {
        try {


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
                    //if (getIntent().getStringExtra("userid").equalsIgnoreCase(tasksData.getUserid())&& tasksData.getStatus().equalsIgnoreCase("Yes"))
                    if (tasksData.getDate().equalsIgnoreCase(strDate) && userID.equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TaskList.add(tasksData);
                }

                setDataAdmin();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDataAdmin() {
        recyclerViewTask.removeAllViews();
        if (TaskList != null && TaskList.size() > 0) {
            CompletedTasksAdapter customerListAdapter = new CompletedTasksAdapter(this, TaskList);
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

    @Override
    public void OnDateSelected(TextView mTextView, String newDate) {
        if (!strDate.equalsIgnoreCase(newDate)) {
            strDate = newDate;
            mTextView.setText(newDate);
            initialiseDatabaseAdmin();
        }
    }
}
