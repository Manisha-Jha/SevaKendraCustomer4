package com.bses.dinesh.dsk.fragMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bses.dinesh.dsk.BuildConfig;
import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.PunchingDataBean;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.sevakendra.MainActivity;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.telematicsapp.OrderTestingActivity;
import com.bses.dinesh.dsk.telematics.utils.GPSTracker;
import com.bses.dinesh.dsk.telematics.utils.GetSnappedLocOnRoad;
import com.bses.dinesh.dsk.utils.ApplicationConstants;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.SignatureLayoutActivity;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import id.zelory.compressor.Compressor;
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
public class PhotosAndID extends Fragment {


    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    public Button click1;
    public Button click2;
    public Button click3;
    public Button click4;
    public Button mcr;
    public Button metertest;
    public Button labtest;
    public static Button signature;
    private String resultclick1 = "";
    private String resultclick2 = "";
    private String resultclick3 = "";
    private String resultclick4 = "";
    private String resultmcr = "";
    private String resultmetertest = "";
    private String resultlabtest = "";
    private static String resultsignature = "";
    private Bitmap click1bmp, click2bmp, click3bmp, click4bmp, mcrbmp, metertestbmp, labtestbmp, signaturebmp;
    private RadioGroup radioinstalledlocation;
    private EditText poleNumber, mrkva, mrkwh, mrkw, mrkvah;
    private String valInstalledLocation = "";
    //private McrOrederProxie orderData;
    private TextView inputSdate;
    private TextInputLayout inputLayoutSdate;
    static String image1String = "";
    static PhotosAndID instance;
    private UserPreferences userPreferences;
    private ByteArrayOutputStream bytes, bytesForDisplay;
    private String pictureImagePath = "";
    private Uri outputFileUri;

    private McrOrderProxie mcrOrderProxie;
    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;

    private Spinner spin_idtype;
    private CheckBox check_agree;
    private Button submit;
    private EditText remarks;
    private PowerManager.WakeLock mWakeLock;
    private ProgressDialog progressDialog;
    private PunchingDataBean punchingDataBean, punchingDataBeanImg;

    RelativeLayout exe, good, ave, bad;
    private String feedbackVal = "";
    public double currentlatitude = 0;
    public double currentlongitude = 0;
    private GPSTracker gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.photos_id, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("OTHER DETAILS");
        progressDialog = new ProgressDialog(getActivity());

        userPreferences = UserPreferences.getInstance(getActivity());
        resultsignature = "";

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


        //seva kendra


        inputLayoutSdate = (TextInputLayout) rootView.findViewById(R.id.input_layout_sdate);
        inputSdate = (TextView) rootView.findViewById(R.id.input_sdate);

        spin_idtype = (Spinner) rootView.findViewById(R.id.spin_idtype);
        click1 = (Button) rootView.findViewById(R.id.click1);
        click2 = (Button) rootView.findViewById(R.id.click2);


        if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {
            click2.setText("Optional ID");
        } else {
            click2.setText("Optional ID *");
        }


        signature = (Button) rootView.findViewById(R.id.signature);
        remarks = (EditText) rootView.findViewById(R.id.remarks);


        submit = (Button) rootView.findViewById(R.id.next3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getCurrentLocation();

                if (userPreferences.getLocalMsgId().equalsIgnoreCase("U01")) {

                    if (resultclick1.isEmpty() || resultsignature.isEmpty()) {
                        Snackbar.make(getView(), "Please check mandatory fields!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        mcrOrderProxie.setStrPIC_NAME(resultclick1);
                        mcrOrderProxie.setStrSIG_NAME(resultsignature);
                        mcrOrderProxie.setStrCF_REMARK(feedbackVal);
                        mcrOrderProxie.setStrText_Remark(remarks.getText().toString());

                        punchDataWS task = new punchDataWS();
                        //Call execute
                        task.execute();
                    }
                } else {

                    if (resultclick1.isEmpty() || resultclick2.isEmpty() || resultsignature.isEmpty() || feedbackVal.isEmpty()) {
                        Snackbar.make(getView(), "Please check mandatory fields!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {

                        mcrOrderProxieOtherConn.setStrImgName(resultclick1);
                        mcrOrderProxieOtherConn.setStrIDName(resultclick2);
                        mcrOrderProxieOtherConn.setStrSignName(resultsignature);
                        mcrOrderProxieOtherConn.setStrRemark(feedbackVal);
                        mcrOrderProxieOtherConn.setStrText_Remark(remarks.getText().toString());

                        punchDataWSOtherConn task = new punchDataWSOtherConn();
                        task.execute();


                    }

                }

            }
        });

        check_agree = (CheckBox) rootView.findViewById(R.id.check_agree);

        check_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    submit.setEnabled(true);
                    submit.setFocusable(true);
                } else {
                    submit.setEnabled(false);
                    submit.setFocusable(false);
                }
            }
        });


        exe = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
        good = (RelativeLayout) rootView.findViewById(R.id.relativeLayout2);
        ave = (RelativeLayout) rootView.findViewById(R.id.relativeLayout3);
        bad = (RelativeLayout) rootView.findViewById(R.id.relativeLayout4);


        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exe.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_thick));

                good.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_green));
                ave.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_yellow));
                bad.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_red));

                feedbackVal = "excellent";
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                good.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_thick));

          /*      exe.setBackgroundResource(0);
                ave.setBackgroundResource(0);
                bad.setBackgroundResource(0);*/

                exe.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_blue));
                ave.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_yellow));
                bad.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_red));

                feedbackVal = "good";
            }
        });

        ave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ave.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_thick));

                exe.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_blue));
                good.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_green));
                bad.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_red));


                feedbackVal = "Average";
            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bad.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_thick));
                feedbackVal = "poor";

                exe.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_blue));
                good.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_green));
                ave.setBackground(getActivity().getDrawable(R.drawable.round_rect_shape_yellow));

            }
        });


        radioinstalledlocation = (RadioGroup) rootView.findViewById(R.id.radioinstalledlocation);
        //Here there is no provision to view this radio group -- Rajveer
        valInstalledLocation = ((RadioButton) rootView.findViewById(radioinstalledlocation.getCheckedRadioButtonId())).getText().toString();
        radioinstalledlocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valInstalledLocation = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();
            }
        });

        poleNumber = (EditText) rootView.findViewById(R.id.polenumber);


        mrkwh = (EditText) rootView.findViewById(R.id.mrkwh);
        mrkw = (EditText) rootView.findViewById(R.id.mrkw);
        mrkvah = (EditText) rootView.findViewById(R.id.mrkvah);
        mrkva = (EditText) rootView.findViewById(R.id.mrkva);

        click3 = (Button) rootView.findViewById(R.id.click3);
        click4 = (Button) rootView.findViewById(R.id.click4);

        mcr = (Button) rootView.findViewById(R.id.mcr);
        metertest = (Button) rootView.findViewById(R.id.metertest);
        labtest = (Button) rootView.findViewById(R.id.labtest);


        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] date_break = date.split("-");
        int current_year = Integer.parseInt(date_break[0]);
        int current_month = Integer.parseInt(date_break[1]);
        int current_day = Integer.parseInt(date_break[2]);

        inputSdate.setText(current_day + "/" + current_month + "/" + current_year);
        inputSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new displayDateFragment();
                dialogFragment.show(getFragmentManager(), "StartDatePicker");
            }
        });

        setListener();


        return rootView;
    }


    private void setListener() {

        click1.setOnClickListener(mClickListener);
        click2.setOnClickListener(mClickListener);
        click3.setOnClickListener(mClickListener);
        click4.setOnClickListener(mClickListener);
        mcr.setOnClickListener(mClickListener);
        metertest.setOnClickListener(mClickListener);
        labtest.setOnClickListener(mClickListener);
        signature.setOnClickListener(mClickListener);

    }


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.click1:
                    selectImage(ApplicationConstants.CLICK1);
                    break;

                case R.id.click2:
                    selectImage(ApplicationConstants.CLICK2);
                    break;

                case R.id.click3:
                    selectImage(ApplicationConstants.CLICK3);
                    break;

                case R.id.click4:
                    selectImage(ApplicationConstants.CLICK4);
                    break;

                case R.id.mcr:
                    selectImage(ApplicationConstants.IMG_MCR);
                    break;

                case R.id.metertest:
                    selectImage(ApplicationConstants.IMG_METERTEST);
                    break;

                case R.id.labtest:
                    selectImage(ApplicationConstants.IMG_LABTEST);
                    break;

                case R.id.signature:
                    // selectImage(ApplicationConstants.IMG_SIGNATURE);
                    Intent intent = new Intent(getActivity(), SignatureLayoutActivity.class);
                    intent.putExtra("activityName", "OtherDetails");
                    startActivity(intent);
                    break;

            }
        }
    };


    public static PhotosAndID getInstance() {
        return instance;
    }

    @SuppressLint("NewApi")
    public static void setSignatureImage(Bitmap bm, Drawable draw) {

        try {
            //image1String = str;
            Drawable d = draw;
            resultsignature = ApplicationUtil.getInstance().BitMapToString(bm);
            signature.setBackground(d);
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    private void selectImage(int imageViewID) {

        try {
            final int imgViewid = imageViewID;

            final CharSequence[] items = {"Take Photo", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {


                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = timeStamp + ".jpg";
                        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                        File file = new File(pictureImagePath);


                        if (Build.VERSION.SDK_INT > 23) {
                            outputFileUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
                        } else {
                            outputFileUri = Uri.fromFile(file);
                        }

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(cameraIntent, imgViewid);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {

                if (requestCode == ApplicationConstants.CLICK1 || requestCode == ApplicationConstants.CLICK2
                        || requestCode == ApplicationConstants.CLICK3 || requestCode == ApplicationConstants.CLICK4 ||
                        requestCode == ApplicationConstants.IMG_MCR
                        || requestCode == ApplicationConstants.IMG_METERTEST || requestCode == ApplicationConstants.IMG_LABTEST
                        || requestCode == ApplicationConstants.IMG_SIGNATURE) {

                    onCaptureImageResult(data, resultCode, requestCode);

                } else if (requestCode == ApplicationConstants.CLICK1_GALLERY || requestCode == ApplicationConstants.CLICK2_GALLERY
                        || requestCode == ApplicationConstants.CLICK3_GALLERY || requestCode == ApplicationConstants.IMG_MCR_GALLERY
                        || requestCode == ApplicationConstants.IMG_METERTEST_GALLERY || requestCode == ApplicationConstants.IMG_LABTEST_GALLERY
                        || requestCode == ApplicationConstants.IMG_SIGNATURE_GALLERY) {

                    onSelectFromGalleryResult(data, resultCode, requestCode);

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

                //need
                if (requestcode == ApplicationConstants.IMG_MCR) {

                    thumbnail = new Compressor(getActivity())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(35)
                            .setCompressFormat(Bitmap.CompressFormat.PNG)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToBitmap(imgFile);

                } else {
                    thumbnail = new Compressor(getActivity())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(20)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToBitmap(imgFile);
                }
            } else {
                thumbnail = (Bitmap) data.getExtras().get("data");
                bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            }

        } catch (Exception er) {
            er.printStackTrace();
        }


        if (requestcode == ApplicationConstants.CLICK1) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            click1.setBackground(d);
            resultclick1 = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.CLICK2) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            click2.setBackground(d);
            //click2bmp = thumbnailForDisplay;
            resultclick2 = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.CLICK3) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            click3.setBackground(d);
            // click3bmp = thumbnailForDisplay;
            resultclick3 = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.CLICK4) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            click4.setBackground(d);
            //click4bmp = thumbnailForDisplay;
            resultclick4 = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.IMG_MCR) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            mcr.setBackground(d);
            //mcrbmp = thumbnailForDisplay;
            resultmcr = ApplicationUtil.getInstance().BitMapToStringMCR(thumbnail);
        } else if (requestcode == ApplicationConstants.IMG_METERTEST) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            metertest.setBackground(d);
            // metertestbmp = thumbnailForDisplay;
            resultmetertest = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.IMG_LABTEST) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            labtest.setBackground(d);
            // labtestbmp = thumbnailForDisplay;
            resultlabtest = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        } else if (requestcode == ApplicationConstants.IMG_SIGNATURE) {
            Drawable d = new BitmapDrawable(getResources(), thumbnail);
            signature.setBackground(d);
            signaturebmp = thumbnailForDisplay;
            resultsignature = ApplicationUtil.getInstance().BitMapToString(thumbnail);

        }

    }


    @SuppressLint("NewApi")
    private void onSelectFromGalleryResult(Intent data, int resultcode, int requestcode) throws IOException {

        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 90;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);


        ExifInterface ei = new ExifInterface(selectedImagePath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


        if (requestcode == ApplicationConstants.CLICK1_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            click1.setBackground(d);
            click1bmp = bm;
            resultclick1 = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.CLICK2_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            click2.setBackground(d);
            click2bmp = bm;
            resultclick2 = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.CLICK3_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            click3.setBackground(d);
            click3bmp = bm;
            resultclick3 = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.IMG_MCR_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            mcr.setBackground(d);
            mcrbmp = bm;
            resultmcr = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.IMG_METERTEST_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            metertest.setBackground(d);
            metertestbmp = bm;
            resultmetertest = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.IMG_LABTEST_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            labtest.setBackground(d);
            labtestbmp = bm;
            resultlabtest = ApplicationUtil.getInstance().BitMapToString(bm);
        } else if (requestcode == ApplicationConstants.IMG_SIGNATURE_GALLERY) {
            Drawable d = new BitmapDrawable(getResources(), bm);
            signature.setBackground(d);
            signaturebmp = bm;
            resultsignature = ApplicationUtil.getInstance().BitMapToString(bm);
        }

    }

    @SuppressLint("ValidFragment")
    class displayDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int iYear = calendar.get(Calendar.YEAR);
            int iMonth = calendar.get(Calendar.MONTH);
            int iDay = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, iYear, iMonth, iDay);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            setSelectedDate(yy, mm + 1, dd);
        }
    }


    public void setSelectedDate(int iYear, int iMonth, int iDay) {
        // if(startOrEndDate.equals("start")){

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] date_break = date.split("-");
        int current_year = Integer.parseInt(date_break[0]);
        int current_month = Integer.parseInt(date_break[1]);
        int current_day = Integer.parseInt(date_break[2]);


        if (iDay == current_day && iMonth == current_month && iYear == current_year) {
            inputSdate.setText(iDay + "/" + iMonth + "/" + iYear);
        } else {
            //startDate = "SELECT";
            Snackbar.make(this.getView(), "Selected date should be 2 days lesser or equal than current date", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }




    private class punchDataWS extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // webservicePG.setVisibility(View.VISIBLE);
            PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            progressDialog.setMessage("Submitting Data..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            int i = 0;
            punchingDataBean = new PunchingDataBean();
            punchingDataBeanImg = new PunchingDataBean();
            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {


                punchingDataBean = ApplicationUtil.getInstance().getWebservice().sendNewPunchData(mcrOrderProxie, getActivity());

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
                mWakeLock.release();

                if (punchingDataBean.getACK().equalsIgnoreCase("True")) {

                    final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                      dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.promt_message_success);

                    TextView requestid = (TextView) dialog.findViewById(R.id.requestid);
                    TextView closeDialog = (TextView) dialog.findViewById(R.id.closePrompt);


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            userPreferences.setEmail("");

                            String current_time= new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = new Date();
                            String current_date = dateFormat.format(date);

                            GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(
                                    currentlatitude + "," + currentlongitude);
                            HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
                            String snappedLatLongi = snappedLocs.get("0");
                            String snappedLati = null;
                            String snappedLongi = null;
                            if (snappedLatLongi != null)
                            {
                                snappedLati = snappedLatLongi.split(",")[0];
                                snappedLongi = snappedLatLongi.split(",")[1];

                                if (snappedLati.equals(null) && snappedLongi.equals(null))
                                {
                                    snappedLati = String.valueOf(currentlatitude);
                                    snappedLongi = String.valueOf(currentlongitude);
                                }
                            }
                            else
                            {
                                snappedLati = String.valueOf(currentlatitude);
                                snappedLongi = String.valueOf(currentlongitude);
                            }

                            updateOrderSubmitTime(userPreferences.getUserid(),current_date,
                                    String.valueOf(snappedLati),String.valueOf(snappedLongi),
                                    current_time, "Noida sec-62",mcrOrderProxie.getStrORDER_NO());

                        }
                    });
                    // messageDescription.setText(message.getGenre().toString());
                    //   messageTitle.setText(message.getTitle().toString());
                    dialog.show();
                } else {
                    Snackbar.make(getView(), "Unsuccessful, Please try again later !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } catch (Exception er) {

                Snackbar.make(getView(), "Server not responding, Try again later!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                er.printStackTrace();
            }


        }


    }
    private void updateOrderSubmitTime(String user_id,String order_submit_date, String lat,
                                       String lng, String submit_time,String address, String order_num)
    {
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
        Call<RetrofitResponse> call = request.update_order_submit_time(user_id,order_submit_date,lat,lng,
                submit_time,address,order_num);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("INSERT",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.Key.equals("Success"))
                    {
                        Toast.makeText(getActivity(), jsonResponse.Key, Toast.LENGTH_SHORT).show();

                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                        mainIntent.putExtra("empType", "employee");
                        startActivity(mainIntent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Server down", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }

    private void getCurrentLocation()
    {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            currentlatitude = gps.getLatitude();
            currentlongitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
    }


    private class punchDataWSOtherConn extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // webservicePG.setVisibility(View.VISIBLE);
            PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            progressDialog.setMessage("Submitting Data..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            int i = 0;
            punchingDataBean = new PunchingDataBean();
            punchingDataBeanImg = new PunchingDataBean();
            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {


                //feedbackVal

                punchingDataBean = ApplicationUtil.getInstance().getWebservice().sendNewPunchDataOtherConn(mcrOrderProxieOtherConn, getActivity());


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
                mWakeLock.release();

                if (punchingDataBean.getACK().equalsIgnoreCase("True")) {

                    final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.promt_message_success);

                    TextView requestid = (TextView) dialog.findViewById(R.id.requestid);
                    TextView closeDialog = (TextView) dialog.findViewById(R.id.closePrompt);


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            userPreferences.setEmail("");

                            String current_time= new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = new Date();
                            String current_date = dateFormat.format(date);

                            GetSnappedLocOnRoad getSnappedLocOnRoad = new GetSnappedLocOnRoad(
                                    currentlatitude + "," + currentlongitude);
                            HashMap<String, String> snappedLocs = getSnappedLocOnRoad.getSnappedLocs();
                            String snappedLatLongi = snappedLocs.get("0");
                            String snappedLati = null;
                            String snappedLongi = null;
                            if (snappedLatLongi != null)
                            {
                                snappedLati = snappedLatLongi.split(",")[0];
                                snappedLongi = snappedLatLongi.split(",")[1];

                                if (snappedLati.equals(null) && snappedLongi.equals(null))
                                {
                                    snappedLati = String.valueOf(currentlatitude);
                                    snappedLongi = String.valueOf(currentlongitude);
                                }
                            }
                            else
                            {
                                snappedLati = String.valueOf(currentlatitude);
                                snappedLongi = String.valueOf(currentlongitude);
                            }

                            updateOrderSubmitTime(userPreferences.getUserid(),current_date,
                                    String.valueOf(snappedLati),String.valueOf(snappedLongi),
                                    current_time,"Noida Sec-62" ,mcrOrderProxieOtherConn.getStrORDER_NO());
                        }
                    });

                    dialog.show();
                } else {
                    Snackbar.make(getView(), "Unsuccessful, Please try again later !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } catch (Exception er) {

                Snackbar.make(getView(), "Server not responding, Try again later!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                er.printStackTrace();
            }


        }


    }


}
