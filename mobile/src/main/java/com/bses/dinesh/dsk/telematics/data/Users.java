package com.bses.dinesh.dsk.telematics.data;

import com.google.gson.annotations.SerializedName;

public class Users
{
    @SerializedName("USER_ID")
    public String id;

    @SerializedName("PHONE_NUMBER")
    public String phNum;

    @SerializedName("EMAIL_ID")
    public String userEmailId;

    @SerializedName("PASSWORD")
    public String password;

    @SerializedName("ROLE_ID")
    public String roleId;

    @SerializedName("NAME")
    public String name;

    @SerializedName("LATITUDE")
    public String lat;

    @SerializedName("LONGITUDE")
    public String lng;

    public String usertypeId;

    public String divId;

    @SerializedName("DIVISION")
    public String divName;

    @SerializedName("ACTIVE")
    public String activeStatus;

    @SerializedName("DESIGNATION")
    public String designation;

    @SerializedName("STATUS")
    public String status;

    @SerializedName("IMEI_NUMBER")
    public String imei_number;

    @SerializedName("ADDRESS")
    public String address;

    public Users() {
    }

    public Users(String id, String phNum, String userEmailId, String password, String roleId, String name, String lat, String lng,String status)
    {
        this.id = id;
        this.phNum = phNum;
        this.userEmailId = userEmailId;
        this.password = password;
        this.roleId = roleId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getPhoneNum() {
        return phNum;
    }

    public void setPhoneNum(String phNum) {
        this.phNum = phNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoleID() {
        return roleId;
    }

    public void setUserRoleID(String roleId) {
        this.roleId = roleId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(String usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId;
    }

    public String getDivName() {
        return divName;
    }

    public void setUserDivName(String divName) {
        this.divName = divName;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getUserDesignation() {
        return designation;
    }

    public void setUserDesignation(String designation) {
        this.designation = designation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
