package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.HistoryTaskListAdapter;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;
/*import com.jhamobi.brplapp.adapter.HistoryTaskListAdapter;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;*/

public class HistoryTaskListActivity extends BaseActivity {
    private RecyclerView recyclerViewTask;
    private List<TasksData> TaskList;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_task_list);
        mContext = HistoryTaskListActivity.this;
        TaskList = new ArrayList<>();
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        if (getIntent().hasExtra("admin")) {
            initialiseDatabaseAdmin();
        } else
            initialiseDatabase();


    }

    private void setData() {
        if (TaskList != null && TaskList.size() > 0) {
            HistoryTaskListAdapter taskListAdapter = new HistoryTaskListAdapter(this, TaskList);
            recyclerViewTask.setAdapter(taskListAdapter);
        }
    }

    private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TaskList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    if (SharedPreferenceManager.with(mContext).getLoggedInUser().getId().equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
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
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                TaskList.clear();
                Log.e("===dataSnapshot====", "=====dataSnapshot======" + dataSnapshot.toString());
                //iterating through all the nodes
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    Log.e("===postSnapshot12====", "=====postSnapshot12======" + postSnapshot12.toString());
                    //getting artist
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    //Log.e("===Users====", "=====Users======" + tasksData.getAddress());
                    //adding artist to the list
                    if (getIntent().getStringExtra("userid").equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TaskList.add(tasksData);
                }

                setDataAdmin();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setDataAdmin() {
        if (TaskList != null && TaskList.size() > 0) {
            HistoryTaskListAdapter customerListAdapter = new HistoryTaskListAdapter(this, TaskList);
            recyclerViewTask.setAdapter(customerListAdapter);
        }

    }
}
