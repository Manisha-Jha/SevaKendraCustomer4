package com.bses.dinesh.dsk.telematics.data;

import com.google.gson.annotations.SerializedName;

public class UserLocationData
{
    @SerializedName("USER_ID")
    public String userid;
    @SerializedName("TIMESTAMP")
    public double timestamp;
    @SerializedName("LATITUDE")
    public String lat;
    @SerializedName("LONGITUDE")
    public String lng;
    @SerializedName("RECORD_ENTRY_DATE")
    public String date;
    @SerializedName("RECORD_ENTRY_TIME")
    public String time;
    @SerializedName("STATUS")
    public String status;

    public UserLocationData() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
