 package com.bses.dinesh.dsk.utils;

 import java.text.DateFormat;
 import java.util.Date;

 public class ApplicationConstants {


public static final String WEB_SERVICE_URL="http://115.249.67.72:9880/Mobileservices_SK/";//old url for testing


//	public static final String WEB_SERVICE_URL="http://125.22.84.50:9820/";// new url for live

	public static final String BaseURL="http://115.249.67.72:9880/DSK_telematic/";// Telematics DateTime
	public static final String DateTime = "";


	public static final String SERVER_IP = "http://107.20.237.175:8086/";
	public static final String SERVER_URL = "http://107.20.237.175:8086/";

	public static final String TARGE_NAME_SPACE = "http://tempuri.org/";
	public static final String APP_ID = "1";
	public static final String App_log = "App_log";
	public static final String GET_VALIDATE_USER = "Seva_get_Login_Details";
	public static final String GET_CHANGE_PASS = "Seva_change_Password";
	public static final String MCR_Insert_INPUT_Data_RV_NEW = "Seva_Insert_Data_New_Conn";
	public static final String MCR_Insert_INPUT_Data_OTHER_CONN = "Seva_Insert_Data_Other_Connection";

	public static final String GET_EMP_MESSAGES = "Seva_get_Order_Details";

	public static final String GET_BROADCAST_MSG = "TTS_TASK_ACTION";
	public static final String SUBMIT_ALLOCATION = "MCR_OrderAssign";
	public static final String CREATE_INSTALLER = "MCR_Create_Installer_Data";



	//phase 3 -- RV
	public static final String MCR_Insert_INPUT_Data_RV = "MCR_Insert_INPUT_Data_RV";
	//phase 3 -- RV Image
	public static final String MCR_Insert_INPUT_Data_Img_RV = "MCR_Insert_INPUT_Data_Img_RV";
	public static final String GET_VALIDATE_SEAL = "MCR_ValidateSEAL";
	public static final String GET_CANCEL_ORDER = "MCR_OrderCancel";

	public static final String ADD_CA_ACCOUNT="AddCAAccount";

	public static final String ERROR_MSG_SERVER_DOWN="You May Be Experiencing Problem With Your Network Service Provider.";

	public static final int REQUEST_CODE_ASK_PERMISSIONS= 123;


	public static final String MethodType1 = "insert";
	public static final String MethodType3 = "view";


	//shared preferences
	public static final String DEVICE_FCM_ID = "DEVICE_FCM_ID";
	public static final String DEVICE_IMEI_ID = "DEVICE_IMEI_ID";
	public static final String USER_ADD = "USER_ADD";
	public static final String PROFILE_IMAGE = "PROFILE_IMAGE";
	public static final String NAME = "NAME";
	public static final String CANUM = "CANUM";
	public static final String MOBILE = "MOBILE";
	public static final String EMAIL = "EMAIL";
	public static final String PASSWORD = "PASSWORD";
	public static final String USER_ROLE = "USER_role";
	public static final String CURRENT_LOGIN = "CURRENT_LOGIN";
	public static final String LAST_LOGIN = "LAST_LOGIN";
	public static final String STATUS = "STATUS";
	public static final String DIVISION = "DIVISION";
	public static final String APPVERSIONWEB = "APPVERSIONWEB";
	public static final String USER_ID = "USER_ID";
	public static final String LAT = "LAT";
	public static final String LONGG = "LONGG";
	public static final String NOTI_ACV = "NOTI_ACV";
	public static final String ID_IMAGE = "ID_IMAGE";
	public static final String LocalMsgId = "LocalMsgId";

    //mcr image browse
	public static final int CLICK1 = 1008;
	public static final int CLICK2 = 1010;
	public static final int CLICK3 = 1012;
	public static final int CLICK4 = 1013;
	public static final int IMG_MCR = 1014;
	public static final int IMG_METERTEST = 1016;
	public static final int IMG_LABTEST = 1018;
	public static final int IMG_SIGNATURE = 1020;

	public static final int CLICK1_GALLERY = 1009;
	public static final int CLICK2_GALLERY = 1011;
	public static final int CLICK3_GALLERY = 10130;
	public static final int IMG_MCR_GALLERY = 1015;
	public static final int IMG_METERTEST_GALLERY = 1017;
	public static final int IMG_LABTEST_GALLERY = 1019;
	public static final int IMG_SIGNATURE_GALLERY = 1021;
	public static final int REASON_PIC = 1100;


	 public static String mDatetime() {

		 try {

			 return DateFormat.getDateTimeInstance().format(new Date());



		 } catch (Exception e) {
			 e.printStackTrace();
			 return "02/04/2019 , 03:30:24";
		 }


	 }
}
