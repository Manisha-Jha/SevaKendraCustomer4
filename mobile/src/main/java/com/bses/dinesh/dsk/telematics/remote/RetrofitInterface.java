package com.bses.dinesh.dsk.telematics.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface
{
    //@FormUrlEncoded
    @POST("api/Seva_Insert_User_Detail")
    Call<RetrofitResponse> insertUserLocation(@Query("USER_ID") String user_id,
                                              @Query("LATITUDE") String lat,
                                              @Query("LONGITUDE") String lng,
                                              @Query("IMEI_NO") String imei_no,
                                              @Query("STATUS") String status,
                                              @Query("TIMESTAMP") String timestamp,
                                              @Query("RECORD_ENTRY_DATE") String date,
                                              @Query("RECORD_ENTRY_TIME") String time);

/*
    @GET("api/Seva_Insert_User_Detail?")
    public Call<RetrofitResponse> insertUserLocation(@Query("USER_ID") String user_id,
                                                     @Query("LATITUDE") String lat,
                                                     @Query("LONGITUDE") String lng,
                                                     @Query("IMEI_NO") String imei_no,
                                                     @Query("STATUS") String status,
                                                     @Query("TIMESTAMP") String timestamp,
                                                     @Query("RECORD_ENTRY_DATE") String date,
                                                     @Query("RECORD_ENTRY_TIME") String time);
*/
    @GET("api/Seva_Get_Division_List?")
    public Call<RetrofitResponse> getDivisionList(@Query("USER_ID") String user_id);

    //@FormUrlEncoded
    @GET("api/Seva_Get_Division_Based?")
    Call<RetrofitResponse> getUserListOnDivisionBased(@Query("DIVISION") String divName);

    @GET("api/Seva_Get_All_User_Detail_List?")
    Call<RetrofitResponse> getAllUserDetail(@Query("USER_ID") String user_id);

    @GET("api/Seva_Get_Datewise_Location_By_User?")
    Call<RetrofitResponse> getUserLocationDateBased(@Query("USER_ID") String user_id,
                                                    @Query("RECORD_ENTRY_DATE") String date);


    @GET("api/Seva_Get_Login?")
    Call<RetrofitResponse> getLogin(@Query("USER_ID") String user_id,
                                    @Query("PASSWORD") String pass);

    @POST("api/Seva_Insert_Order_Open_Time?")
    Call<RetrofitResponse> insert_order_open_time(@Query("ORDER_NO") String order_num,
                                                  @Query("USER_ID") String user_id,
                                                  @Query("CUSTOMER_FORM_OPEN_TIME") String open_time);

    @POST("api/Seva_Update_Order_Submit_Detail?")
    Call<RetrofitResponse> update_order_submit_time(@Query("USER_ID") String user_id,
                                                    @Query("ORDER_SUBMIT_DATE") String submit_date,
                                                    @Query("ORDER_SUBMIT_LAT") String lat,
                                                    @Query("ORDER_SUBMIT_LNG") String lng,
                                                    @Query("ORDER_SUBMIT_TIME") String submit_time,
                                                    @Query("CUSTOMER_ADDRESS") String cust_address,
                                                    @Query("ORDER_NO") String order_num);


    @GET("api/Seva_Get_Order_Location_Detail?")
    Call<RetrofitResponse> getOrderLocationDetail(@Query("USER_ID") String user_id,
                                                  @Query("date") String date);

    @GET("api/Seva_Get_Order_Execution_Information?")
    Call<RetrofitResponse> getOrderExecutionInformation(@Query("ORDER_NO") String order_num);

    @GET("api/Seva_Get_User_Current_Location_and_Status?")
    Call<RetrofitResponse> getUserCurrentLocation(@Query("USER_ID") String order_num);


}
