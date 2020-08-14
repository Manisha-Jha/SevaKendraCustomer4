package com.bses.dinesh.dsk.fragMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.BroadcastMsgBean;
import com.bses.dinesh.dsk.bean.Message;
import com.bses.dinesh.dsk.bean.PunchingDataBean;
import com.bses.dinesh.dsk.proxies.BroadcastMsgProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.proxies.OrderCancelProxie;
import com.bses.dinesh.dsk.sevakendra.MainActivity;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.utils.ApplicationConstants;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.MyNetwork;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bses.dinesh.dsk.utils.ApplicationConstants.BaseURL;


/**
 * Created by Krishna on 4/29/2016.
 */
public class ChatHome extends Fragment {

    private List<Message> messageList = new ArrayList<>();
    private RecyclerView recyclerView, recyclerEmpView;

    private String startOrEndDate = "";
    private EditText messageDescription;
    private UserPreferences userPreferences;
    private String startDate = "", endDate = "";

    private ArrayList<Message> msgs;

    private List<Message> messageListByEmp = new ArrayList<>();
    private List<Message> msgListbyEmp = new ArrayList<Message>();
    private ProgressDialog progressDialog;
    private BroadcastMsgProxie broadcastMsgProxie;
    private BroadcastMsgBean broadcastMsgBean;
    String replyType = "R";
    private FrameLayout fm1, fm2, fm3;
    private boolean clickBool = false, titlebool = false;

    private Button title, send;
    private TextView getORDERNO_, getSTICKERNO_, getSTARTDATE_, getFINISHDATE_, getNAME_, getFATHERNAME_, getADDRESS_,
            getBP_NO_, getPOLENO_, getDIVISIONOFFICE_, getREQUESTTYPE_, getCABLESIZE_,
            getCABLELENGTH_, getPLANNERGROUP_, getACCOUNT_CLASS_, getSANCTIONED_LOAD_, getZDSS_, getCA_NO_;

    private EditText getMOBILENO_, getEmail_;
    private Button assign, cancel_order;
    private Dialog dialog;
    private String approval = "", response = "";
    String getORDERNO, getSTICKERNO, getSTARTDATE, getFINISHDATE, getNAME, getFATHERNAME, getADDRESS, getMOBILENO,
            getBP_NO, getPOLENO, getDIVISIONOFFICE, getREQUESTTYPE, getCABLESIZE,
            getCABLELENGTH, getPLANNERGROUP, getMETER_NO, getACCOUNT_CLASS, getSANCTIONED_LOAD, getZDSS, getCA_NO,
            getPM_ACTIVITY, getOLD_M_READING, getKWH_OLD, getKVAH_OLD,
            getKWH_OLD_DATE, getKW_OLD, getKVA_OLD, getKW_DATE_OLD, getILART, getBUKRS, getZZ_RLOAD, getZZ_VKONT, Existing_LOAD;

    private String getTITLE, getNAME_FIRST, getNAME_LAST, getSTREET, getHOUSE_NUM1, getSTR_SUPPL, getSTR_SUPPL3, POST_CODE1, getPINCODE;

    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    private View filterDialogView;
    public String[] resonValue, reasonSub, raw;
    public int[] compImg;
    private String reasonResult = "0";
    private AlertDialog filterAlert;
    private Button reasonpic;
    private Bitmap reasonpicbmp;
    private String resultreasonpic = "";
    private PunchingDataBean punchingDataBean;
    private EditText remark;
    private ByteArrayOutputStream bytes, bytesForDisplay;
    private String pictureImagePath = "";
    private Uri outputFileUri;
    private int kycVal = 0;
    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private Fragment fragment;

    private ProgressDialog pd;
    MyNetwork network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.promt_message_chat, container, false);

        ChangeToolTitle();
        // db = new DBHandler(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("REQUEST DETAILS");

        userPreferences = UserPreferences.getInstance(getActivity());

        getORDERNO = getArguments().getString("getORDERNO");
        getSTICKERNO = getArguments().getString("getSTICKERNO");
        getSTARTDATE = getArguments().getString("getSTARTDATE"); //appointment
        getFINISHDATE = getArguments().getString("getFINISHDATE");  // re appointmemt
        getNAME = getArguments().getString("getNAME");
        getFATHERNAME = getArguments().getString("getFATHERNAME");
        getADDRESS = getArguments().getString("getADDRESS");
        getMOBILENO = getArguments().getString("getMOBILENO");
        getBP_NO = getArguments().getString("getBP_NO");  // emial
        getILART = getArguments().getString("getILART");  // order type code
        userPreferences.setLocalMsgId(getILART);

        getBUKRS = getArguments().getString("getBUKRS");  // compcode
        getZZ_RLOAD = getArguments().getString("getZZ_RLOAD");  // load
        getZZ_VKONT = getArguments().getString("getZZ_VKONT");  // CA number


        getTITLE = getArguments().getString("getTITLE");
        getNAME_FIRST = getArguments().getString("getNAME_FIRST");
        getNAME_LAST = getArguments().getString("getNAME_LAST");
        getSTREET = getArguments().getString("getSTREET");
        getHOUSE_NUM1 = getArguments().getString("getHOUSE_NUM1");
        getSTR_SUPPL = getArguments().getString("getSTR_SUPPL");
        getSTR_SUPPL3 = getArguments().getString("getSTR_SUPPL3");
        POST_CODE1 = getArguments().getString("getPOST_CODE1");
        getPINCODE = getArguments().getString("getPINCODE");
        Existing_LOAD = getArguments().getString("Existing_LOAD");


        //webservicePG = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        //chatTitle = (TextView)rootView.findViewById(R.id.alert_title);
        getORDERNO_ = (TextView) rootView.findViewById(R.id.ordernumber);
        getSTICKERNO_ = (TextView) rootView.findViewById(R.id.stickernum);
        getSTARTDATE_ = (TextView) rootView.findViewById(R.id.startdate);
        getFINISHDATE_ = (TextView) rootView.findViewById(R.id.finishdate);
        getNAME_ = (TextView) rootView.findViewById(R.id.name);
        getFATHERNAME_ = (TextView) rootView.findViewById(R.id.fathername);
        getADDRESS_ = (TextView) rootView.findViewById(R.id.address);
        getMOBILENO_ = (EditText) rootView.findViewById(R.id.mobilenum);
        getBP_NO_ = (TextView) rootView.findViewById(R.id.bp);
        getCA_NO_ = (TextView) rootView.findViewById(R.id.ca);
        getPOLENO_ = (TextView) rootView.findViewById(R.id.polenum);

        getDIVISIONOFFICE_ = (TextView) rootView.findViewById(R.id.divoffice);
        getREQUESTTYPE_ = (TextView) rootView.findViewById(R.id.requesttype);
        getCABLESIZE_ = (TextView) rootView.findViewById(R.id.cablesize);
        getCABLELENGTH_ = (TextView) rootView.findViewById(R.id.cablelength);
        getPLANNERGROUP_ = (TextView) rootView.findViewById(R.id.plannergroup);

        getACCOUNT_CLASS_ = (TextView) rootView.findViewById(R.id.accountclass);
        getSANCTIONED_LOAD_ = (TextView) rootView.findViewById(R.id.sanctionload);
        getEmail_ = (EditText) rootView.findViewById(R.id.email);

        getORDERNO_.setText(getORDERNO);
        //getSTICKERNO_.setText(getSTICKERNO);


        if (getSTARTDATE != null) {
            if (getSTARTDATE.length() > 9) {
                String[] arr = getSTARTDATE.substring(0, 10).split("-");
                getSTARTDATE_.setText(arr[2] + "-" + arr[1] + "-" + arr[0]);
            } else {
                getSTARTDATE_.setText("NA");
            }
        }

        if (getFINISHDATE != null) {
            if (getFINISHDATE.length() > 9) {
                String[] arr = getFINISHDATE.substring(0, 10).split("-");
                getFINISHDATE_.setText(arr[2] + "-" + arr[1] + "-" + arr[0]);
            } else {
                getFINISHDATE_.setText("NA");
            }
        }

        getNAME_.setText(getNAME);
        getFATHERNAME_.setText(getFATHERNAME);
        getADDRESS_.setText(getADDRESS);

        getMOBILENO_.setText(getMOBILENO);
        getMOBILENO_.setEnabled(false);

        getEmail_.setText(getBP_NO);
        getEmail_.setEnabled(false);


        assign = (Button) rootView.findViewById(R.id.assign);
        cancel_order = (Button) rootView.findViewById(R.id.cancel_order);

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (kycVal == 0) {
                    kycVal = 1;
                } else {
                    kycVal = 0;
                }

                getMOBILENO_.setFocusable(true);
                getMOBILENO_.setEnabled(true);
                getEmail_.setFocusable(true);
                getEmail_.setEnabled(true);

            }
        });


        assign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                insertOrderOpenTime(userPreferences.getUserid(), getORDERNO, current_time);
               /* try {


                        if (network.isOnline())  {
                            pd = new ProgressDialog(getActivity());
                            pd.setTitle("Please wait...");
                            pd.setMessage("Fetching data....");
                            pd.setIndeterminate(true);
                            pd.setCancelable(false);
                            pd.show();

                            final String REQ_TAG = "VACTIVITY";
                            RequestQueue queue = Volley.newRequestQueue(getActivity());

                            JSONObject map = new JSONObject();
                            try {
                                map.put("DateTime",mDatetime() );


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstants.BaseURL + ApplicationConstants.DateTime, map,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                if (pd.isShowing())
                                                    pd.dismiss();
                                                if (response.getString("Status").equals("Success")) {


                                                    mMoveToNext();


                                                } else {

                                                    Toast.makeText(getActivity(), "Invalid Request", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }

                                    if (error instanceof NoConnectionError) {
                                        Toast.makeText(getActivity(), "Network connection error", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof TimeoutError) {
                                        Toast.makeText(getActivity(), "Network connection TimeOut", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof AuthFailureError) {
                                        Toast.makeText(getActivity(), "Authfailure Error", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof ServerError) {
                                        Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getActivity(), "Parcer error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    60 * 1000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            jsonObjectRequest.setTag(REQ_TAG);
                            queue.add(jsonObjectRequest);
                        }else {
                            Toast.makeText(getActivity(),"Please check Internet Connection",Toast.LENGTH_LONG).show();
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (pd.isShowing())
                        pd.dismiss();
                }
*/
            }

        });

        TextView chatTitle = (TextView) rootView.findViewById(R.id.alert_title);

        return rootView;
    }

    private void insertOrderOpenTime(String user_id, String order_num, String open_time) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.insert_order_open_time(order_num, user_id, open_time);
        call.enqueue(new Callback<RetrofitResponse>() {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response) {
                Log.e("INSERT", response.raw() + "");
                RetrofitResponse jsonResponse = response.body();
                if (jsonResponse.Key.equals("Success")) {
                    Toast.makeText(getActivity(), jsonResponse.Key, Toast.LENGTH_SHORT).show();

                    mMoveToNext();

                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage() + "");
            }
        });
    }


    private void mMoveToNext() {
        try {

            // need to be update
            fragment = new NameAndAddress();

            if (getILART.equalsIgnoreCase("U01")) {

                mcrOrderProxie = new McrOrderProxie();
                mcrOrderProxie.setStrORDER_NO(getORDERNO);
                mcrOrderProxie.setStrREGTYPE(getILART);
                mcrOrderProxie.setStrFATHER_NAME(getFATHERNAME);
                mcrOrderProxie.setStrMOBILE_NO(getMOBILENO);
                mcrOrderProxie.setStrEMAIL(getBP_NO);
                mcrOrderProxie.setStrCOMPANY(getBUKRS);
                mcrOrderProxie.setStrUser_id(userPreferences.getUserid());
                mcrOrderProxie.setStrKYC(String.valueOf(kycVal));
                mcrOrderProxie.setStrAPPLIED_LOAD(getZZ_RLOAD);

                mcrOrderProxie.setStrTITLE(getTITLE);
                mcrOrderProxie.setStrFIRST_NAME(getNAME_FIRST);
                mcrOrderProxie.setStrLAST_NAME(getNAME_LAST);
                mcrOrderProxie.setStrSTREET(getSTREET);
                mcrOrderProxie.setStrHOUSE_NO(getHOUSE_NUM1);
                mcrOrderProxie.setStrExistingLoad(Existing_LOAD);
                if (getPINCODE == null) {
                    mcrOrderProxie.setStrPIN(POST_CODE1);
                } else {
                    mcrOrderProxie.setStrPIN(getPINCODE);
                }


                if (getSTR_SUPPL3 == null) {
                    mcrOrderProxie.setStrAREA(getSTR_SUPPL);
                } else if (getSTR_SUPPL == null) {
                    mcrOrderProxie.setStrAREA(getSTR_SUPPL3);
                } else {
                    mcrOrderProxie.setStrAREA(getSTR_SUPPL + getSTR_SUPPL3);
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData", mcrOrderProxie);
                fragment.setArguments(bundle);

            } else {
                mcrOrderProxieOtherConn = new McrOrderProxieOtherConn();
                mcrOrderProxieOtherConn.setStrORDER_NO(getORDERNO);
                mcrOrderProxieOtherConn.setStrReqType(getILART);
                mcrOrderProxieOtherConn.setStrFATHER_NAME(getFATHERNAME);
                mcrOrderProxieOtherConn.setStrMOBILE_NO(getMOBILENO);
                mcrOrderProxieOtherConn.setStrEMAIL(getBP_NO);
                mcrOrderProxieOtherConn.setStrCompany(getBUKRS);
                mcrOrderProxieOtherConn.setStrUser_ID(userPreferences.getUserid());
                mcrOrderProxieOtherConn.setStrKYC(String.valueOf(kycVal));
                mcrOrderProxieOtherConn.setStrRLOAD_S4(getZZ_RLOAD);
                mcrOrderProxieOtherConn.setStrCA_NUMBER(getZZ_VKONT);

                mcrOrderProxieOtherConn.setStrTITLE(getTITLE);
                mcrOrderProxieOtherConn.setStrFIRST_NAME(getNAME_FIRST);
                mcrOrderProxieOtherConn.setStrLAST_NAME(getNAME_LAST);
                mcrOrderProxieOtherConn.setStrSTREET(getSTREET);
                mcrOrderProxieOtherConn.setStrHOUSE_NO(getHOUSE_NUM1);
                mcrOrderProxieOtherConn.setStrPIN(getPINCODE);
                mcrOrderProxieOtherConn.setStrExistingLoad(Existing_LOAD);
                if (getSTR_SUPPL3 == null) {
                    mcrOrderProxieOtherConn.setStrAREA(getSTR_SUPPL);
                } else if (getSTR_SUPPL == null) {
                    mcrOrderProxieOtherConn.setStrAREA(getSTR_SUPPL3);
                } else {
                    mcrOrderProxieOtherConn.setStrAREA(getSTR_SUPPL + getSTR_SUPPL3);
                }


                if (getPINCODE == null) {
                    mcrOrderProxieOtherConn.setStrPIN(POST_CODE1);
                } else {
                    mcrOrderProxieOtherConn.setStrPIN(getPINCODE);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ChangeToolTitle() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ORDER DETAILS");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {

                if (requestCode == ApplicationConstants.REASON_PIC) {

                    onCaptureImageResult(data, resultCode, requestCode);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("NewApi")
    private void onCaptureImageResult(Intent data, int resultcode, int requestcode) {

        Bitmap thumbnail = null, thumbnailForDisplay = null;
        try {
            File imgFile = new File(pictureImagePath);

            if (imgFile.exists()) {

            } else {
                thumbnail = (Bitmap) data.getExtras().get("data");
                bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            }

        } catch (Exception er) {
            er.printStackTrace();
        }

        if (requestcode == ApplicationConstants.REASON_PIC) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            reasonpic.setBackground(d);
            reasonpicbmp = thumbnail;
            resultreasonpic = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        }


    }


    private class punchDataWS extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // webservicePG.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Requesting...");
            progressDialog.incrementProgressBy(20);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


        }

        @Override
        protected Object doInBackground(Object[] params) {


            punchingDataBean = new PunchingDataBean();

            OrderCancelProxie orderCancelProxie = new OrderCancelProxie();
            orderCancelProxie.setInstaller_ID(userPreferences.getUserid());
            orderCancelProxie.setOrder_ID(getORDERNO);
            orderCancelProxie.setImage(resultreasonpic);
            orderCancelProxie.setReason(reasonResult);
            orderCancelProxie.setRemarks(remark.getText().toString());

            try {
                // punchingDataBean = ApplicationUtil.getInstance().getWebservice().cancelOrderRequest(orderCancelProxie, getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            try {

                filterAlert.dismiss();

                progressDialog.dismiss();


                if (punchingDataBean.getACK().equalsIgnoreCase("true")) {


                    final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.promt_message_success);

                    TextView alert_title2 = (TextView) dialog.findViewById(R.id.alert_title2);
                    TextView closeDialog = (TextView) dialog.findViewById(R.id.closePrompt);
                    alert_title2.setText("Successfully cancelled !!");


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            userPreferences.setEmail("");

                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                            mainIntent.putExtra("empType", "employee");
                            startActivity(mainIntent);

                        }
                    });

                    dialog.show();
                } else {
                    Snackbar.make(getView(), "Server not responding !!", Snackbar.LENGTH_LONG)
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
