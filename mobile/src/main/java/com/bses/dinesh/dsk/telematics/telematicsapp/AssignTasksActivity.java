package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


/*import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
/*import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.utils.CommonUtilities;*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_TASKS;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_USERS;

/*import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_TASKS;
import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_USERS;*/

public class AssignTasksActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    AppCompatSpinner spinEngineer; // btnLogin;
    String userId;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<Users> UsersList;
    private AppCompatEditText etName, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tasks);
        UsersList = new ArrayList<>();
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        spinEngineer = findViewById(R.id.spinEngineer);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
        spinEngineer.setOnItemSelectedListener(this);
        initialiseDatabase();
    }

    private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_USERS);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UsersList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    Users users = postSnapshot12.getValue(Users.class);
                    if (!users.getUserRoleID().equalsIgnoreCase("SAdmin"))
                        UsersList.add(users);
                }
                setUserData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setUserData() {
        if (UsersList != null && UsersList.size() > 0) {
            String[] userList = new String[UsersList.size() + 1];
            userList[0] = "Select";
            int pos = 0;
            for (Users users : UsersList) {
                userList[pos + 1] = users.getName();
                pos++;
            }
            ArrayAdapter adapter = new ArrayAdapter(AssignTasksActivity.this, android.R.layout.simple_spinner_item, userList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinEngineer.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    Toast.makeText(AssignTasksActivity.this, "Enter customer name.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer address.", Toast.LENGTH_SHORT).show();
                } else if (spinEngineer.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Select field engineer.", Toast.LENGTH_SHORT).show();
                } else {
                    setTaskData();
                }
                break;
        }
    }

    private void setTaskData() {
        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TABLE_TASKS);
        //String key = mFirebaseDatabase.push().getKey();
        Map map = new HashMap();
        String key = mFirebaseDatabase.push().getKey();
        String strDate = CommonUtilities.todayDate();
        map.put("id", key);
        map.put("name", etName.getText().toString().trim());
        map.put("address", etAddress.getText().toString().trim());
        map.put("userid", userId);
        map.put("status", "");
        map.put("lat", "");
        map.put("lng", "");
        map.put("timestamp", ServerValue.TIMESTAMP);
        map.put("CreateOn", strDate);
        map.put("date", "");
        map.put("time", "");
        mFirebaseDatabase.child(key).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                finish();
            }
        });
    }

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

    public static class CompletedTasksActivity {
    }
}
