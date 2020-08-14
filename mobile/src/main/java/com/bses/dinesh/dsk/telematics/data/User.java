package com.bses.dinesh.dsk.telematics.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("DIVISION")
    @Expose
    private String dIVISION;


    private String USER_ID;
    private String NAME;
    private String PHONE_NUMBER;

    public String getDIVISION() {
        return dIVISION;
    }

    public void setDIVISION(String dIVISION) {
        this.dIVISION = dIVISION;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }
}
