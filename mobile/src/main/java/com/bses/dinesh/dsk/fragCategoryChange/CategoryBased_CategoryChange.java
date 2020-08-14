package com.bses.dinesh.dsk.fragCategoryChange;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class CategoryBased_CategoryChange extends Fragment {

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

    //New
    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private Spinner spin_category, spin_purpose, spin_usage;
    private String[] usageArr, usageArrSub;
    private String spinUsageVal = "0";
    private EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_catchange, container, false);
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("CATEGORY CHANGE+");


        Bundle bundle = getArguments();
        //orderData = (McrOrederProxie) bundle.getSerializable("orderData");
        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
            mcrOrderProxie = (McrOrderProxie) bundle.getSerializable("orderData");
        } else {
            mcrOrderProxieOtherConn = (McrOrderProxieOtherConn) bundle.getSerializable("orderData");
        }


        // seva kendra


        spin_purpose = (Spinner) rootView.findViewById(R.id.spin_purpose);
        spin_usage = (Spinner) rootView.findViewById(R.id.spin_usage);


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

        //usageArr = getResources().getStringArray(R.array.usage_array);
        //usageArrSub = getResources().getStringArray(R.array.usage_array_sub);
        // seva kendra

        usageArr = getResources().getStringArray(R.array.usage_array0);
        usageArrSub = getResources().getStringArray(R.array.usage_array_sub0);

        // seva kendra

        spin_usage.setAdapter(new SpinnerAdapterFilter(getActivity(), R.layout.custom_spinner, usageArr, usageArrSub));
        spin_usage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    TextView pmTypeText = (TextView) view.findViewById(R.id.text_main_seen);
                    TextView pmTypeID = (TextView) view.findViewById(R.id.sub_text_seen);

                    spinUsageVal = pmTypeID.getText().toString();
                    //String compID = String.valueOf(reqId.getText().toString());
                } else {
                    spinUsageVal = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        description = (EditText) rootView.findViewById(R.id.description);


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

        radioInstalledBus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstalledBus = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if (valInstalledBus.equalsIgnoreCase("Yes")) {
                    radiocustomerbusll.setVisibility(View.GONE);

                    busbarmainll.setVisibility(View.VISIBLE);
                    dropbussizecustVal = "";
                } else if (valInstalledBus.equalsIgnoreCase("Old")) {

                    busbarmainll.setVisibility(View.VISIBLE);
                    radiocustomerbusll.setVisibility(View.VISIBLE);

                } else {

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

                if (valcustomerinstall.equalsIgnoreCase("BSES Bus-Bar")) {
                    // elcbbasedll.setVisibility(View.VISIBLE);
                    dropbussizecustVal = "";
                    busbarcustomerll.setVisibility(View.GONE);
                    busbarll.setVisibility(View.VISIBLE);
                } else {
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

        dropreasonre = (Spinner) rootView.findViewById(R.id.dropreasonre);
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
        cablelength.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (runninglengthfrom.getText().toString().trim().isEmpty()) {
                    return false;
                } else {
                    if (!runninglengthto.getText().toString().trim().isEmpty()) {

                        from = Integer.parseInt(runninglengthfrom.getText().toString().trim());
                        to = Integer.parseInt(runninglengthto.getText().toString().trim());

                        if (from < to || from == to) {
                            int result = to - from;
                            if (result < 0) {
                                // return false;
                            } else {
                                cablelength.setText(Integer.toString(result));
                            }
                        } else {
                            Snackbar.make(getView(), "From length should be less than to length!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            cablelength.setText("");
                            runninglengthto.setText("");
                        }
                    }
                }
                return false;
            }
        });


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


                mcrOrderProxieOtherConn.setStrPURPOSE_S5(spinUsageVal);
                mcrOrderProxieOtherConn.setStrDESC_S5(description.getText().toString());


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
        });


        return rootView;
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
