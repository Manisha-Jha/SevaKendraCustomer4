package com.bses.dinesh.dsk.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.sevakendra.LoginScreen;
import com.bses.dinesh.dsk.webServices.Webservice;
import com.bses.dinesh.dsk.webServices.WebserviceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by Krishna on 5/3/2016.
 */
public final class ApplicationUtil {

    public static ApplicationUtil instance;
    public Webservice webservice;
    private static final String TAG = "Contacts";
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private ApplicationUtil(){
        instance = this;
    }

    public static ApplicationUtil getInstance() {

        if(instance == null){
            instance = new ApplicationUtil();
        }
        return instance;
    }
    // check internet connection
    public boolean checkInternetConnection(Context activity){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        return connected;


    }

    //decode base64
    public Bitmap decodeBase64(String input, Context activity){

        byte[] decodeByte = Base64.decode(input.trim(),activity.MODE_WORLD_READABLE);
        System.out.println("....1");
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }


    //get alert dialog
    public AlertDialog getAlertDialog(String msg, Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }


    //get alert dialog
    public AlertDialog showAlertDialogWithTitle(String title, String msg, String pos_btn, String neg_btn, int view, Context context){

       final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Setting Dialog Title
       // alertDialog.setTitle(title);
        // Setting Dialog Message
      //  alertDialog.setMessage(msg);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(view, null);
        builder.setView(dialogView);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
      /*  if(!option1.equalsIgnoreCase("")) {
            alertDialog.setPositiveButton(option1,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          //  finish();
                        }
                    });
        }
        // Setting Negative "NO" Button
        if(!option2.equalsIgnoreCase("")) {
            alertDialog.setNegativeButton(option2,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
        }*/
        // Showing Alert Message
       // alertDialog.show();
        TextView msg_title = (TextView) dialogView.findViewById(R.id.alert_title);
        msg_title.setText(title);

        TextView messageDescription = (TextView) dialogView.findViewById(R.id.message_description);
        messageDescription.setText(msg);

        Button negative = (Button) dialogView.findViewById(R.id.dialogButtonNO);
        Button positive = (Button) dialogView.findViewById(R.id.dialogButtonYES);

       final AlertDialog alertDialog = builder.create();

        if(pos_btn.length()==0){
            positive.setVisibility(View.GONE);
        }
        else{
        }

        if(neg_btn.length()==0){

        }
        else{
            negative.setText("DISMISS");
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
        }



        return alertDialog;
    }


    //get IMEI number
    /*public String getImeiNo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }*/

    //get working days between two dates
    public int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        int i = 0;

        do {

            System.out.println("............. i  = "+i);
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
                i++;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays;
    }


    // is service running in foreground
    public boolean isRunningInForeground(Context context) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        for(int i=0;i<tasks.size();i++)
        {
            System.out.println("Task-------"+tasks.get(i));
        }
        System.out.println("Task-------"+tasks.get(0).baseActivity);
        System.out.println("Task-------"+tasks.get(0).numActivities);
        if (tasks.isEmpty()) {
            return false;
        }
        String topActivityName = tasks.get(0).topActivity.getPackageName();
        System.out.println(".............topActivity = " +topActivityName);
        System.out.println(".............packageName = " +context.getPackageName());
        return topActivityName.equalsIgnoreCase(context.getPackageName());
    }


    // check service running or not
    public boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    // URL to Bitmap conversion
    public boolean getBitmapFromURL(String src, String key, Context context) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getInputStream() !=null){

                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte[] arr = baos.toByteArray();
                String imageString= Base64.encodeToString(arr, Base64.DEFAULT);
                System.out.println("..........."+ key);
                System.out.println("..........."+ imageString);
                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor edit=shre.edit();
                edit.putString(key, imageString);
                edit.commit();
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



   public Webservice getWebservice() {
       webservice = new WebserviceImpl();
       return webservice;

   }

    public static void showLogoutDialog(final Context context, String title, String msg) {
        //final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( context);
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_warning_black_24dp);
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                UserPreferences pref = UserPreferences.getInstance(context);
             /*  // pref.setDevice_fcm_id("");
                pref.setDevice_imei_id("");
               // pref.setAddress("");
                pref.setCompCode(""); //division
                pref.setFirst_name("");
               // pref.setProfile_image("");
                //pref.setMobile("");
                //pref.setEmail("");
                pref.setPassword("");
               // pref.setCurrent_login("");
                pref.setLast_login("");
                pref.setStatus("");
                pref.setUserid("");
              //  pref.setIdImage("");
                pref.setUser_role("");
                pref.setRoleDesc("");
                pref.setLat("0.0");
                pref.setLong("0.0");*/

                pref.setFirst_name("");
                pref.setCompCode(""); // company brpl/bypl
                pref.setRoleDesc("");  // designation
                pref.setDivision(""); //division
                pref.setPassword("");
                pref.setUserid("");
                pref.setUser_role("");
                pref.setAppVersionWeb("");

               // pref.setDivision("");

              /*  DBHandler db = new DBHandler(context);
                if(db.getNewOrderRowsCount() > 0) {
                    db.deleteAllNewOrder();
                }*/


                Intent i = new Intent(context, LoginScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static void logout(final Context context) {
        //final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);


                UserPreferences pref = UserPreferences.getInstance(context);
                // pref.setDevice_fcm_id("");
                pref.setDevice_imei_id("");
                // pref.setAddress("");
                pref.setCompCode(""); //division
                pref.setFirst_name("");
                // pref.setProfile_image("");
                //pref.setMobile("");
                //pref.setEmail("");
                pref.setPassword("");
                // pref.setCurrent_login("");
                pref.setLast_login("");
                pref.setStatus("");
                pref.setUserid("");
                //  pref.setIdImage("");
                pref.setUser_role("");
                pref.setRoleDesc("");
                pref.setAppVersionWeb("");
                // pref.setDivision("");

             /*   DBHandler db = new DBHandler(context);
                if(db.getNewOrderRowsCount() > 0) {
                    db.deleteAllNewOrder();
                }
*/

                Intent i = new Intent(context, LoginScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
                System.exit(0);

    }

    public static void showVersion(final Context context, String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
       /* alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                });*/
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


    public static void showBackDialog(final Context context, String title, String msg) {
       AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {
                       // ((Activity) context).finish();
                        ((Activity) context).finishAffinity();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }



    public static boolean isConnectingToInternet(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        }

        return false;
    }



    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }



    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void showSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }



    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }
    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }


   /* public static void showNetworkErrorMessage(final Context context) {
        android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(context);
        dlg.setCancelable(false);
        dlg.setTitle("Error");
        dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
        dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dlg.show();
    }*/



    public String addNotificationKey(String senderId, String userEmail, String[] registrationId, String idToken)
                                    throws IOException, JSONException {

        URL url = new URL("https://android.googleapis.com/gcm/notification");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);

        // HTTP request header
        con.setRequestProperty("project_id", senderId);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "key=" + "AIzaSyBfAYc8T2zJp_UGkEbde6nFgM3xeMOUlo4");
        con.setRequestMethod("POST");
        con.connect();

        // HTTP request
        JSONObject data = new JSONObject();
        data.put("operation", "create");
        data.put("notification_key_name", userEmail);
        data.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
      //  data.put("id_token", idToken);

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        // add ids
      /*  data.put("operation", "create");
        data.put("notification_key_name", userEmail);
        data.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
        data.put("id_token", idToken);

        os.write(data.toString().getBytes("UTF-8"));
        os.close();*/

        // Read the response into a string
//        HTTP/1.0 200 OK
 //       HTTP/1.0 401 Unauthorized
        InputStream is = null;

        int responseCode =  con.getResponseCode();
        Log.e(".Get Notif Err Code.:", responseCode+"");

        if(responseCode >200 && responseCode<400){
           is = con.getInputStream();
        }
        else{
            is = con.getErrorStream();
        }


        String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
        is.close();

        // Parse the JSON string and return the notification key
        JSONObject response = new JSONObject(responseString);
        return response.getString("notification_key");

    }


    private void insertDummyContact(Context context) {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = context.getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }


   /* public void  showPermiDialog(final Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_CONTACTS},
                                    ApplicationConstants.REQUEST_CODE_ASK_PERMISSIONS);
                            dialog.dismiss();
                        }
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.dismiss();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }*/


    public String getAccount(Context con) {
        Account[] accounts = AccountManager.get(con).getAccountsByType("com.google");
        if (accounts.length == 0) {
            return null;
        }
        return accounts[0].name;
    }

    public String BitMapToString(Bitmap bitmap){
        /*ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;*/

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public String BitMapToStringMCR(Bitmap bitmapval){

        /*ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;*/

        Bitmap bitmap = changeBitmapContrastBrightness(bitmapval, 1.3f, 3f);

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     *
     * @param bmp input bitmap
     * @param contrast 0..10 1 is default
     * @param brightness -255..255 0 is default
     * @return new bitmap
     */
    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public String BitMapToStringUsingBuffer(Bitmap bitmap){
        //double  width = bitmap.getWidth();
        //double  height = bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] b = byteBuffer.array();

        /*int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte [] b = byteBuffer.array();*/


        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



    public String BitMapToStringBAOS(ByteArrayOutputStream baos){
        //ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b= baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



    public static File from(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String fileName = getFileName(context, uri);
        String[] splitName = splitFileName(fileName);
        File tempFile = File.createTempFile(splitName[0], splitName[1]);
        tempFile = rename(tempFile, fileName);
        tempFile.deleteOnExit();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            copy(inputStream, out);
            inputStream.close();
        }

        if (out != null) {
            out.close();
        }
        return tempFile;
    }


    private static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }

    private static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private static File rename(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.equals(file)) {
            if (newFile.exists() && newFile.delete()) {
                Log.d("FileUtil", "Delete old " + newName + " file");
            }
            if (file.renameTo(newFile)) {
                Log.d("FileUtil", "Rename file to " + newName);
            }
        }
        return newFile;
    }

    private static long copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @SuppressLint("MissingPermission")
    public String getDeviceImei(Context ctx){

        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        System.out.println("..........imei no = "+telephonyManager.getDeviceId());

         return telephonyManager.getDeviceId();


       // return "35570206487133";

    }



    public boolean isMobileCorrect(String mobile){
        boolean iscorrect = false;

        if(mobile.equals("0000000000") || mobile.equals("1111111111") || mobile.equals("2222222222")
                || mobile.equals("3333333333")|| mobile.equals("4444444444") || mobile.equals("5555555555")
                || mobile.equals("6666666666") || mobile.equals("7777777777") || mobile.equals("8888888888")
                ||mobile.equals("9999999999") || mobile.charAt(0) == '1' || mobile.charAt(0) == '2' || mobile.charAt(0) == '3'
                || mobile.charAt(0) == '4' || mobile.charAt(0) == '5' || mobile.charAt(0) == '6'){
            iscorrect = false;
        }

        else{
            iscorrect = true;
        }
        return iscorrect;


    }


    public static boolean isValidMail(String email2) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email2);
        check = m.matches();

        /*if(!check)
        {
            //txtEmail.setError("Not Valid Email");
        }*/
        return check;
    }



}
