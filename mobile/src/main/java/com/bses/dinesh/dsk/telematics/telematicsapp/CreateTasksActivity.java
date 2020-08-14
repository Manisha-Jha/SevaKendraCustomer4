package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/*import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;*/

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.DivisionData;
import com.bses.dinesh.dsk.telematics.data.TasksDataModel;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*import com.jhamobi.brplapp.data.DivisionData;
import com.jhamobi.brplapp.data.TasksDataModel;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.server.AppConstants;
import com.jhamobi.brplapp.utils.CommonUtilities;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_REQUEST_TYPE;

//import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_REQUEST_TYPE;

public class CreateTasksActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    AppCompatSpinner spinRequestType; // btnLogin;
    String requestTypeId, requestTypeName;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<DivisionData> RequestTypeList;
    private AppCompatEditText etName, etAddress, etMobile, etEmail, etRequestNo;
    private CreateTasksAsyncTask mCreateTaskAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tasks);
        RequestTypeList = new ArrayList<>();
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etRequestNo = findViewById(R.id.etRequestNo);
        spinRequestType = findViewById(R.id.spinRequestType);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
        spinRequestType.setOnItemSelectedListener(this);
        //initialiseDatabase();
        getRequestTypeData();
    }

    private void getRequestTypeData() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        //mFirebaseDatabase = mFirebaseInstance.getReference("RequestType");
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_REQUEST_TYPE);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RequestTypeList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    DivisionData divisionData = postSnapshot12.getValue(DivisionData.class);
                    RequestTypeList.add(divisionData);
                }
                setUserData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setUserData() {
        if (RequestTypeList != null && RequestTypeList.size() > 0) {
            String[] typeList = new String[RequestTypeList.size() + 1];
            typeList[0] = "Select";
            int pos = 0;
            for (DivisionData data : RequestTypeList) {
                typeList[pos + 1] = data.getName();
                pos++;
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinRequestType.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer name.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etMobile.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer mobile.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etEmail.getText().toString().trim()) && !CommonUtilities.isValidEmail(etEmail.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer email.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer address.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etRequestNo.getText().toString().trim())) {
                    Toast.makeText(this, "Enter customer request number.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(requestTypeId) && spinRequestType.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Select request type.", Toast.LENGTH_SHORT).show();
                } else {
                    mCreateTaskAsyncTask = new CreateTasksAsyncTask();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        mCreateTaskAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                    else
                        mCreateTaskAsyncTask.execute((Void) null);
                }
                break;
        }
    }

    private Boolean saveTaskDataInDB() {
        Boolean isSaved = false;
        Map<String, Object> taskInfo = new HashMap<String, Object>();
        String userId = SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getId();

        taskInfo.put(AppConstants.USER_ID, userId);
        taskInfo.put(AppConstants.TASK_NAME, etName.getText().toString().trim());
        taskInfo.put(AppConstants.TASK_LATTITUDE, AppConstants.NO_AVAILABLE);
        taskInfo.put(AppConstants.TASK_LONGITUDE, AppConstants.NO_AVAILABLE);
        taskInfo.put(AppConstants.CUSTOMER_MOBILE, etMobile.getText().toString().trim());
        taskInfo.put(AppConstants.CUSTOMER_EMAILID, etEmail.getText().toString().trim());
        taskInfo.put(AppConstants.CUSTOMER_ADDRESS, etAddress.getText().toString().trim());
        taskInfo.put(AppConstants.CUSTOMER_REQUEST, etRequestNo.getText().toString().trim());
        taskInfo.put(AppConstants.CUSTOMER_REQUEST_TYPE_ID, requestTypeId);
        taskInfo.put(AppConstants.CUSTOMER_REQUEST_TYPE_NAME, requestTypeName);
        taskInfo.put(AppConstants.TASK_STATUS, AppConstants.TASK_NOT_SUBMITTED);

        try {
            AppClient client = AppClient.getAppClient(CreateTasksActivity.this);
            client.insertTask(taskInfo);
            isSaved = false;
        } catch (Exception e) {

        }
        return isSaved;
    }

    /*private void setTaskData() {
        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TABLE_TASKS_DATA);
        //String key = mFirebaseDatabase.push().getKey();
        Map map = new HashMap();
        String key = mFirebaseDatabase.push().getKey();
        String strDate = CommonUtilities.todayDate();
        map.put("id", key);
        map.put("name", etName.getText().toString().trim());
        map.put("mobile", etMobile.getText().toString().trim());
        map.put("email", etEmail.getText().toString().trim());
        map.put("address", etAddress.getText().toString().trim());
        map.put("requestNo", etRequestNo.getText().toString().trim());
        map.put("requestTypeId", requestTypeId);
        map.put("requestTypeName", requestTypeName);
        map.put("userid", "");
        map.put("status", "");
        map.put("lat", "");
        map.put("lng", "");
        map.put("timestamp", ServerValue.TIMESTAMP);
        map.put("CreateOn",strDate );
        map.put("assignedDate", "");
        map.put("date", "");
        map.put("time", "");
        mFirebaseDatabase.child(key).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                clearData();
                //finish();
                Toast.makeText(CreateTasksActivity.this, "Created successfully", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (adapterView.getId()) {
            case R.id.spinRequestType:
                if (pos != 0) {
                    requestTypeId = RequestTypeList.get(pos - 1).getId();
                    requestTypeName = RequestTypeList.get(pos - 1).getName();
                } else {
                    requestTypeId = "";
                    requestTypeName = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void clearData() {
        etName.setText("");
        etMobile.setText("");
        etEmail.setText("");
        etAddress.setText("");
        etRequestNo.setText("");
        spinRequestType.setSelection(0);
    }

    public class CreateTasksAsyncTask extends AsyncTask<Void, Void, Boolean> {
        List<TasksDataModel> taskDataList;
        private ProgressDialog pdLoadQuestion;

        public CreateTasksAsyncTask() {
            pdLoadQuestion = new ProgressDialog(CreateTasksActivity.this);
            taskDataList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            pdLoadQuestion.setMessage("Waiting ...");
            pdLoadQuestion.show();
            pdLoadQuestion.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status;
            status = saveTaskDataInDB();
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pdLoadQuestion.dismiss();
            mCreateTaskAsyncTask = null;
            Toast.makeText(CreateTasksActivity.this, "Task Successfully Created", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            pdLoadQuestion.dismiss();
            mCreateTaskAsyncTask = null;
        }
    }
}
