package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    LoginAsyncTask mLoginAsyncTask;
    private EditText etUserId;
    private EditText etPassword;
    private boolean isViewPass = false;
    private ImageView imgPasswordStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserId = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        imgPasswordStatus = findViewById(R.id.imgPasswordStatus);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.imgPasswordStatus).setOnClickListener(this);
        if (SharedPreferenceManager.with(this).isUserLoggedIn())
        {//.getUserDesignation().equalsIgnoreCase(AppConstants.MANAGER)
            try {
                if (SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUserRoleID().
                        equalsIgnoreCase(AppConstants.MANAGER_USER_ROLE))
                    startActivity(new Intent(this, ManagerDashboardActivity.class));
                else if(SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getUserRoleID().
                        equalsIgnoreCase(AppConstants.FIELD_USER_ROLE))
                    startActivity(new Intent(this, FieldUserDashboardActivity.class));
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (etPassword.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    if (etUserId.getText().toString().trim().length() > 1 &&
                            etPassword.getText().toString().trim().length() > 1) {

                        String uid = etUserId.getText().toString().trim();
                        String pass = etPassword.getText().toString().trim();

                        checkLogin(uid,pass);
                        /*
                        mLoginAsyncTask = new LoginAsyncTask(uid, pass);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            mLoginAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                        else
                            mLoginAsyncTask.execute((Void) null);
                        */
                    } else {
                        Toast.makeText(this, "Fill", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.imgPasswordStatus:
                visibilityPassword();
                break;
        }
    }

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

    public class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoadQuestion;
        private String userId;
        private String pass;
        private Users user;

        public LoginAsyncTask(String userId, String pass) {
            pdLoadQuestion = new ProgressDialog(LoginActivity.this);
            this.userId = userId;
            this.pass = pass;
        }

        @Override
        protected void onPreExecute() {
            pdLoadQuestion.setMessage("Waiting ...");
            pdLoadQuestion.show();
            pdLoadQuestion.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                user = client.login(userId, pass);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pdLoadQuestion.dismiss();
            mLoginAsyncTask = null;
            if (success == true)
            {
                if (user != null) {
                    SharedPreferenceManager.with(getApplicationContext()).setUserLoggedIn(true);
                    SharedPreferenceManager.with(getApplicationContext()).updateLoggedInUser(user);

                    // Only Managers are able to login those have at least one Filed User (Role_ID = Seva_MA)
                    if (user.getUserRoleID().equalsIgnoreCase(AppConstants.MANAGER_USER_ROLE)
                            && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE))
                        startActivity(new Intent(getApplicationContext(), ManagerDashboardActivity.class));
                    else if (user.getUserRoleID().equals(AppConstants.FIELD_USER_ROLE)
                            && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE))
                        startActivity(new Intent(getApplicationContext(), FieldUserDashboardActivity.class));
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Login details", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            pdLoadQuestion.dismiss();
            mLoginAsyncTask = null;
        }
    }


    private void checkLogin(final String userId, String password)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://115.249.67.72:9880/DSK_telematic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Login");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getLogin(userId,password);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("Login-",response.raw()+"");

                RetrofitResponse jsonResponse = response.body();
                if(jsonResponse.getUser_login() != null && jsonResponse.getUser_login().size()>0)
                {
                    List<Users> users = jsonResponse.getUser_login();
                    Users user = users.get(0);
                    if (user != null)
                    {
                        progressDoalog.dismiss();
                        SharedPreferenceManager.with(getApplicationContext()).setUserLoggedIn(true);
                        SharedPreferenceManager.with(getApplicationContext()).updateLoggedInUser(user);

                        // Only Managers are able to login those have at least one Filed User (Role_ID = Seva_MA)
                        if (user.getUserRoleID().equalsIgnoreCase(AppConstants.MANAGER_USER_ROLE)
                                && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE))
                            startActivity(new Intent(getApplicationContext(), ManagerDashboardActivity.class));
                        else if (user.getUserRoleID().equals(AppConstants.FIELD_USER_ROLE)
                                && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE))
                            startActivity(new Intent(getApplicationContext(), FieldUserDashboardActivity.class));
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(LoginActivity.this, "Wrong UserId or Password!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}
