package com.bses.dinesh.dsk.fragMain;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.PunchingDataBean;
import com.bses.dinesh.dsk.fragCategoryChange.CategoryBased_CategoryChange;
import com.bses.dinesh.dsk.fragLoadChange.CategoryBased_LoadChange;
import com.bses.dinesh.dsk.fragNameChange.CategoryBased_NameChange;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.proxies.SealValidateProxie;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Created by Krishna on 4/29/2016.
 */
public class NameAndAddress extends Fragment {

    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    //private McrOrederProxie orderData;
    public static EditText devicenumber;
    private EditText stickernumber, busnumber, drumnumber, runninglengthfrom,
            runninglengthto, cablelength, outputcablelength, outputbuslength, drumnumberbb, runninglengthfrombb, runninglengthtobb, cablelengthbb;
    private RadioGroup radioInstalledBus, radiocableinstalltype, radioelcbinstall, radiostickerinstall, radiocableinstalltypebb, radiocustomerbus;
    private String valInstalledBus, valInstallType, valelcbinstall, valdropreason, valStickerinstall, valInstallTypebb, valcustomerinstall;
    private Spinner dropbussize, dropbussizecust, dropcablesize, dropreason, dropreasonre;
    private Spinner dropterminalseal1, dropterminalseal2, dropmeterseal1, dropmeterseal2,
            dropbusbarseal1, dropbusbarseal2, busbarcablesize;
    public static Button validateseal, scanmeter;
    public static String meterscannedVal = "No", dropbussizecustVal = "";
    private ProgressDialog progressDialog;
    private PunchingDataBean punchingDataBean;
    private String sealValidateVal = "false";
    private int from, to, frombb, tobb;
    //private DBHandler db;

    private String[] seals, objectsSub;
    private ArrayList<String> sealUsed = new ArrayList<String>(6);
    private UserPreferences userPreferences;
    private String printseal1, printseal2, printseal3, printseal4, printseal5, printseal6;
    private String elcbSubmitVal = "";
    private EditText firstname, middlename, lastname, fathersname, mothername, houseno, buildingname, street, area, pin, landmark, mobilenum, landline, email;
    private RadioGroup radiotitle, radiogender;
    private String valTitle, valGender;

    //New
    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private Fragment fragment;
    private RadioButton radio_mr, radio_mrs, radio_ms, radio_other, radio_extra2, radio_male, radio_female, radio_othergender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.name_address_screen1, container, false);
        progressDialog = new ProgressDialog(getActivity());
        // db = new DBHandler(getActivity());
        userPreferences = UserPreferences.getInstance(getActivity());

        sealUsed = new ArrayList<String>(6);
        sealUsed.add(" ");
        sealUsed.add(" ");
        sealUsed.add(" ");
        sealUsed.add(" ");
        sealUsed.add(" ");
        sealUsed.add(" ");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("CUSTOMER DETAILS");


        Bundle bundle = getArguments();
        //orderData = (McrOrederProxie) bundle.getSerializable("orderData");
        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
            mcrOrderProxie = (McrOrderProxie) bundle.getSerializable("orderData");
        } else {
            mcrOrderProxieOtherConn = (McrOrderProxieOtherConn) bundle.getSerializable("orderData");
        }


        // seva kendra fields
        firstname = (EditText) rootView.findViewById(R.id.firstname);
        middlename = (EditText) rootView.findViewById(R.id.middlename);
        lastname = (EditText) rootView.findViewById(R.id.lastname);


        radiotitle = (RadioGroup) rootView.findViewById(R.id.radiotitle);

        radio_mr = (RadioButton) rootView.findViewById(R.id.radio_mr);
        radio_mrs = (RadioButton) rootView.findViewById(R.id.radio_mrs);
        radio_ms = (RadioButton) rootView.findViewById(R.id.radio_ms);
        radio_other = (RadioButton) rootView.findViewById(R.id.radio_other);
        radio_extra2 = (RadioButton) rootView.findViewById(R.id.radio_extra2);

        valTitle = ((RadioButton) rootView.findViewById(radiotitle.getCheckedRadioButtonId())).getText().toString();
        radiotitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valTitle = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });


        radiogender = (RadioGroup) rootView.findViewById(R.id.radiogender);

        radio_male = (RadioButton) rootView.findViewById(R.id.radio_male);
        radio_female = (RadioButton) rootView.findViewById(R.id.radio_female);
        radio_othergender = (RadioButton) rootView.findViewById(R.id.radio_othergender);

        valGender = ((RadioButton) rootView.findViewById(radiogender.getCheckedRadioButtonId())).getText().toString();
        radiogender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valGender = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });


        fathersname = (EditText) rootView.findViewById(R.id.fathersname);
        mothername = (EditText) rootView.findViewById(R.id.mothername);

        houseno = (EditText) rootView.findViewById(R.id.houseno);
        buildingname = (EditText) rootView.findViewById(R.id.buildingname);
        street = (EditText) rootView.findViewById(R.id.street);
        area = (EditText) rootView.findViewById(R.id.area);
        pin = (EditText) rootView.findViewById(R.id.pin);

        landmark = (EditText) rootView.findViewById(R.id.landmark);
        mobilenum = (EditText) rootView.findViewById(R.id.mobilenum);
        landline = (EditText) rootView.findViewById(R.id.landline);
        email = (EditText) rootView.findViewById(R.id.email);


        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {

            if (mcrOrderProxie.getStrMOBILE_NO() != null) {
                mobilenum.setText(mcrOrderProxie.getStrMOBILE_NO());
            }
            if (mcrOrderProxie.getStrEMAIL() != null) {
                email.setText(mcrOrderProxie.getStrEMAIL());
            }

            if (mcrOrderProxie.getStrTITLE() != null) {

                if (mcrOrderProxie.getStrTITLE().equalsIgnoreCase("Mr.")) {
                    radio_mr.setChecked(true);
                    radio_male.setChecked(true);
                    valGender = "Male";
                    valTitle = "Mr.";
                } else if (mcrOrderProxie.getStrTITLE().equalsIgnoreCase("Mrs.")) {
                    radio_mrs.setChecked(true);
                    radio_female.setChecked(true);
                    valGender = "Female";
                } else if (mcrOrderProxie.getStrTITLE().equalsIgnoreCase("Ms.")) {
                    radio_ms.setChecked(true);
                    radio_female.setChecked(true);
                    valTitle = "Ms.";
                    valGender = "Female";
                } else {
                    radio_other.setChecked(true);
                    radio_male.setChecked(true);
                    valTitle = "Mr.";
                }

            }

            if (mcrOrderProxie.getStrFIRST_NAME() != null) {
                firstname.setText(mcrOrderProxie.getStrFIRST_NAME());
            }
            if (mcrOrderProxie.getStrLAST_NAME() != null) {
                lastname.setText(mcrOrderProxie.getStrLAST_NAME());
            }
            if (mcrOrderProxie.getStrFATHER_NAME() != null) {
                fathersname.setText(mcrOrderProxie.getStrFATHER_NAME());
            }

            if (mcrOrderProxie.getStrHOUSE_NO() != null) {
                houseno.setText(mcrOrderProxie.getStrHOUSE_NO());
            }
            if (mcrOrderProxie.getStrAREA() != null) {
                area.setText(mcrOrderProxie.getStrAREA());
            }
            if (mcrOrderProxie.getStrSTREET() != null) {
                street.setText(mcrOrderProxie.getStrSTREET());
            }
            if (mcrOrderProxie.getStrPIN() != null) {
                pin.setText(mcrOrderProxie.getStrPIN());
            }

        } else {

            if (mcrOrderProxieOtherConn.getStrMOBILE_NO() != null) {
                mobilenum.setText(mcrOrderProxieOtherConn.getStrMOBILE_NO());
            }
            if (mcrOrderProxieOtherConn.getStrEMAIL() != null) {
                email.setText(mcrOrderProxieOtherConn.getStrEMAIL());
            }

            if (mcrOrderProxieOtherConn.getStrTITLE() != null) {

                if (mcrOrderProxieOtherConn.getStrTITLE().equalsIgnoreCase("Mr.")) {
                    radio_mr.setChecked(true);
                    radio_male.setChecked(true);
                    valGender = "Male";
                    valTitle = "Mr.";
                } else if (mcrOrderProxieOtherConn.getStrTITLE().equalsIgnoreCase("Mrs.")) {
                    radio_mrs.setChecked(true);
                    radio_female.setChecked(true);
                    valGender = "Female";
                } else if (mcrOrderProxieOtherConn.getStrTITLE().equalsIgnoreCase("Ms.")) {
                    radio_ms.setChecked(true);
                    radio_female.setChecked(true);
                    valTitle = "Ms.";
                    valGender = "Female";
                } else {
                    radio_other.setChecked(true);
                    radio_male.setChecked(true);
                    valTitle = "Mr.";
                }

            }

            if (mcrOrderProxieOtherConn.getStrFIRST_NAME() != null) {
                firstname.setText(mcrOrderProxieOtherConn.getStrFIRST_NAME());
            }
            if (mcrOrderProxieOtherConn.getStrLAST_NAME() != null) {
                lastname.setText(mcrOrderProxieOtherConn.getStrLAST_NAME());
            }
            if (mcrOrderProxieOtherConn.getStrFATHER_NAME() != null) {
                fathersname.setText(mcrOrderProxieOtherConn.getStrFATHER_NAME());
            }

            if (mcrOrderProxieOtherConn.getStrHOUSE_NO() != null) {
                houseno.setText(mcrOrderProxieOtherConn.getStrHOUSE_NO());
            }
            if (mcrOrderProxieOtherConn.getStrAREA() != null) {
                area.setText(mcrOrderProxieOtherConn.getStrAREA());
            }
            if (mcrOrderProxieOtherConn.getStrSTREET() != null) {
                street.setText(mcrOrderProxieOtherConn.getStrSTREET());
            }
            if (mcrOrderProxieOtherConn.getStrPIN() != null) {
                pin.setText(mcrOrderProxieOtherConn.getStrPIN());
            }

        }


        scanmeter = (Button) rootView.findViewById(R.id.scanmeter);


        devicenumber = (EditText) rootView.findViewById(R.id.devicenumber);


        radiostickerinstall = (RadioGroup) rootView.findViewById(R.id.radiostickerinstall);
        valStickerinstall = ((RadioButton) rootView.findViewById(radiostickerinstall.getCheckedRadioButtonId())).getText().toString();

        final LinearLayout stickerll = (LinearLayout) rootView.findViewById(R.id.stickerll);
        stickernumber = (EditText) rootView.findViewById(R.id.stickernumber);

        radiostickerinstall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valStickerinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if (valStickerinstall.equalsIgnoreCase("Yes")) {
                    stickerll.setVisibility(View.VISIBLE);
                } else {
                    stickerll.setVisibility(View.GONE);
                    stickernumber.setText("");
                }
            }
        });


        //devicenumber.setText(orderData.getDEVICENO());

        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
            devicenumber.setText("Request Number -" + mcrOrderProxie.getStrORDER_NO());
        } else {
            devicenumber.setText("CA Number -" + mcrOrderProxieOtherConn.getStrCA_NUMBER());
        }

        radioelcbinstall = (RadioGroup) rootView.findViewById(R.id.radioelcbinstall);
        valelcbinstall = ((RadioButton) rootView.findViewById(radioelcbinstall.getCheckedRadioButtonId())).getText().toString();

        final LinearLayout elcbbasedll = (LinearLayout) rootView.findViewById(R.id.elcbbasedll);

        radioelcbinstall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valelcbinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if (valelcbinstall.equalsIgnoreCase("Yes")) {
                    // elcbbasedll.setVisibility(View.VISIBLE);
                    valelcbinstall = "Yes";
                } else {

                    valelcbinstall = "No";

                }
            }
        });

        radioInstalledBus = (RadioGroup) rootView.findViewById(R.id.radioinstalledbus);

        final LinearLayout busbarmainll = (LinearLayout) rootView.findViewById(R.id.busbarinstalled_ll);
        final LinearLayout busbarll = (LinearLayout) rootView.findViewById(R.id.busbarll);

        dropbussize = (Spinner) rootView.findViewById(R.id.dropbussize);
        dropbussizecust = (Spinner) rootView.findViewById(R.id.dropbussizecust);

        busnumber = (EditText) rootView.findViewById(R.id.busnumber);
        busbarcablesize = (Spinner) rootView.findViewById(R.id.busbarcablesize);
        drumnumberbb = (EditText) rootView.findViewById(R.id.drumnumberbb); // new

        radiocableinstalltypebb = (RadioGroup) rootView.findViewById(R.id.radiocableinstalltypebb);
        valInstallTypebb = ((RadioButton) rootView.findViewById(radiocableinstalltypebb.getCheckedRadioButtonId())).getText().toString();

        radiocableinstalltypebb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstallTypebb = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });

        runninglengthfrombb = (EditText) rootView.findViewById(R.id.runninglengthfrombb); // new
        runninglengthtobb = (EditText) rootView.findViewById(R.id.runninglengthtobb); // new
        cablelengthbb = (EditText) rootView.findViewById(R.id.cablelengthbb);

        cablelengthbb.setEnabled(true);
        cablelengthbb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (runninglengthfrombb.getText().toString().trim().isEmpty()) {
                    return false;
                } else {
                    if (!runninglengthtobb.getText().toString().trim().isEmpty()) {


                        frombb = Integer.parseInt(runninglengthfrombb.getText().toString().trim());
                        tobb = Integer.parseInt(runninglengthtobb.getText().toString().trim());

                        if (frombb < tobb || frombb == tobb) {
                            int result = tobb - frombb;
                            if (result < 0) {
                                // return false;
                            } else {
                                cablelengthbb.setText(Integer.toString(result));
                            }
                        } else {
                            Snackbar.make(getView(), "From length should be less than to length!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            cablelengthbb.setText("");
                            runninglengthtobb.setText("");
                        }
                    }
                }

                return false;
            }
        });

        final LinearLayout busbarcablesize_layout = (LinearLayout) rootView.findViewById(R.id.busbarcablesize_layout);
        final LinearLayout busbarcustomerll = (LinearLayout) rootView.findViewById(R.id.busbarcustomerll);
        valInstalledBus = ((RadioButton) rootView.findViewById(radioInstalledBus.getCheckedRadioButtonId())).getText().toString();
        final LinearLayout radiocustomerbusll = (LinearLayout) rootView.findViewById(R.id.radiocustomerbusll);

        radiocustomerbus = (RadioGroup) rootView.findViewById(R.id.radiocustomerbus);
        dropreason = (Spinner) rootView.findViewById(R.id.dropreason);
        dropreasonre = (Spinner) rootView.findViewById(R.id.dropreasonre);


        dropcablesize = (Spinner) rootView.findViewById(R.id.dropcablesize);
        drumnumber = (EditText) rootView.findViewById(R.id.drumnumber);
        outputcablelength = (EditText) rootView.findViewById(R.id.outputcablelength);
        radiocableinstalltype = (RadioGroup) rootView.findViewById(R.id.radiocableinstalltype);
        valInstallType = ((RadioButton) rootView.findViewById(radiocableinstalltype.getCheckedRadioButtonId())).getText().toString();
        radiocableinstalltype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstallType = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });

        runninglengthfrom = (EditText) rootView.findViewById(R.id.runninglengthfrom);
        runninglengthto = (EditText) rootView.findViewById(R.id.runninglengthto);
        cablelength = (EditText) rootView.findViewById(R.id.cablelength);
        cablelength.setEnabled(true);

        validateseal = (Button) rootView.findViewById(R.id.validateseal);

        validateseal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchDataWS task = new punchDataWS();
                //Call execute
                task.execute();
            }
        });


        Button button1 = (Button) rootView.findViewById(R.id.next2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (checkValidation()) {


                    if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {

                        mcrOrderProxie.setStrFIRST_NAME(firstname.getText().toString());
                        mcrOrderProxie.setStrMIDDLE_NAME(middlename.getText().toString());
                        mcrOrderProxie.setStrLAST_NAME(lastname.getText().toString());
                        mcrOrderProxie.setStrTITLE(valTitle);
                        mcrOrderProxie.setStrGENDER(valGender);
                        mcrOrderProxie.setStrFATHER_NAME(fathersname.getText().toString());
                        mcrOrderProxie.setStrMOTHER_NAME(mothername.getText().toString());
                        mcrOrderProxie.setStrHOUSE_NO(houseno.getText().toString());
                        mcrOrderProxie.setStrBUILDING_NAME(buildingname.getText().toString());
                        mcrOrderProxie.setStrSTREET(street.getText().toString());
                        mcrOrderProxie.setStrAREA(area.getText().toString());
                        mcrOrderProxie.setStrPIN(pin.getText().toString());
                        mcrOrderProxie.setStrLANDMARK(landmark.getText().toString());
                        mcrOrderProxie.setStrMOBILE_NO(mobilenum.getText().toString());
                        mcrOrderProxie.setStrPHONE_NO(landline.getText().toString());
                        mcrOrderProxie.setStrEMAIL(email.getText().toString());

                        fragment = new CategoryBased_NewConn();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderData", mcrOrderProxie);
                        fragment.setArguments(bundle);

                    } else {

                        mcrOrderProxieOtherConn.setStrFIRST_NAME(firstname.getText().toString());
                        mcrOrderProxieOtherConn.setStrMIDDLE_NAME(middlename.getText().toString());
                        mcrOrderProxieOtherConn.setStrLAST_NAME(lastname.getText().toString());

                        mcrOrderProxieOtherConn.setStrTITLE(valTitle);
                        mcrOrderProxieOtherConn.setStrGENDER(valGender);

                        mcrOrderProxieOtherConn.setStrFATHER_NAME(fathersname.getText().toString());
                        mcrOrderProxieOtherConn.setStrsMOTHER_NAME(mothername.getText().toString());

                        mcrOrderProxieOtherConn.setStrHOUSE_NO(houseno.getText().toString());
                        mcrOrderProxieOtherConn.setStrBUILDING_NAME(buildingname.getText().toString());
                        mcrOrderProxieOtherConn.setStrSTREET(street.getText().toString());
                        mcrOrderProxieOtherConn.setStrAREA(area.getText().toString());
                        mcrOrderProxieOtherConn.setStrPIN(pin.getText().toString());
                        mcrOrderProxieOtherConn.setStrLANDMARK(landmark.getText().toString());
                        mcrOrderProxieOtherConn.setStrMOBILE_NO(mobilenum.getText().toString());
                        mcrOrderProxieOtherConn.setStrPHONE_NO(landline.getText().toString());
                        mcrOrderProxieOtherConn.setStrEMAIL(email.getText().toString());

                        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U02")) { //name change
                            fragment = new CategoryBased_NameChange();
                        } else if (userPreferences.getLocalMsgId().equalsIgnoreCase("U03") || userPreferences.getLocalMsgId().equalsIgnoreCase("U04")) { //address chnage
                            fragment = new CategoryBased_LoadChange();
                        } else if (userPreferences.getLocalMsgId().equalsIgnoreCase("U05") || userPreferences.getLocalMsgId().equalsIgnoreCase("U06")) { //category change
                            fragment = new CategoryBased_CategoryChange();
                        } else {
                            fragment = new CategoryBased_NewConn();
                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderData", mcrOrderProxieOtherConn);
                        fragment.setArguments(bundle);
                    }

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                    fragmentTransaction.commit();

                } else {


                }
            }
        });


        scanmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return rootView;
    }


    private boolean checkValidation() {


        if (valTitle.isEmpty() || valGender.isEmpty() || fathersname.getText().toString().trim().isEmpty()
                || houseno.getText().toString().trim().isEmpty() || pin.getText().toString().trim().isEmpty()
                || mobilenum.getText().toString().trim().isEmpty()) {

            Snackbar.make(getView(), "Please check mendatory field !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if ((!ApplicationUtil.getInstance().isMobileCorrect(mobilenum.getText().toString().trim()) || mobilenum.getText().toString().trim().length() < 10)) {
            Snackbar.make(getView(), "Enter correct mobile number !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        } else if (!email.getText().toString().trim().isEmpty()) {
            if (!ApplicationUtil.getInstance().isValidMail(email.getText().toString().trim())) {
                Snackbar.make(getView(), "Enter correct email ID !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private class punchDataWS extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // webservicePG.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Validating...");
            progressDialog.incrementProgressBy(20);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            punchingDataBean = new PunchingDataBean();

            SealValidateProxie sealValidateProxie = new SealValidateProxie();
            sealValidateProxie.setSealNo(dropterminalseal1.getSelectedItem().toString());
            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {
                //  punchingDataBean = ApplicationUtil.getInstance().getWebservice().checkSealData(sealValidateProxie, getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            try {
                progressDialog.dismiss();

                if (punchingDataBean.getACK().equalsIgnoreCase("true")) {

                    sealValidateVal = "true";
                    final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.promt_message_success);

                    TextView alert_title2 = (TextView) dialog.findViewById(R.id.alert_title2);
                    TextView closeDialog = (TextView) dialog.findViewById(R.id.closePrompt);
                    alert_title2.setText("Seal successfully validated !!");

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                } else if (punchingDataBean.getACK().equalsIgnoreCase("consumed")) {

                    dropterminalseal1.setSelection(0);
                    sealValidateVal = "consume";

                    Snackbar.make(getView(), "Seal has been consumed already !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    dropterminalseal1.setSelection(0);
                    sealValidateVal = "false";

                    Snackbar.make(getView(), "Seal is not available !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
        }


        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }


}
