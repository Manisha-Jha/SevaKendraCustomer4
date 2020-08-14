package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.widget.AppCompatSpinner;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.DivisionData;
import com.bses.dinesh.dsk.telematics.data.TasksDataModel;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bses.dinesh.dsk.telematics.utils.Constant.ConstantValues.BLANK_VALUE;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_DIVISION;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_USERS;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_USER_TYPE;




public class RegistrationActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText etUsername;
    private EditText etEmailId;
    private EditText etPassword;
    private ProgressDialog progressDialog;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<Users> UsersList;
    private List<DivisionData> DivisionList; // Added by SM
    private List<DivisionData> UserTypeList; // Added by SM
    private Users users;
    private boolean isViewPass = false;
    private ImageView imgPasswordStatus; // Added by SM
    private AppCompatSpinner spinDivision; // Added by SM
    private AppCompatSpinner spinUserType; // Added by SM
    private String userTypeID, userTypeName, divID, divName; // Added by SM
    private CreateUserAsyncTask mCreateUserAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the Registration form.
        this.progressDialog = CommonUtilities.getDefaultLoader(this);
        UsersList = new ArrayList<>();
        DivisionList = new ArrayList<>();
        UserTypeList = new ArrayList<>();
        etUsername = findViewById(R.id.etMobile);
        spinDivision = findViewById(R.id.spinDivision);
        spinUserType = findViewById(R.id.spinUserType);

        etEmailId = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        imgPasswordStatus = findViewById(R.id.imgPasswordStatus);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.imgPasswordStatus).setOnClickListener(this);
        spinUserType.setOnItemSelectedListener(this);
        spinDivision.setOnItemSelectedListener(this);

        getUserTypeData();
        getDivisionData();


    }

    private void addUsers() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_USERS);
        users = new Users();
        users.setName(etUsername.getText().toString());
        users.setUserEmailId(etEmailId.getText().toString());
        users.setPassword(etPassword.getText().toString());
        mFirebaseDatabase.push().setValue(users);

        addUsers();
    }

    /*private void setUserData() {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(System.currentTimeMillis());
        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TABLE_USERS);
        String key = mFirebaseDatabase.push().getKey();
        Users users = new Users();
        users.setName(etUsername.getText().toString());
        users.setUserEmailId(etEmailId.getText().toString());
        users.setPassword(etPassword.getText().toString().trim());
        users.setId(key);
        //users.setUsertype(USER_TYPE_FIELD_TEAM);
        users.setLat(BLANK_VALUE);
        users.setLng(BLANK_VALUE);
        users.setUsertype(userTypeName);
        users.setUsertypeId(userTypeID);
        users.setDivId(divID);
        users.setUserDivName(divName);
        mFirebaseDatabase.child(key).setValue(users, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(RegistrationActivity.this, "User added successfully.", Toast.LENGTH_LONG).show();
                clearUserData();
            }
        });
    }*/

  /*  private void clearUserData() {
        etUsername.setText("");
        etEmailId.setText("");
        etPassword.setText("");
    }*/

    private void getDivisionData() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_DIVISION);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DivisionList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    DivisionData divisionData = postSnapshot12.getValue(DivisionData.class);
                    DivisionList.add(divisionData);
                }
                setDivisionData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getUserTypeData() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_USER_TYPE);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserTypeList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    DivisionData divisionData = postSnapshot12.getValue(DivisionData.class);
                    UserTypeList.add(divisionData);
                }
                setUserTypeData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


/*    private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_USERS);
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String User) {
                String TAG = null;
                Log.d(TAG, "onChildAdded" + dataSnapshot.getKey());
                //Comment comment = dataSnapshot.getValue(Comment.class);
                Toast.makeText(RegistrationActivity.this, "Registered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                if (TextUtils.isEmpty(etUsername.getText().toString())) {
                    Toast.makeText(this, "Please enter name.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etEmailId.getText().toString())) {
                    Toast.makeText(this, "Please enter email.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userTypeID) || spinUserType.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please select user type.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(divID) || spinDivision.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please select division.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
                } else {
                    // doRegister(etUsername.getText().toString().trim(), etPassword.getText().toString());
                    mCreateUserAsyncTask = new CreateUserAsyncTask();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        mCreateUserAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                    else
                        mCreateUserAsyncTask.execute((Void) null);
                }
                // startActivity(new Intent(this, AdminDashboardActivity.class));
                // finish();
                break;
            case R.id.imgPasswordStatus: // Added by SM
                visibilityPassword();
                break;
        }
    }

    private Boolean saveUserDataInDB() {
        Boolean isSaved = false;
        Map<String, Object> userInfo = new HashMap<String, Object>();

        userInfo.put(AppConstants.USER_NAME, etUsername.getText().toString().trim());
        userInfo.put(AppConstants.USER_EMAILID, etEmailId.getText().toString().trim());
        userInfo.put(AppConstants.USER_PASSWORD, etPassword.getText().toString().trim());
        userInfo.put(AppConstants.USER_LATTITUDE, BLANK_VALUE);
        userInfo.put(AppConstants.USER_LONGITUDE, BLANK_VALUE);
        userInfo.put(AppConstants.USER_TYPE_NAME, userTypeName);
        userInfo.put(AppConstants.USER_TYPE_ID, userTypeID);
        userInfo.put(AppConstants.USER_DIVISION_ID, divID);
        userInfo.put(AppConstants.USER_DIVISION_NAME, divName);

        try {
            AppClient client = AppClient.getAppClient(RegistrationActivity.this);
            client.insertUser(userInfo);
            isSaved = false;
        } catch (Exception e) {

        }
        return isSaved;


    }

    // Added by SM
    private void visibilityPassword() {
        if (isViewPass) {
            isViewPass = false;
            // hide password
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgPasswordStatus.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            isViewPass = true;
            // show password
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgPasswordStatus.setImageResource(R.drawable.ic_visibility_off_black_24dp);

        }
    }

    private void doRegister(String trim, String toString) {
        if (users.getUserEmailId().equalsIgnoreCase(etUsername.getText().toString().trim())
                && users.getPassword().equalsIgnoreCase(etPassword.getText().toString().trim())) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        }
    }

    private void setUserTypeData() {
        if (UserTypeList != null && UserTypeList.size() > 0) {
            String[] userTypeList = new String[UserTypeList.size() + 1];
            userTypeList[0] = "Select";
            int pos = 0;
            for (DivisionData userType : UserTypeList) {
                userTypeList[pos + 1] = userType.getName();
                pos++;
            }
            ArrayAdapter adapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_item, userTypeList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinUserType.setAdapter(adapter);
        }
    }

    private void setDivisionData() {
        if (DivisionList != null && DivisionList.size() > 0) {
            String[] divisionList = new String[DivisionList.size() + 1];
            divisionList[0] = "Select";
            int pos = 0;
            for (DivisionData division : DivisionList) {
                divisionList[pos + 1] = division.getName();
                pos++;
            }
            ArrayAdapter adapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_item, divisionList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinDivision.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (adapterView.getId()) {
            case R.id.spinUserType:
                if (pos != 0) {
                    userTypeID = UserTypeList.get(pos - 1).getId();
                    userTypeName = UserTypeList.get(pos - 1).getName();
                } else {
                    userTypeID = "";
                    userTypeName = "";
                }
                break;
            case R.id.spinDivision:
                if (pos != 0) {
                    divID = DivisionList.get(pos - 1).getId();
                    divName = DivisionList.get(pos - 1).getName();
                } else {
                    divID = "";
                    divName = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public class CreateUserAsyncTask extends AsyncTask<Void, Void, Boolean> {
        List<TasksDataModel> taskDataList;
        private ProgressDialog pdLoadQuestion;

        public CreateUserAsyncTask() {
            pdLoadQuestion = new ProgressDialog(RegistrationActivity.this);
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
            status = saveUserDataInDB();
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pdLoadQuestion.dismiss();
            mCreateUserAsyncTask = null;
            Toast.makeText(RegistrationActivity.this, "Task Successfully Created", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            pdLoadQuestion.dismiss();
            mCreateUserAsyncTask = null;
        }
    }

}
