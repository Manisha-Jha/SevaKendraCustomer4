package com.bses.dinesh.dsk.sevakendra;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.bses.dinesh.dsk.R;
import com.google.android.material.textfield.TextInputLayout;


/**
 * Created by Krishna on 5/3/2016.
 */
public class ForgetPassword extends Activity implements View.OnClickListener {


    private Toolbar toolbar;
    private EditText inputEmail;
    private TextInputLayout inputLayoutEmail;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen mode
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            setContentView(R.layout.forgetpassword);

            toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
            TextView ctoolbar_title = (TextView) toolbar.findViewById(R.id.ctoolbar_title);
            ctoolbar_title.setText(R.string.app_title_main);

            init();

            setListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init() {

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        inputEmail = (EditText) findViewById(R.id.input_email);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

    }

    private void setListeners() {
        btnConfirm.setOnClickListener(this);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_confirm:
                // do your code
                //passwordSentToEmail(v);
                break;

            default:
                break;
        }

    }


    private class MyTextWatcher implements TextWatcher {

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

                case R.id.input_email:
                    validateEmail();
                    break;

            }
        }
    }


    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        // Intent mainIntent = new Intent(ForgetPassword.this,LoginScreen.class);
        // ForgetPassword.this.startActivity(mainIntent);
        finish();

    }
}


