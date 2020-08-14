package com.bses.dinesh.dsk.telematics.data;

import com.google.gson.annotations.SerializedName;

public class OrderInformation
{
    @SerializedName("ORDER_NO")
    public String ORDER_NO;
    @SerializedName("ORDER_SUBMIT_LAT")
    public String ORDER_SUBMIT_LAT;
    @SerializedName("ORDER_SUBMIT_LNG")
    public String ORDER_SUBMIT_LNG;
    @SerializedName("ORDER_SUBMIT_DATE")
    public String ORDER_SUBMIT_DATE;
    @SerializedName("ORDER_SUBMIT_TIME")
    public String ORDER_SUBMIT_TIME;
    @SerializedName("USER_ID")
    public String USER_ID;
    @SerializedName("CUSTOMER_FORM_OPEN_TIME")
    public String CUSTOMER_FORM_OPEN_TIME;
    @SerializedName("CUSTOMER_ADDRESS")
    public String CUSTOMER_ADDRESS;

    public OrderInformation() {
    }

    public OrderInformation(String ORDER_NO,
            String ORDER_SUBMIT_LAT,
            String ORDER_SUBMIT_LNG,
            String ORDER_SUBMIT_DATE,
            String ORDER_SUBMIT_TIME,
            String USER_ID,
            String CUSTOMER_FORM_OPEN_TIME,
            String CUSTOMER_ADDRESS)
    {
        this.ORDER_NO = ORDER_NO;
        this.ORDER_SUBMIT_LAT = ORDER_SUBMIT_LAT;
        this.ORDER_SUBMIT_LNG = ORDER_SUBMIT_LNG;
        this.ORDER_SUBMIT_DATE = ORDER_SUBMIT_DATE;
        this.ORDER_SUBMIT_TIME = ORDER_SUBMIT_TIME;
        this.USER_ID = USER_ID;
        this.CUSTOMER_FORM_OPEN_TIME = CUSTOMER_FORM_OPEN_TIME;
        this.CUSTOMER_ADDRESS = CUSTOMER_ADDRESS;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public String getCUSTOMER_FORM_OPEN_TIME() {
        return CUSTOMER_FORM_OPEN_TIME;
    }

    public String getORDER_SUBMIT_DATE() {
        return ORDER_SUBMIT_DATE;
    }

    public String getORDER_SUBMIT_LAT() {
        return ORDER_SUBMIT_LAT;
    }

    public String getCUSTOMER_ADDRESS() {
        return CUSTOMER_ADDRESS;
    }

    public String getORDER_SUBMIT_LNG() {
        return ORDER_SUBMIT_LNG;
    }

    public String getORDER_SUBMIT_TIME() {
        return ORDER_SUBMIT_TIME;
    }
}
