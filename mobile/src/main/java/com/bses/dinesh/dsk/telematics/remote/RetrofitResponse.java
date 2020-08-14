package com.bses.dinesh.dsk.telematics.remote;

import com.bses.dinesh.dsk.telematics.data.OrderInformation;
import com.bses.dinesh.dsk.telematics.data.User;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*import com.jhamobi.brplapp.data.OrderInformation;
import com.jhamobi.brplapp.data.User;
import com.jhamobi.brplapp.data.UserLocationData;
import com.jhamobi.brplapp.data.Users;*/

import java.util.List;

public class
RetrofitResponse
{
    public String Key; //success is json object

    @SerializedName("DIVISION")//for users based on division
    @Expose
    private List<User> DIVISION = null;

    @SerializedName("DivisionList")//for division list
    @Expose
    private List<User> DivisionList = null;

    @SerializedName("UserDetail")//all user related to manager
    @Expose
    private List<Users> usersDetailList = null;

    @SerializedName("UserLoc")//all user related to manager
    @Expose
    private List<UserLocationData> userLocationBasedOnDate = null;

    @SerializedName("User_login")//all user related to manager
    @Expose
    private List<Users> user_login = null;

    @SerializedName("OrderLoc")//submitted order location details
    @Expose
    private List<OrderInformation> order_loc = null;

    @SerializedName("ORDER_INFO")//submitted order information
    @Expose
    private List<OrderInformation> order_info = null;

    @SerializedName("CurrentUser")//submitted order information
    @Expose
    private List<Users> current_loc = null;

    public List<User> getDivisionList() {
        return DivisionList;
    }

    public List<User> getUser() {
        return DIVISION;
    }

    public List<Users> getUsersDetailList() {
        return usersDetailList;
    }

    public List<UserLocationData> getUserLocationBasedOnDate() {
        return userLocationBasedOnDate;
    }

    public List<Users> getUser_login() {
        return user_login;
    }

    public List<OrderInformation> getUser_loc() { return order_loc; }

    public List<OrderInformation> getOrder_info() { return order_info; }

    public List<Users> getCurrent_loc() { return current_loc; }

}
