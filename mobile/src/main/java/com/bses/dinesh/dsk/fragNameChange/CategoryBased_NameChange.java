package com.bses.dinesh.dsk.fragNameChange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bses.dinesh.dsk.BuildConfig;
import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.fragMain.PhotosAndID;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.utils.ApplicationConstants;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.SignatureLayoutActivity;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Krishna on 4/29/2016.
 */
public class CategoryBased_NameChange extends Fragment {


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
    static CategoryBased_NameChange instance;
    private UserPreferences userPreferences;
    private ByteArrayOutputStream bytes, bytesForDisplay;
    private String pictureImagePath = "";
    private Uri outputFileUri;

    private McrOrderProxieOtherConn mcrOrderProxieOtherConn;
    private String month_name;


    //new
    private RadioGroup radioNameChangeReason, radiogender, radiobilling, radio_areatype, radiopremises, radiometerchoice, radioload;
    private String valradioNameChangeReason, valRadioBilling, valRadioAreaType, valRadioPremises, valMeterChoice, valRadioLoad;
    private String valradioNameChangeReasonSubmit = "", valGender = "", valGenderSubmit = "", valRadioBillingSubmit = "", valRadioAreaTypeSubmit = "",
            valRadioPremisesSubmit = "", spinUsageVal = "", valMeterChoiceSubmit = "", valRadioLoadSubmit = "";
    private EditText nameOfApplicant, father_name_correct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_namechange, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("NAME CHANGE");

        userPreferences = UserPreferences.getInstance(getActivity());
        resultsignature = "";

        Bundle bundle = getArguments();
        //orderData = (McrOrederProxie) bundle.getSerializable("orderData");
        mcrOrderProxieOtherConn = (McrOrderProxieOtherConn) bundle.getSerializable("orderData");


        // seva kendra


        radioNameChangeReason = (RadioGroup) rootView.findViewById(R.id.radio_namechange_reason);
        valradioNameChangeReason = ((RadioButton) rootView.findViewById(radioNameChangeReason.getCheckedRadioButtonId())).getText().toString();
        radioNameChangeReason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valradioNameChangeReason = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();

                if (valradioNameChangeReason.equalsIgnoreCase("Purchase of Property")) {
                    valradioNameChangeReasonSubmit = "01";
                }
                if (valradioNameChangeReason.equalsIgnoreCase("Legal Heir")) {
                    valradioNameChangeReasonSubmit = "02";
                }
                if (valradioNameChangeReason.equalsIgnoreCase("Change in Tenacy")) {
                    valradioNameChangeReasonSubmit = "03";
                }
                if (valradioNameChangeReason.equalsIgnoreCase("Typing Error")) {
                    valradioNameChangeReasonSubmit = "04";
                } else {
                    valradioNameChangeReasonSubmit = "04";
                }
            }
        });


        nameOfApplicant = (EditText) rootView.findViewById(R.id.correct_name);


        radiogender = (RadioGroup) rootView.findViewById(R.id.radiogender);
        valGender = ((RadioButton) rootView.findViewById(radiogender.getCheckedRadioButtonId())).getText().toString();
        radiogender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                valGender = ((RadioButton) group.getRootView().findViewById(checkedId)).getText().toString();


                if (valGender.equalsIgnoreCase("Male")) {
                    valGenderSubmit = "M";
                }
                if (valGender.equalsIgnoreCase("Female")) {
                    valGenderSubmit = "F";
                }
                if (valGender.equalsIgnoreCase("Other")) {
                    valGenderSubmit = "O";
                } else {
                    valGenderSubmit = "M";
                }
            }
        });


        inputLayoutSdate = (TextInputLayout) rootView.findViewById(R.id.input_layout_sdate);
        inputSdate = (TextView) rootView.findViewById(R.id.input_sdate);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] date_break = date.split("-");
        int current_year = Integer.parseInt(date_break[0]);
        int current_month = Integer.parseInt(date_break[1]);
        int current_day = Integer.parseInt(date_break[2]);

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        month_name = month_date.format(calendar.getTime());

        inputSdate.setText(current_day + " " + month_name + " " + current_year);
        inputSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new displayDateFragment();
                dialogFragment.show(getFragmentManager(), "StartDatePicker");
            }
        });


        father_name_correct = (EditText) rootView.findViewById(R.id.father_name_correct);


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

        click1 = (Button) rootView.findViewById(R.id.click1_);
        click2 = (Button) rootView.findViewById(R.id.click2_);
        click3 = (Button) rootView.findViewById(R.id.click3_);
        click4 = (Button) rootView.findViewById(R.id.click4_);


        mcr = (Button) rootView.findViewById(R.id.mcr_);
        metertest = (Button) rootView.findViewById(R.id.metertest_);
        labtest = (Button) rootView.findViewById(R.id.labtest_);
        signature = (Button) rootView.findViewById(R.id.signature_);


        setListener();


        Button button1 = (Button) rootView.findViewById(R.id.next3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()) {

                    // mcrOrderProxie.setCBL_SIZE(dropcablesize.getSelectedItem().toString());

                    mcrOrderProxieOtherConn.setStrREASON_S3(valradioNameChangeReasonSubmit);
                    mcrOrderProxieOtherConn.setStrNAME_S3(nameOfApplicant.getText().toString());
                    mcrOrderProxieOtherConn.setStrGENDER_S3(valGenderSubmit);
                    mcrOrderProxieOtherConn.setStrDOB_S3(inputSdate.getText().toString());
                    mcrOrderProxieOtherConn.setStrFATHEERNAME_S3(father_name_correct.getText().toString());


                    Fragment fragment = new PhotosAndID();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderData", mcrOrderProxieOtherConn);
                    fragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                    fragmentTransaction.commit();


                } else {


                }
            }
        });


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


    private boolean checkValidation() {


        if (valradioNameChangeReasonSubmit.isEmpty() || valGenderSubmit.isEmpty() || nameOfApplicant.getText().toString().trim().isEmpty()
                || inputSdate.getText().toString().isEmpty() || father_name_correct.getText().toString().isEmpty()) {

            Snackbar.make(getView(), "Please check mendatory field !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
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


    public static CategoryBased_NameChange getInstance() {
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

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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


                } else {

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


            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            month_name = month_date.format(calendar.getTime());

            return new DatePickerDialog(getActivity(), this, iYear, iMonth, iDay);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            setSelectedDate(yy, mm + 1, dd, month_name);
        }
    }


    public void setSelectedDate(int iYear, int iMonth, int iDay, String month_name) {
        // if(startOrEndDate.equals("start")){

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] date_break = date.split("-");
        int current_year = Integer.parseInt(date_break[0]);
        int current_month = Integer.parseInt(date_break[1]);
        int current_day = Integer.parseInt(date_break[2]);

        if ((iDay == current_day - 1 || iDay == current_day - 2 || iDay == current_day) && iMonth == current_month && iYear == current_year) {
            inputSdate.setText(iDay + " " + month_name + " " + iYear);
        } else if (iMonth <= current_month - 1 && iYear == current_year) {
            if ((current_day == 1 || current_day == 2) && (iDay == 31 || iDay == 30 || iDay == 29)) {
                inputSdate.setText(iDay + " " + month_name + " " + iYear);
            } else if (iDay >= current_day) {
                inputSdate.setText(iDay + " " + month_name + " " + iYear);
            }

        } else if (iYear == current_year - 1) {
            if (current_month == 1 && iMonth == 12) {
                if ((current_day == 1 || current_day == 2) && iDay == 31 && iDay == 30 && iDay == 29) {
                    inputSdate.setText(iDay + " " + month_name + " " + iYear);
                }
            }
        }

//        if (iDay <= current_day && iMonth <= current_month && iYear <= current_year) {
//            inputSdate.setText(iDay + "/" + month_name + "/" + iYear);
//        }
        else {
            //startDate = "SELECT";
            Snackbar.make(this.getView(), "Selected date should be lesser than current date", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


}
