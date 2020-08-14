package com.bses.dinesh.dsk.fragMain;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.bses.dinesh.dsk.adapter.SpinnerAdapterFilter;
import com.bses.dinesh.dsk.bean.PunchingDataBean;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.proxies.SealValidateProxie;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Created by Krishna on 4/29/2016.
 */
public class CategoryBased_NewConn extends Fragment {

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

    private EditText loadval, voltage, phase, panno, aadharnum, voterid;
    private String valRadioServiceConn, valRadioBilling, valRadioAreaType, valRadioPremises, valMeterChoice, valRadioLoad;
    private RadioGroup radioconn_service, radiobilling, radio_areatype, radiopremises, radiometerchoice, radioload;
    private Spinner spin_category, spin_purpose, spin_usage;

    //New
    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private PowerManager.WakeLock mWakeLock;
    private String[] pmActiArr, pmActiArrSub, categoryArr, categoryArrSub, usageArr, usageArrSub;
    String spinCategoryVal, valRadioServiceConnSubmit = "", valRadioBillingSubmit = "", valRadioAreaTypeSubmit = "",
            valRadioPremisesSubmit = "", spinUsageVal = "", valMeterChoiceSubmit = "", valRadioLoadSubmit = "";

    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_newconn, container, false);
        try {


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
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("CATEGORY DETAILS");
            try {
                Bundle bundle = getArguments();
                //orderData = (McrOrederProxie) bundle.getSerializable("orderData");
                if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
                    mcrOrderProxie = (McrOrderProxie) bundle.getSerializable("orderData");
                } else {
                    mcrOrderProxieOtherConn = (McrOrderProxieOtherConn) bundle.getSerializable("orderData");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                categoryArr = getResources().getStringArray(R.array.category_array);
                categoryArrSub = getResources().getStringArray(R.array.category_array_sub);
                // seva kendra
                spin_category = (Spinner) rootView.findViewById(R.id.spin_category);
                Button button1 = (Button) rootView.findViewById(R.id.next2);
                radioconn_service = (RadioGroup) rootView.findViewById(R.id.radioconn_service);

                valRadioServiceConn = ((RadioButton) rootView.findViewById(radioconn_service.getCheckedRadioButtonId())).getText().toString();

                radiobilling = (RadioGroup) rootView.findViewById(R.id.radiobilling);
                valRadioBilling = ((RadioButton) rootView.findViewById(radiobilling.getCheckedRadioButtonId())).getText().toString();
                radio_areatype = (RadioGroup) rootView.findViewById(R.id.radio_areatype);
                valRadioAreaType = ((RadioButton) rootView.findViewById(radio_areatype.getCheckedRadioButtonId())).getText().toString();

                radiopremises = (RadioGroup) rootView.findViewById(R.id.radiopremises);
                valRadioPremises = ((RadioButton) rootView.findViewById(radiopremises.getCheckedRadioButtonId())).getText().toString();
                spin_purpose = (Spinner) rootView.findViewById(R.id.spin_purpose);

                spin_usage = (Spinner) rootView.findViewById(R.id.spin_usage);
                radiometerchoice = (RadioGroup) rootView.findViewById(R.id.radiometerchoice);
                valMeterChoice = ((RadioButton) rootView.findViewById(radiometerchoice.getCheckedRadioButtonId())).getText().toString();
                radioload = (RadioGroup) rootView.findViewById(R.id.radioload);
                valRadioLoad = ((RadioButton) rootView.findViewById(radioload.getCheckedRadioButtonId())).getText().toString();
                loadval = (EditText) rootView.findViewById(R.id.loadval);

                voltage = (EditText) rootView.findViewById(R.id.voltage);
                phase = (EditText) rootView.findViewById(R.id.phase);
                panno = (EditText) rootView.findViewById(R.id.panno);
                aadharnum = (EditText) rootView.findViewById(R.id.aadharnum);
                voterid = (EditText) rootView.findViewById(R.id.voterid);


                scanmeter = (Button) rootView.findViewById(R.id.scanmeter);


                devicenumber = (EditText) rootView.findViewById(R.id.devicenumber);


                radiostickerinstall = (RadioGroup) rootView.findViewById(R.id.radiostickerinstall);
                valStickerinstall = ((RadioButton) rootView.findViewById(radiostickerinstall.getCheckedRadioButtonId())).getText().toString();

                final LinearLayout stickerll = (LinearLayout) rootView.findViewById(R.id.stickerll);
                stickernumber = (EditText) rootView.findViewById(R.id.stickernumber);
                radioelcbinstall = (RadioGroup) rootView.findViewById(R.id.radioelcbinstall);
                valelcbinstall = ((RadioButton) rootView.findViewById(radioelcbinstall.getCheckedRadioButtonId())).getText().toString();

                final LinearLayout elcbbasedll = (LinearLayout) rootView.findViewById(R.id.elcbbasedll);

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
                runninglengthfrombb = (EditText) rootView.findViewById(R.id.runninglengthfrombb); // new
                runninglengthtobb = (EditText) rootView.findViewById(R.id.runninglengthtobb); // new
                cablelengthbb = (EditText) rootView.findViewById(R.id.cablelengthbb);

                cablelengthbb.setEnabled(true);

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
                runninglengthfrom = (EditText) rootView.findViewById(R.id.runninglengthfrom);
                runninglengthto = (EditText) rootView.findViewById(R.id.runninglengthto);
                cablelength = (EditText) rootView.findViewById(R.id.cablelength);

                cablelength.setEnabled(true);

                // -- need


                validateseal = (Button) rootView.findViewById(R.id.validateseal);


                spin_category.setAdapter(new SpinnerAdapterFilter(getActivity(), R.layout.custom_spinner, categoryArr, categoryArrSub));
                spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {


                            if (position != 0) {
                                TextView pmTypeText = (TextView) view.findViewById(R.id.text_main_seen);
                                TextView pmTypeID = (TextView) view.findViewById(R.id.sub_text_seen);

                                spinCategoryVal = pmTypeID.getText().toString();
                                //String compID = String.valueOf(reqId.getText().toString());
                            } else {
                                spinCategoryVal = "000";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                radioconn_service.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valRadioServiceConn = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valStickerinstall.equalsIgnoreCase("New Connection")) {
                            valRadioServiceConnSubmit = "NC";
                        }
                        if (valStickerinstall.equalsIgnoreCase("Existing(Dormant Reconnection)")) {
                            valRadioServiceConnSubmit = "DMNT";
                        }
                        if (valStickerinstall.equalsIgnoreCase("Permanent Connection")) {
                            valRadioServiceConnSubmit = "PMNT";
                        }
                        if (valStickerinstall.equalsIgnoreCase("Temporary")) {
                            valRadioServiceConnSubmit = "TMP";
                        } else {
                            valRadioServiceConnSubmit = "NC";
                        }
                    }
                });


                radiobilling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valRadioBilling = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valRadioBilling.equalsIgnoreCase("Pre-Paid")) {
                            valRadioBillingSubmit = "PRPAID";
                        }
                        if (valRadioBilling.equalsIgnoreCase("Post-Paid")) {
                            valRadioBillingSubmit = "PSTPAID";
                        } else {
                            valRadioBillingSubmit = "PRPAID";
                        }
                    }
                });


                radio_areatype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valRadioAreaType = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valRadioAreaType.equalsIgnoreCase("Govt. House/DDA Flats/CGHS Flat")) {
                            valRadioAreaTypeSubmit = "DDA_CGHS";
                        }
                        if (valRadioAreaType.equalsIgnoreCase("Jhuggi Jhopri Cluster")) {
                            valRadioAreaTypeSubmit = "JJCLUSTER";
                        }
                        if (valRadioAreaType.equalsIgnoreCase("Others")) {
                            valRadioAreaTypeSubmit = "OTHER";
                        } else {
                            valRadioAreaTypeSubmit = "DDA_CGHS";
                        }
                    }
                });


                radiopremises.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valRadioPremises = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valRadioPremises.equalsIgnoreCase("Owned")) {
                            valRadioPremisesSubmit = "01";
                        }
                        if (valRadioPremises.equalsIgnoreCase("Rented/Lease")) {
                            valRadioPremisesSubmit = "02";
                        }
                        if (valRadioPremises.equalsIgnoreCase("Company Provided")) {
                            valRadioPremisesSubmit = "03";
                        }
                        if (valRadioPremises.equalsIgnoreCase("Govt. Provided")) {
                            valRadioPremisesSubmit = "04";
                        }
                        if (valRadioPremises.equalsIgnoreCase("Others")) {
                            valRadioPremisesSubmit = "05";
                        } else {
                            valRadioPremisesSubmit = "01";
                        }
                    }
                });


                spin_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i == 0) {
                            usageArr = getResources().getStringArray(R.array.usage_array0);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub0);

                        } else if (i == 1) {
                            usageArr = getResources().getStringArray(R.array.usage_array1);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub1);
                        } else if (i == 2) {
                            usageArr = getResources().getStringArray(R.array.usage_array2);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub2);
                        } else if (i == 3) {
                            usageArr = getResources().getStringArray(R.array.usage_array3);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub3);
                        } else if (i == 4) {
                            usageArr = getResources().getStringArray(R.array.usage_array4);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub4);
                        } else if (i == 5) {
                            usageArr = getResources().getStringArray(R.array.usage_array5);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub5);
                        } else {
                            usageArr = getResources().getStringArray(R.array.usage_array0);
                            usageArrSub = getResources().getStringArray(R.array.usage_array_sub0);
                        }

                        spin_usage.setAdapter(new SpinnerAdapterFilter(getActivity(), R.layout.custom_spinner, usageArr, usageArrSub));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                usageArr = getResources().getStringArray(R.array.usage_array0);
                usageArrSub = getResources().getStringArray(R.array.usage_array_sub0);

                spin_usage.setAdapter(new SpinnerAdapterFilter(getActivity(), R.layout.custom_spinner, usageArr, usageArrSub));
                spin_usage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {


                            if (position != 0) {
                                TextView pmTypeText = (TextView) view.findViewById(R.id.text_main_seen);
                                TextView pmTypeID = (TextView) view.findViewById(R.id.sub_text_seen);

                                spinUsageVal = pmTypeID.getText().toString();
                                //String compID = String.valueOf(reqId.getText().toString());
                            } else {
                                spinUsageVal = "0";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                valMeterChoiceSubmit = "01";
                radiometerchoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valMeterChoice = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valMeterChoice.equalsIgnoreCase("BSES Meter")) {
                            valMeterChoiceSubmit = "01";
                        }
                        if (valMeterChoice.equalsIgnoreCase("Applicant's Meter")) {
                            valMeterChoiceSubmit = "02";
                        } else {
                            valMeterChoiceSubmit = "01";
                        }
                    }
                });


                valRadioLoadSubmit = "KW";
                radioload.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valRadioLoad = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                        if (valRadioLoad.equalsIgnoreCase("KW")) {
                            valRadioLoadSubmit = "KW";
                        }
                        if (valRadioLoad.equalsIgnoreCase("KVA")) {
                            valRadioLoadSubmit = "KVA";
                        } else {
                            valRadioLoadSubmit = "KW";
                        }
                    }
                });

                try {
                    if (mcrOrderProxie.getStrAPPLIED_LOAD() != null) {
                        loadval.setText(mcrOrderProxie.getStrAPPLIED_LOAD());
                        double loadvalue = Double.parseDouble(mcrOrderProxie.getStrAPPLIED_LOAD());
                        if (loadvalue < 10.0) {
                            phase.setText("1");
                        } else {
                            phase.setText("3");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                radiostickerinstall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        try {


                            valStickerinstall = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                            if (valStickerinstall.equalsIgnoreCase("Yes")) {
                                stickerll.setVisibility(View.VISIBLE);
                            } else {
                                stickerll.setVisibility(View.GONE);
                                stickernumber.setText("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    //devicenumber.setText(orderData.getDEVICENO());
                    devicenumber.setText("Request Number -" + mcrOrderProxie.getStrORDER_NO());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                radiocableinstalltypebb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valInstallTypebb = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
                    }
                });

                dropreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            valdropreason = "01";
                        } else {
                            valdropreason = "01";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dropreasonre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            valdropreason = "09";
                        } else {
                            valdropreason = "09";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                radiocableinstalltype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        valInstallType = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
                    }
                });


                validateseal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        punchDataWS task = new punchDataWS();
                        //Call execute
                        task.execute();
                    }
                });


                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (checkValidation()) {

                            if (userPreferences.getLocalMsgId().equalsIgnoreCase("U07")) {

                                mcrOrderProxieOtherConn.setStrPAN_NO(panno.getText().toString());
                                mcrOrderProxieOtherConn.setStrID_NO(voterid.getText().toString());

                                fragment = new PhotosAndID();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("orderData", mcrOrderProxieOtherConn);
                                fragment.setArguments(bundle);

                            } else {
                                mcrOrderProxie.setStrAPPLIED_CATEGORY(spinCategoryVal);
                                mcrOrderProxie.setStrSERVICE_REQ(valRadioServiceConnSubmit);
                                mcrOrderProxie.setStrBILLING_TYPE(valRadioBillingSubmit);
                                mcrOrderProxie.setStrAREA_TYPE(valRadioAreaTypeSubmit);
                                mcrOrderProxie.setStrPREMISES_TYPE(valRadioPremisesSubmit);
                                mcrOrderProxie.setStrPURPOSE(spinUsageVal);

                                mcrOrderProxie.setStrMETER_CHOICE(valMeterChoiceSubmit);
                                mcrOrderProxie.setStrLOAD_TYPE(valRadioLoadSubmit);
                                mcrOrderProxie.setStrAPPLIED_LOAD(loadval.getText().toString());
                                mcrOrderProxie.setStrAPPLIED_VOLTAGE_LVL(voltage.getText().toString());
                                mcrOrderProxie.setStrAPPLIED_PHASE(phase.getText().toString());
                                mcrOrderProxie.setStrPAN_NO(panno.getText().toString());
                                mcrOrderProxie.setStrAADHAR_NO(aadharnum.getText().toString());
                                mcrOrderProxie.setStrID_NO(voterid.getText().toString());

                                fragment = new PhotosAndID();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("orderData", mcrOrderProxie);
                                fragment.setArguments(bundle);
                            }

                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                            fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                            fragmentTransaction.commit();

                        }
                    }
                });


                scanmeter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    private boolean checkValidation() {


        if (spinCategoryVal.equalsIgnoreCase("000") || valRadioServiceConnSubmit.isEmpty() || valRadioBillingSubmit.isEmpty() || valRadioAreaTypeSubmit.isEmpty() ||
                valRadioPremisesSubmit.isEmpty() || spinUsageVal.equalsIgnoreCase("0") ||
                valMeterChoiceSubmit.isEmpty() || valRadioLoadSubmit.isEmpty() || loadval.getText().toString().trim().isEmpty()) {

            Snackbar.make(getView(), "Please check mandatory field !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
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
