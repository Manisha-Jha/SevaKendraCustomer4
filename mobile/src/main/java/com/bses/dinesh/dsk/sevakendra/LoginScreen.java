package com.bses.dinesh.dsk.sevakendra;

/**
 * Created by Krishna on 4/28/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.User;
import com.bses.dinesh.dsk.proxies.ValidateUserProxie;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.bses.dinesh.dsk.utils.ApplicationException;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.GPSTracker;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginScreen extends Activity {


    private String empId = "", empPassword = "";
    private String loginStatus;
    // public ProgressBar webservicePG;
    static boolean errored = false;
    public Intent mainIntent;
    private String regGcmId = "";
    private int versionNumber = 1;
    private String currentAppVersion = "";
    private UserPreferences userPreferences;
    private ProgressDialog progressDialog;
    private double ver_web = 1.0, ver_app = 1.0;
    private PackageInfo pinfo;
    // GPSTracker class
    GPSTracker gpsTracker;
    private String stringLatitude = "0.0";
    private String stringLongitude = "0.0";
    private User user;


    ProgressBar webservicePG;


 Toolbar toolbar;


    TextView ctoolbar_title;


    TextInputLayout inputLayoutName;


    TextInputLayout inputLayoutEmail;


    TextInputLayout inputLayoutPassword;


    EditText inputEmpid;


    EditText inputPassword;

    TextView txt_version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);
        // ButterKnife.bind(this);

        try {
        webservicePG = (ProgressBar) findViewById(R.id.progressBar1);


       toolbar = (Toolbar) findViewById(R.id.custom_toolbar);


        ctoolbar_title = (TextView) findViewById(R.id.ctoolbar_title);


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);


        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);


        inputEmpid = (EditText) findViewById(R.id.input_empid);


        inputPassword = (EditText) findViewById(R.id.input_password);

        txt_version = (TextView) findViewById(R.id.txt_version);
        Button btn_signin = (Button) findViewById(R.id.btn_signin);
        TextView forgetPassword = (TextView) findViewById(R.id.forgetPassword);

        btn_signin.setOnClickListener(v -> {
            if (validateEmpid() || validatePassword()) {
                submitForm(v);
            }
        });
        forgetPassword.setOnClickListener(v -> {
            forgetPassword();
        });


        progressDialog = new ProgressDialog(LoginScreen.this);
        userPreferences = UserPreferences.getInstance(LoginScreen.this);

        ctoolbar_title.setText(R.string.app_title_main);
        inputLayoutEmail.setVisibility(View.GONE);



            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = pinfo.versionCode;
            currentAppVersion = pinfo.versionName;
            txt_version.setText("Version : " + currentAppVersion);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception er) {
            er.printStackTrace();
        }

        getGPSDATA();
        // setSupportActionBar(toolbar);

    }

   /* @OnClick(R.id.btn_signin)
    public void onSignInClick(View v) {
        submitForm(v);
    }

    @OnClick(R.id.forgetPassword)
    public void onForgetClick() {
        forgetPassword();
    }*/

   /* @OnTextChanged(R.id.input_empid)
    public void onEmpidChange() {
        new MyTextWatcher(inputEmpid);
    }

    @OnTextChanged(R.id.input_password)
    public void onPassChange() {
        new MyTextWatcher(inputPassword);
    }*/


    private void getGPSDATA() {

        gpsTracker = new GPSTracker(LoginScreen.this);

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());

            userPreferences.setLat(stringLatitude);
            userPreferences.setLong(stringLongitude);
        } else {

            gpsTracker.showSettingsAlert();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void submitForm(View view) {


        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        if (!validateEmpid()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }


        sendLoginDataToWeb();

    }

    private void forgetPassword() {

        Intent mainIntent = new Intent(LoginScreen.this, ForgetPassword.class);
        LoginScreen.this.startActivity(mainIntent);
        // LoginScreen.this.finish();
    }

    private boolean validateEmpid() {
        if (inputEmpid.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_empid));
            requestFocus(inputEmpid);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


  /*  private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_empid:
                    validateEmpid();
                    break;

                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
*/

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }


    public void backButtonHandler() {
        ApplicationUtil.getInstance().showBackDialog(LoginScreen.this, "Leave application?", "Are you sure you want to leave the application?");

    }

    private void sendLoginDataToWeb() {

        empId = inputEmpid.getText().toString().trim();
        empPassword = inputPassword.getText().toString().trim();


        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }


    private class AsyncCallWS extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            ValidateUserProxie validateUserProxie = new ValidateUserProxie();
            validateUserProxie.setStrPassword(empPassword);
            validateUserProxie.setStrUser_id(empId);
            validateUserProxie.setStrIMEI(ApplicationUtil.getInstance().getDeviceImei(LoginScreen.this));
            validateUserProxie.setStrLongitude(stringLongitude);
            validateUserProxie.setStrLatitude(stringLatitude);

            user = new User();

            if (ApplicationUtil.getInstance().checkInternetConnection(LoginScreen.this)) {
                try {
                    user = ApplicationUtil.getInstance().getWebservice().validateUser(validateUserProxie, LoginScreen.this);
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
            }

            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webservicePG.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {


                webservicePG.setVisibility(View.INVISIBLE);
                mainIntent = new Intent(LoginScreen.this, MainActivity.class);

                if (user.getUSER_ID() != null)
                    errored = false;
                else {
                    if (user != null)
                        errored = false;
                    else
                        errored = true;
                }

                if (!errored) {
                    if (user.getUSER_ID() != null) {




                        UserPreferences pref = UserPreferences.getInstance(LoginScreen.this);

                        pref.setFirst_name(user.getNAME());
                        pref.setCompCode(user.getACTIVE_FLAG()); // company brpl/bypl
                        pref.setRoleDesc(user.getIMEI_NUMBER());  // designation
                        pref.setDivision(user.getCOMP_CODE()); //division
                        pref.setPassword(empPassword);
                        pref.setUserid(user.getUSER_ID());
                        pref.setUser_role(user.getROLE_ID());
                        pref.setAppVersionWeb(user.getAPP_VERSION_WEB());

                        Users users=   new Users(user.getUSER_ID(), user.getPHONE_NUMBER(), user.getEMAIL_ID(), "", user.getROLE_ID(), user.getNAME(), "", "","");
                        SharedPreferenceManager.with(getApplicationContext()).updateLoggedInUser(users);
                        SharedPreferenceManager.with(getApplicationContext()).setUserLoggedIn(true);
                        mainIntent = new Intent(LoginScreen.this, MainActivity.class);
                        mainIntent.putExtra("empType", "admin");
                        LoginScreen.this.startActivity(mainIntent);
                        LoginScreen.this.finish();


                    } else {
                        Snackbar.make(getCurrentFocus(), "Invalid Username/Password !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } else {
                    Snackbar.make(getCurrentFocus(), "Unable to connect with data base server.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            } catch (Exception er) {
                er.printStackTrace();
                Snackbar.make(getCurrentFocus(), "Unable to connect with data base server. !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            //Re-initialize Error Status to False
            errored = false;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }


    }


}