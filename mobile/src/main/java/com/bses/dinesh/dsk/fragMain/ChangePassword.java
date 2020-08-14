package com.bses.dinesh.dsk.fragMain;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.ChangePassBean;
import com.bses.dinesh.dsk.proxies.ChangePasswordProxie;
import com.bses.dinesh.dsk.utils.ApplicationException;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


/**
 * Created by Krishna on 4/29/2016.
 */
public class ChangePassword extends Fragment {

    private TextInputLayout inputLayoutOldPassword,inputLayoutNewPassword,inputLayoutReNewPassword;
    private EditText inputOldPassword,inputNewPassword,inputReNewPassword;
    private Button changePassSubmit;
    private UserPreferences pref;
    private ProgressDialog pd;
    private ChangePasswordProxie changePassProxie;
    private ChangePassBean changePassBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_changepassword, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("CHANGE PASSWORD");
        pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        inputLayoutOldPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_old_password);
        inputOldPassword = (EditText) rootView.findViewById(R.id.input_old_password);

        inputLayoutNewPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_new_password);
        inputNewPassword = (EditText) rootView.findViewById(R.id.input_new_password);

        inputLayoutReNewPassword = (TextInputLayout) rootView.findViewById(R.id.reinput_layout_new_password);
        inputReNewPassword = (EditText) rootView.findViewById(R.id.reinput_new_password);

        changePassSubmit = (Button) rootView.findViewById(R.id.btn_change_pass_submit);

        inputOldPassword.addTextChangedListener(new MyTextWatcher(inputOldPassword));
        inputNewPassword.addTextChangedListener(new MyTextWatcher(inputNewPassword));
        inputReNewPassword.addTextChangedListener(new MyTextWatcher(inputReNewPassword));

        changePassSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChangePasswordRequest();
            }
        });

        return rootView;
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

                case R.id.input_old_password:
                    validateOldPassword();
                    break;
                case R.id.input_new_password:
                    validateNewPassword();
                    break;
                case R.id.reinput_new_password:
                    validateReNewPassword();
                    break;
            }
        }
    }


    private boolean validateOldPassword() {
        if (inputOldPassword.getText().toString().trim().isEmpty()) {
            inputLayoutOldPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputOldPassword);
            return false;
        }



        else {
            inputLayoutOldPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNewPassword() {

        if (inputNewPassword.getText().toString().trim().isEmpty()) {
            inputLayoutNewPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputNewPassword);
            return false;
        }

        else {
            inputLayoutNewPassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateReNewPassword() {

        if (inputReNewPassword.getText().toString().trim().isEmpty()) {
            inputLayoutReNewPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputReNewPassword);
            return false;
        }

        if(!inputNewPassword.getText().toString().trim().equals(inputReNewPassword.getText().toString().trim())){
            inputLayoutReNewPassword.setError(getString(R.string.err_msg_re_password));
            requestFocus(inputReNewPassword);
            return false;
        }

        else {
            inputLayoutReNewPassword.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void submitChangePasswordRequest() {

        if (!validateOldPassword()) {
            return;
        }
        if (!validateNewPassword()) {
            return;
        }
        if (!validateReNewPassword()) {
            return;
        }


        AsyncChangePass task = new AsyncChangePass();
        //Call execute
        task.execute();



    }


    private class AsyncChangePass extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {
                changePassBean = ApplicationUtil.getInstance().getWebservice().ChangePassword(changePassProxie, getActivity());
            }

            catch (ApplicationException e) {
                e.printStackTrace();
            }

            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Loading..");
            pd.show();

            changePassProxie = new ChangePasswordProxie();
            changePassProxie.setStrUser_id(pref.getUserid());
            changePassProxie.setStrCurrent_Password(inputOldPassword.getText().toString().trim());
            changePassProxie.setStrNew_Password(inputNewPassword.getText().toString().trim());
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Make Progress Bar invisible
            //loginStatus = inputEmpid.getText().toString();

            try {
                pd.dismiss();


                if (changePassBean.getSuccessMsg().toString().equalsIgnoreCase("true")) {


                    final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //dialog.setCancelable(false);
                    //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.promt_message_success);

                    TextView alert_title2 = (TextView) dialog.findViewById(R.id.alert_title2);
                    TextView closeDialog = (TextView) dialog.findViewById(R.id.closePrompt);
                    alert_title2.setText("Password Successfully Changed !!");


                                   /* messageTitle.setText(msg.getMSGTITLE());
                                    messageDate.setText(msg.getMSGDATE());
                                    messageDescription.setText(msg.getMSGDESC());*/
                    // requestid.setText(broadcastMsgBean.getACK());

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            inputOldPassword.setText("");
                            inputNewPassword.setText("");
                            inputReNewPassword.setText("");

                            ApplicationUtil.getInstance().logout(getActivity());


                        }
                    });
                    // messageDescription.setText(message.getGenre().toString());
                    //   messageTitle.setText(message.getTitle().toString());
                    dialog.show();
                }


                else {

                    Snackbar.make(getView() , "Server is not responding, Please try again !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
            catch (Exception er){
                er.printStackTrace();
            }



        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }






}
