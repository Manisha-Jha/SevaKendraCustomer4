package com.bses.dinesh.dsk.fragLoadChange;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.bses.dinesh.dsk.fragMain.PhotosAndID;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.proxies.SealValidateProxie;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Created by Krishna on 4/29/2016.
 */
public class CategoryBased_LoadChange extends Fragment {

    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    //private McrOrederProxie orderData;
    public static EditText devicenumber;
    private EditText stickernumber, busnumber, drumnumber, runninglengthfrom,
            runninglengthto,cablelength, outputcablelength, outputbuslength
            , drumnumberbb, runninglengthfrombb, runninglengthtobb, cablelengthbb;
    private RadioGroup radioInstalledBus,radiocableinstalltype, radioelcbinstall, radiostickerinstall , radiocableinstalltypebb, radiocustomerbus ;
    private String valInstalledBus, valInstallType, valelcbinstall, valdropreason, valStickerinstall ,valInstallTypebb, valcustomerinstall ;
    private Spinner dropbussize, dropbussizecust, dropcablesize, dropreason, dropreasonre;
    private Spinner dropterminalseal1, dropterminalseal2, dropmeterseal1, dropmeterseal2,
    dropbusbarseal1, dropbusbarseal2, busbarcablesize;
    public static Button validateseal,scanmeter;
    public static String meterscannedVal = "No", dropbussizecustVal = "";
    private ProgressDialog progressDialog;
    private PunchingDataBean punchingDataBean;
    private String sealValidateVal = "false";
    private int from, to, frombb, tobb;
    //private DBHandler db;

    private  String[] seals, objectsSub;
    private ArrayList<String> sealUsed = new ArrayList<String>(6);
    private UserPreferences userPreferences;
    private String printseal1, printseal2, printseal3, printseal4, printseal5, printseal6;
    private String elcbSubmitVal="";

    //New
    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private EditText existloadval, requiredloadval, voltage, phase;
    private String  valMeterChoice, valRadioLoad;
    private RadioGroup  radiometerchoice, radioload;
    private String valMeterChoiceSubmit="" , valRadioLoadSubmit = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.category_loadchange, container, false);
        progressDialog = new ProgressDialog(getActivity());

       // db = new DBHandler(getActivity());
        userPreferences = UserPreferences.getInstance(getActivity());
        sealUsed = new ArrayList<String>(6);
        sealUsed.add(" "); sealUsed.add(" "); sealUsed.add(" "); sealUsed.add(" "); sealUsed.add(" "); sealUsed.add(" ");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LOAD CHANGE");
        Bundle bundle = getArguments();
        //orderData = (McrOrederProxie) bundle.getSerializable("orderData");

        existloadval = (EditText) rootView.findViewById(R.id.existloadval);
         // seva kendra
        requiredloadval = (EditText) rootView.findViewById(R.id.requiredloadval);


        if(userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
            mcrOrderProxie = (McrOrderProxie) bundle.getSerializable("orderData");
        }
        else{
            mcrOrderProxieOtherConn =  (McrOrderProxieOtherConn) bundle.getSerializable("orderData");
            requiredloadval.setText(mcrOrderProxieOtherConn.getStrRLOAD_S4());
            existloadval.setText(mcrOrderProxieOtherConn.getStrExistingLoad());
        }


        voltage = (EditText) rootView.findViewById(R.id.voltage);
        phase = (EditText) rootView.findViewById(R.id.phase);

        Double loadvalue = Double.parseDouble(mcrOrderProxieOtherConn.getStrRLOAD_S4());

        if(loadvalue<10.0){
            phase.setText("1");
        }
        else{
            phase.setText("3");
        }

        radiometerchoice = (RadioGroup) rootView.findViewById(R.id.radiometerchoice);
        valMeterChoice = ((RadioButton)rootView.findViewById(radiometerchoice.getCheckedRadioButtonId())).getText().toString();
        valMeterChoiceSubmit = "01";

        radiometerchoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valMeterChoice = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valMeterChoice.equalsIgnoreCase("BSES Meter")){
                    valMeterChoiceSubmit = "01";
                }
                if(valMeterChoice.equalsIgnoreCase("Applicant's Meter")){
                    valMeterChoiceSubmit = "02";
                }
                else{
                    valMeterChoiceSubmit = "01";
                }
            }
        });


        radioload = (RadioGroup) rootView.findViewById(R.id.radioload);
        valRadioLoad = ((RadioButton)rootView.findViewById(radioload.getCheckedRadioButtonId())).getText().toString();

        radioload.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valRadioLoad = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valRadioLoad.equalsIgnoreCase("KW")){
                    valRadioLoadSubmit = "KW";
                }
                if(valRadioLoad.equalsIgnoreCase("KVA")){
                    valRadioLoadSubmit = "KVA";
                }
                else{
                    valRadioLoadSubmit = "KW";
                }
            }
        });


        scanmeter = (Button) rootView.findViewById(R.id.scanmeter);

       /* if(!userPreferences.getEmail().toString().equalsIgnoreCase("")){
            scanmeter.setText("Verified");
        }*/

        devicenumber = (EditText) rootView.findViewById(R.id.devicenumber);


        radiostickerinstall = (RadioGroup) rootView.findViewById(R.id.radiostickerinstall);
        valStickerinstall = ((RadioButton)rootView.findViewById(radiostickerinstall.getCheckedRadioButtonId())).getText().toString();

        final LinearLayout stickerll = (LinearLayout) rootView.findViewById(R.id.stickerll);
        stickernumber = (EditText) rootView.findViewById(R.id.stickernumber);

        radiostickerinstall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valStickerinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valStickerinstall.equalsIgnoreCase("Yes")){
                    stickerll.setVisibility(View.VISIBLE);
                }
                else{
                    stickerll.setVisibility(View.GONE);
                    stickernumber.setText("");
                }
            }
        });





        //devicenumber.setText(orderData.getDEVICENO());
       // devicenumber.setText("Request Number -"+mcrOrderProxie.getStrORDER_NO());

        radioelcbinstall = (RadioGroup) rootView.findViewById(R.id.radioelcbinstall);
        valelcbinstall = ((RadioButton)rootView.findViewById(radioelcbinstall.getCheckedRadioButtonId())).getText().toString();

        final LinearLayout elcbbasedll = (LinearLayout) rootView.findViewById(R.id.elcbbasedll);

        radioelcbinstall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valelcbinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valelcbinstall.equalsIgnoreCase("Yes")){
                   // elcbbasedll.setVisibility(View.VISIBLE);
                    valelcbinstall = "Yes";
                }
                else{

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
        valInstallTypebb = ((RadioButton)rootView.findViewById(radiocableinstalltypebb.getCheckedRadioButtonId())).getText().toString();

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
                }
                else {
                    if (!runninglengthtobb.getText().toString().trim().isEmpty()) {


                        frombb = Integer.parseInt(runninglengthfrombb.getText().toString().trim());
                        tobb = Integer.parseInt(runninglengthtobb.getText().toString().trim());

                        if(frombb < tobb || frombb == tobb) {
                            int result = tobb - frombb;
                            if (result < 0) {
                                // return false;
                            } else {
                                cablelengthbb.setText(Integer.toString(result));
                            }
                        }
                        else{
                            Snackbar.make(getView() , "From length should be less than to length!!", Snackbar.LENGTH_LONG)
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
        valInstalledBus = ((RadioButton)rootView.findViewById(radioInstalledBus.getCheckedRadioButtonId())).getText().toString();
        final LinearLayout radiocustomerbusll = (LinearLayout) rootView.findViewById(R.id.radiocustomerbusll);

        radioInstalledBus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstalledBus = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valInstalledBus.equalsIgnoreCase("Yes")){
                    radiocustomerbusll.setVisibility(View.GONE);

                    busbarmainll.setVisibility(View.VISIBLE);
                    dropbussizecustVal = "";
                }

                else if(valInstalledBus.equalsIgnoreCase("Old")){

                    busbarmainll.setVisibility(View.VISIBLE);
                    radiocustomerbusll.setVisibility(View.VISIBLE);

                }
                else{

                    busbarmainll.setVisibility(View.GONE);
                    radiocustomerbusll.setVisibility(View.GONE);
                    dropbussizecustVal = "";
                }
            }
        });


        radiocustomerbus = (RadioGroup) rootView.findViewById(R.id.radiocustomerbus);
        radiocustomerbus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valcustomerinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if(valcustomerinstall.equalsIgnoreCase("BSES Bus-Bar")){
                    // elcbbasedll.setVisibility(View.VISIBLE);
                    dropbussizecustVal = "";
                    busbarcustomerll.setVisibility(View.GONE);
                    busbarll.setVisibility(View.VISIBLE);
                }
                else{
                    dropbussizecustVal = dropbussizecust.getSelectedItem().toString();
                    busbarcustomerll.setVisibility(View.VISIBLE);
                    busbarll.setVisibility(View.GONE);
                }
            }
        });

        dropreason = (Spinner) rootView.findViewById(R.id.dropreason);
        dropreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    valdropreason = "01";
                }
                else{
                    valdropreason = "01";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropreasonre = (Spinner) rootView.findViewById(R.id.dropreasonre);
        dropreasonre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    valdropreason = "09";
                }
                else{
                    valdropreason = "09";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    /*    if(mcrOrderProxie.getPM_ACTIVITY() != null){
            if(mcrOrderProxie.getPM_ACTIVITY().equalsIgnoreCase("T02")) {
                dropreasonre.setVisibility(View.VISIBLE);
                dropreason.setVisibility(View.GONE);
            }

            else{
                dropreason.setVisibility(View.VISIBLE);
                dropreasonre.setVisibility(View.GONE);
            }
        }*/

        dropcablesize = (Spinner) rootView.findViewById(R.id.dropcablesize);

        drumnumber = (EditText) rootView.findViewById(R.id.drumnumber);

        outputcablelength = (EditText) rootView.findViewById(R.id.outputcablelength);

        radiocableinstalltype = (RadioGroup) rootView.findViewById(R.id.radiocableinstalltype);

        valInstallType = ((RadioButton)rootView.findViewById(radiocableinstalltype.getCheckedRadioButtonId())).getText().toString();

        radiocableinstalltype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstallType = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });

        runninglengthfrom = (EditText) rootView.findViewById(R.id.runninglengthfrom);
        runninglengthto = (EditText) rootView.findViewById(R.id.runninglengthto);
        cablelength = (EditText) rootView.findViewById(R.id.cablelength);
       /*runninglengthfrom.addTextChangedListener(new MyTextWatcher(runninglengthfrom));
        runninglengthto.addTextChangedListener(new MyTextWatcher(runninglengthto));
        cablelength.addTextChangedListener(new MyTextWatcher(cablelength));*/

        cablelength.setEnabled(true);
        cablelength.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (runninglengthfrom.getText().toString().trim().isEmpty()) {
                    return false;
                }
                else {
                    if (!runninglengthto.getText().toString().trim().isEmpty()) {

                        from = Integer.parseInt(runninglengthfrom.getText().toString().trim());
                        to = Integer.parseInt(runninglengthto.getText().toString().trim());

                        if(from < to || from == to) {
                            int result = to - from;
                            if (result < 0) {
                               // return false;
                            } else {
                                cablelength.setText(Integer.toString(result));
                            }
                        }
                        else{
                            Snackbar.make(getView() , "From length should be less than to length!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            cablelength.setText("");
                            runninglengthto.setText("");
                        }
                    }
                }
                return false;
            }
        });

        // -- need


     /*   try{
             mcrSeals = new ArrayList<McrSeal>(db.getAllSeals());
             seals = new String[mcrSeals.size()+1];
             objectsSub = new String[mcrSeals.size()+1];

            seals[0] = "Select";
            objectsSub[0] = " ";

            for(int i=0; i<mcrSeals.size();i++){
                seals[i+1] = mcrSeals.get(i).getSEAL_NUMBER();
                objectsSub[i+1] = " ";
            }
        }
        catch (Exception er){
            er.printStackTrace();
        }*/

        validateseal = (Button) rootView.findViewById(R.id.validateseal);

        /*dropterminalseal1 = (Spinner) rootView.findViewById(R.id.dropterminalseal1);
        dropterminalseal1.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropterminalseal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(0, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=0) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropterminalseal2 = (Spinner) rootView.findViewById(R.id.dropterminalseal2);
        dropterminalseal2.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropterminalseal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(1, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=1) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropmeterseal1 = (Spinner) rootView.findViewById(R.id.dropmeterseal1);
        dropmeterseal1.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropmeterseal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(2, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=2) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dropmeterseal2 = (Spinner) rootView.findViewById(R.id.dropmeterseal2);
        dropmeterseal2.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropmeterseal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(3, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=3) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropbusbarseal1 = (Spinner) rootView.findViewById(R.id.dropbusbarseal1);
        dropbusbarseal1.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropbusbarseal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(4, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=4) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dropbusbarseal2 = (Spinner) rootView.findViewById(R.id.dropbusbarseal2);
        dropbusbarseal2.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner, seals,objectsSub));
        dropbusbarseal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sealUsed.set(5, parent.getSelectedItem().toString());

                for(int i=0; i<sealUsed.size();i++){
                    if(i!=5) {
                        if (sealUsed.get(i).equalsIgnoreCase(parent.getSelectedItem().toString()) && parent.getSelectedItemPosition() != 0) {
                            Snackbar.make(getView(), "Seal is being used!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            parent.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        // -- need

        /*pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        inputLayoutOldPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_old_password);
        inputOldPassword = (EditText) rootView.findViewById(R.id.input_old_password);

        inputLayoutNewPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_new_password);
        inputNewPassword = (EditText) rootView.findViewById(R.id.input_new_password);

        inputLayoutReNewPassword = (TextInputLayout) rootView.findViewById(R.id.reinput_layout_new_password);
        inputReNewPassword = (EditText) rootView.findViewById(R.id.reinput_new_password);

        changePassSubmit = (Button) rootView.findViewById(R.id.btn_change_pass_submit);*/

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


               if(checkValidation()) {

                   mcrOrderProxieOtherConn.setStrELOAD_S4(existloadval.getText().toString());
                   mcrOrderProxieOtherConn.setStrRLOAD_S4(requiredloadval.getText().toString());
                   mcrOrderProxieOtherConn.setStrMETERCHOICE_S4(valMeterChoiceSubmit);
                   mcrOrderProxieOtherConn.setStrVLEVEL_S4(voltage.getText().toString());
                   mcrOrderProxieOtherConn.setStrPHASE_S4(phase.getText().toString());


                   Fragment fragment = new PhotosAndID();
                   Bundle bundle = new Bundle();
                   bundle.putSerializable("orderData", mcrOrderProxieOtherConn);
                   fragment.setArguments(bundle);

                   FragmentManager fm = getFragmentManager();
                   FragmentTransaction fragmentTransaction = fm.beginTransaction();
                   fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                   fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                   fragmentTransaction.commit();

               }
               else{
               }

           }
       });

       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((stickernumber.getText().toString().equalsIgnoreCase("") && valStickerinstall.equalsIgnoreCase("Yes")) || (cablelength.getText().toString().equalsIgnoreCase(""))
                         ||  dropterminalseal1.getSelectedItemPosition()==0

                        ) {

                    Snackbar.make(getView() , "Check mandatory fields!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                else {

                    if(runninglengthto.getText().toString().equalsIgnoreCase("") && runninglengthfrom.getText().toString().equalsIgnoreCase("")){
                        submitValues();
                    }

                    else{
                        int diff = (to - from);
                        String strDiff = Integer.toString(diff);

                        if(strDiff.equalsIgnoreCase(cablelength.getText().toString())){

                            submitValues();

                        }
                        else{

                            Snackbar.make(getView() , "Check running length From/ To !!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    }

                }
            }
        });*/


        scanmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //need
              //  Intent intent = new Intent(getActivity(), BSESScanner.class);
              //  startActivity(intent);
            }
        });

        return rootView;
    }



    private boolean checkValidation(){




        if(valMeterChoiceSubmit.isEmpty() ||
                existloadval.getText().toString().trim().isEmpty() || requiredloadval.getText().toString().trim().isEmpty() ){

            Snackbar.make(getView() , "Please check mandatory field !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }


        else{
            return true;
        }
    }

    private void submitValues(){


        final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.promt_seal_recheck);

        TextView alert_title2 = (TextView) dialog.findViewById(R.id.alert_title2);
        TextView done = (TextView) dialog.findViewById(R.id.closePrompt);
        TextView closeDialog = (TextView) dialog.findViewById(R.id.cancel);
        TextView seal1 = (TextView) dialog.findViewById(R.id.requestid1);
        TextView seal2 = (TextView) dialog.findViewById(R.id.requestid2);
        TextView seal3 = (TextView) dialog.findViewById(R.id.requestid3);
        TextView seal4 = (TextView) dialog.findViewById(R.id.requestid4);
        TextView seal5 = (TextView) dialog.findViewById(R.id.requestid5);
        TextView seal6 = (TextView) dialog.findViewById(R.id.requestid6);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(dropterminalseal1.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal1 = "";
        }
        else{
            printseal1 = dropterminalseal1.getSelectedItem().toString();
        }


        if(dropterminalseal2.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal2 = "";
        }
        else{
            printseal2 = dropterminalseal2.getSelectedItem().toString();
        }


        if(dropmeterseal1.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal3 = "";
        }
        else{
            printseal3 = dropmeterseal1.getSelectedItem().toString();
        }


        if(dropmeterseal2.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal4 = "";
        }
        else{
            printseal4 = dropmeterseal2.getSelectedItem().toString();
        }



        if(dropbusbarseal1.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal5 = "";
        }
        else{
            printseal5 = dropbusbarseal1.getSelectedItem().toString();
        }


        if(dropbusbarseal2.getSelectedItem().toString().equalsIgnoreCase("Select")){
            printseal6 = "";
        }
        else{
            printseal6 = dropbusbarseal2.getSelectedItem().toString();
        }

        alert_title2.setText("* Please verify your selected seals\n\n");

        seal1.setText("Terminal Seal-1 : "+printseal1);
        seal2.setText("Terminal Seal-2 : "+printseal2);
        seal3.setText( "MeterBox Seal-1 : "+printseal3);
        seal4.setText("MeterBox Seal-2 : "+printseal4);
        seal5.setText("BusBar Seal-1 : "+printseal5);
        seal6.setText("BusBar Seal-2 : "+printseal6);


        /* messageTitle.setText(msg.getMSGTITLE());
           messageDate.setText(msg.getMSGDATE());
           messageDescription.setText(msg.getMSGDESC());*/

        // requestid.setText(broadcastMsgBean.getACK());


        /*done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                orderData.setDEVICENO(devicenumber.getText().toString());
                orderData.setOTHERSTICKER(stickernumber.getText().toString());

                orderData.setOTHER4(valelcbinstall);// ELCB installed yes no

                orderData.setINSTALLEDBUSBAR(valInstalledBus);
                orderData.setBUSBARSIZE(dropbussize.getSelectedItem().toString());
                orderData.setBUSBARNUMBER(busnumber.getText().toString());
                orderData.setDRUMSIZE(drumnumber.getText().toString());


                orderData.setOTHER5(valdropreason); // activity reason 01 for new connection
                orderData.setCABLESIZE2(dropcablesize.getSelectedItem().toString());
                orderData.setCABLEINSTALLTYPE(valInstallType);
                orderData.setRUNNINGLENGTHFROM(runninglengthfrom.getText().toString());
                orderData.setRUNNINGLENGTHTO(runninglengthto.getText().toString());
                orderData.setCABLELENGTH(cablelength.getText().toString());

                orderData.setTERMINALSEAL1(dropterminalseal1.getSelectedItem().toString());
                orderData.setTERMINALSEAL2(dropterminalseal2.getSelectedItem().toString());
                orderData.setMETERBOXSEAL1(dropmeterseal1.getSelectedItem().toString());
                orderData.setMETERBOXSEAL2(dropmeterseal2.getSelectedItem().toString());
                orderData.setBUSBARSEAL1(dropbusbarseal1.getSelectedItem().toString());
                orderData.setBUSBARSEAL2(dropbusbarseal2.getSelectedItem().toString());

                orderData.setOUTPUTCABLELENGTH(outputcablelength.getText().toString());
                orderData.setPUNCHED(busbarcablesize.getSelectedItem().toString());   // busbarcablesize

                orderData.setOUTPUTBUSLENGTH(cablelengthbb.getText().toString());  // use bus bar cable length

                // newly added jan 4, 2018
                orderData.setDRUMNUMBERBB(drumnumberbb.getText().toString());
                orderData.setVALINSTALLTYPEBB(valInstallTypebb);
                orderData.setRUNNINGLENGTHFROMBB(runninglengthfrombb.getText().toString());
                orderData.setRUNNINGLENGTHTOBB(runninglengthtobb.getText().toString());
                orderData.setELCBSUBMITVAL(valelcbinstall);
                orderData.setMETERSCANNEDVAL(meterscannedVal);

                // customer bus bar add
                orderData.setEXTRA2(dropbussizecustVal);


                Fragment fragment = new OtherDetails();
                // fragment.setArguments(bundle);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData", orderData);
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                fragmentTransaction.commit();
            }
        });*/

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

              /*  mcrOrderProxie.setACTVTY_RSN(valdropreason);
                mcrOrderProxie.setTF_STCKR_STUS(valStickerinstall.toString());

                if(valStickerinstall.toString().equalsIgnoreCase("Yes")){
                    mcrOrderProxie.setTF_STCKR_NO(stickernumber.getText().toString());
                }

                else{
                    mcrOrderProxie.setTF_STCKR_NO("");
                }

                mcrOrderProxie.setELCB_INSLD_ON_SITE(valelcbinstall);
                mcrOrderProxie.setBUS_BAR_INSTALLD(valInstalledBus); //Bus Bar Installed Yes / No / Old

                if(!valInstalledBus.equalsIgnoreCase("No")){

                    mcrOrderProxie.setBUS_BAR_SIZE(dropbussize.getSelectedItem().toString());
                    mcrOrderProxie.setBUS_BAR_NO(busnumber.getText().toString());
                    mcrOrderProxie.setBUS_BAR_DRM_NO(drumnumberbb.getText().toString());
                    mcrOrderProxie.setBUS_BAR_CBL_INSTL_TYP(valInstallTypebb);
                    mcrOrderProxie.setBUS_BR_RUN_LENTH_FRM(runninglengthfrombb.getText().toString());
                    mcrOrderProxie.setBUS_BR_RUN_LENTH_TO(runninglengthtobb.getText().toString());
                    mcrOrderProxie.setBUS_BR_CBL_LNGTH(cablelengthbb.getText().toString());
                    mcrOrderProxie.setBUS_BAR_B_OR_C(valcustomerinstall);

                }

                else{
                    mcrOrderProxie.setBUS_BAR_SIZE("");
                    mcrOrderProxie.setBUS_BAR_NO("");
                    mcrOrderProxie.setBUS_BAR_DRM_NO("");
                    mcrOrderProxie.setBUS_BAR_CBL_INSTL_TYP("");
                    mcrOrderProxie.setBUS_BR_RUN_LENTH_FRM("");
                    mcrOrderProxie.setBUS_BR_RUN_LENTH_TO("");
                    mcrOrderProxie.setBUS_BR_CBL_LNGTH("");
                    mcrOrderProxie.setBUS_BAR_B_OR_C("");
                }

                mcrOrderProxie.setCBL_SIZE(dropcablesize.getSelectedItem().toString());
                mcrOrderProxie.setCBL_DRM_NUMBR(drumnumber.getText().toString());
                mcrOrderProxie.setCABLEINSTALLTYPE(valInstallType);
                mcrOrderProxie.setCBL_RUN_LENTH_FRM(runninglengthfrom.getText().toString());
                mcrOrderProxie.setCBL_RUN_LENTH_TO(runninglengthto.getText().toString());
                mcrOrderProxie.setCABLELENGTH(cablelength.getText().toString());
                mcrOrderProxie.setOUTPUTCABLELENGTH(outputcablelength.getText().toString());
                mcrOrderProxie.setTERMINALSEAL1(dropterminalseal1.getSelectedItem().toString());
                mcrOrderProxie.setTERMINALSEAL2(dropterminalseal2.getSelectedItem().toString());
                mcrOrderProxie.setMETERBOXSEAL1(dropmeterseal1.getSelectedItem().toString());
                mcrOrderProxie.setMETERBOXSEAL2(dropmeterseal2.getSelectedItem().toString());
                mcrOrderProxie.setBUSBARSEAL1(dropbusbarseal1.getSelectedItem().toString());
                mcrOrderProxie.setBUSBARSEAL2(dropbusbarseal2.getSelectedItem().toString());
                mcrOrderProxie.setMETERSCANNEDVAL(meterscannedVal);
*/

                Fragment fragment = new CategoryBased_LoadChange();
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData", mcrOrderProxie);
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                fragmentTransaction.commit();
            }
        });

        dialog.show();

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
                case R.id.runninglengthfrom:
                    validateLengthFrom();
                    break;
                case R.id.runninglengthto:
                    validateLengthTo();
                    break;
                case R.id.cablelength:
                    validateCableLength();
                    break;
            }
        }
    }


    private boolean validateLengthFrom() {

        if (runninglengthfrom.getText().toString().trim().isEmpty()) {

            return false;
        }

        else {
            if (!runninglengthto.getText().toString().trim().isEmpty()) {

                int from = Integer.parseInt(runninglengthfrom.getText().toString().trim());
                int to = Integer.parseInt(runninglengthto.getText().toString().trim());
                int result = from - to;
                if(result < 1){
                    return false;
                }
                else {
                    cablelength.setText(Integer.toString(result));
                }
            }
        }
        return true;
    }


    private boolean validateLengthTo() {

        if (runninglengthto.getText().toString().trim().isEmpty()) {
            return false;
        }

        else {
            if (!runninglengthfrom.getText().toString().trim().isEmpty()) {

                int from = Integer.parseInt(runninglengthfrom.getText().toString().trim());
                int to = Integer.parseInt(runninglengthto.getText().toString().trim());
                int result = from - to;
                 if(result < 1){
                     return false;
                 }
                 else {
                     cablelength.setText(Integer.toString(result));
                 }
            }
        }

        return true;
    }


    private boolean validateCableLength() {

        if (runninglengthto.getText().toString().trim().isEmpty()) {
            return false;
        }

        else {
            if (!runninglengthfrom.getText().toString().trim().isEmpty()) {

                int from = Integer.parseInt(runninglengthfrom.getText().toString().trim());
                int to = Integer.parseInt(runninglengthto.getText().toString().trim());
                int result = from - to;
                if(result < 1){
                    return false;
                }
                else {
                    cablelength.setText(Integer.toString(result));
                }
            }
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
            }
            catch (Exception e) {
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
                }

                else if (punchingDataBean.getACK().equalsIgnoreCase("consumed")) {

                    dropterminalseal1.setSelection(0);
                    sealValidateVal = "consume";

                    Snackbar.make(getView() , "Seal has been consumed already !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else {
                    dropterminalseal1.setSelection(0);
                    sealValidateVal = "false";

                    Snackbar.make(getView() , "Seal is not available !!", Snackbar.LENGTH_LONG)
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
