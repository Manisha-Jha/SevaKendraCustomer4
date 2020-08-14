package com.bses.dinesh.dsk.telematics.remote;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by SM on 11/07/19.
 */

public class RetrofitInterfaces {

    public interface DoLogin {
        @POST("LOGIN")
        @FormUrlEncoded
//        Call<ResponseBody> post(@Field("username") String emailId,
//                                @Field("password") String password);
        Call<ResponseBody> post(@Field("vehicleId") String emailId);
    }


    public interface FetchTasksList {
        @POST("FetchTasksList")
        @FormUrlEncoded
        Call<ResponseBody> post(@Header("USERAUTH") String userAuth,
                                @Field("customer_id") String customer_id);
    }


    public interface GetEmployeeDetails {
        @POST("GetEmployeeDetails")
        @FormUrlEncoded
        Call<ResponseBody> post(@Header("USERAUTH") String userAuth,
                                @Field("company_id") String company_id,
                                @Field("emp_id") String emp_id);
    }



    public interface GetEmployeeDetail {
        @POST("http://115.249.67.72:9880/DSK_telematic/api/UserDetailInsert/SaveUserDetails?")
        @FormUrlEncoded
        Call<ResponseBody> post(@Field("UserId") String user_id,
                                @Field("lattude") String lat,
                                @Field("_longlattude") String lng,
                                @Field("_Imeino") String imei,
                                @Field("status") String status);
    }

    public interface GetDashboard {
        @POST("GetDashboard")
        @FormUrlEncoded
        Call<ResponseBody> post(@Header("USERAUTH") String userAuth,
                                @Field("company_id") String company_id);
    }

    public interface GoLogin {
        @POST("GoLogin")
        @Multipart
        Call<ResponseBody> save(@PartMap HashMap<String, RequestBody> map);

    }


//    public interface ChangePassword {
//        @POST("CHANGEPASSWORD")
//        @FormUrlEncoded
//        Call<ResponseBody> post(@Header("userAuth") String userAuth,
//                                @Field("user_id") String user_id,
//                                @Field("currentPassword") String currentPassword,
//                                @Field("newPassword") String newPassword);
//    }
//    public interface ContactUs {
//        @POST("CONTACTUS")
//        @FormUrlEncoded
//        Call<ResponseBody> post(@Field("name") String name,
//                                @Field("email") String email,
//                                @Field("phone") String phone,
//                                @Field("message") String message);
//    }
//
//    public interface ForgotPassword {
//        @POST("FORGOTPASSWORD")
//        @FormUrlEncoded
//        Call<ResponseBody> post(@Field("email") String email);
//    }

}



