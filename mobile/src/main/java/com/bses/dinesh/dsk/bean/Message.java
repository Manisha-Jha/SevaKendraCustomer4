package com.bses.dinesh.dsk.bean;

import java.io.Serializable;

/**
 * Created by Krishna on 4/27/2016.
 */
public class Message implements Serializable {



    String MODULE_CODE;
    String MODULE_NAME;
    String TYP_F_WRK_ID;
    String TYP_F_WRK_NAME;

    String TASK_DESCRIPTION;
    String TASK_PURPOSE_OF_CHANGE;
    String PRIORITY_NAME;
    String TASK_UNIQUE_ID;

    String TASK_ASSIGNED_BY;
    String TASK_ASSIGNED_DATE;
    String NAME;

    String NEWENTRY_DATE;
    String DELAY_DAYS;

    public Message(){

    }

    public Message(String MODULE_CODE, String MODULE_NAME, String TYP_F_WRK_ID, String TYP_F_WRK_NAME, String TASK_DESCRIPTION, String PRIORITY_NAME, String TASK_PURPOSE_OF_CHANGE, String TASK_UNIQUE_ID, String TASK_ASSIGNED_BY, String TASK_ASSIGNED_DATE, String NAME, String NEWENTRY_DATE, String DELAY_DAYS) {
        this.MODULE_CODE = MODULE_CODE;
        this.MODULE_NAME = MODULE_NAME;
        this.TYP_F_WRK_ID = TYP_F_WRK_ID;
        this.TYP_F_WRK_NAME = TYP_F_WRK_NAME;
        this.TASK_DESCRIPTION = TASK_DESCRIPTION;
        this.PRIORITY_NAME = PRIORITY_NAME;
        this.TASK_PURPOSE_OF_CHANGE = TASK_PURPOSE_OF_CHANGE;
        this.TASK_UNIQUE_ID = TASK_UNIQUE_ID;
        this.TASK_ASSIGNED_BY = TASK_ASSIGNED_BY;
        this.TASK_ASSIGNED_DATE = TASK_ASSIGNED_DATE;
        this.NAME = NAME;
        this.NEWENTRY_DATE = NEWENTRY_DATE;
        this.DELAY_DAYS = DELAY_DAYS;
    }



    public String getMODULE_CODE() {
        return MODULE_CODE;
    }

    public void setMODULE_CODE(String MODULE_CODE) {
        this.MODULE_CODE = MODULE_CODE;
    }

    public String getMODULE_NAME() {
        return MODULE_NAME;
    }

    public void setMODULE_NAME(String MODULE_NAME) {
        this.MODULE_NAME = MODULE_NAME;
    }

    public String getTYP_F_WRK_ID() {
        return TYP_F_WRK_ID;
    }

    public void setTYP_F_WRK_ID(String TYP_F_WRK_ID) {
        this.TYP_F_WRK_ID = TYP_F_WRK_ID;
    }

    public String getTYP_F_WRK_NAME() {
        return TYP_F_WRK_NAME;
    }

    public void setTYP_F_WRK_NAME(String TYP_F_WRK_NAME) {
        this.TYP_F_WRK_NAME = TYP_F_WRK_NAME;
    }

    public String getTASK_DESCRIPTION() {
        return TASK_DESCRIPTION;
    }

    public void setTASK_DESCRIPTION(String TASK_DESCRIPTION) {
        this.TASK_DESCRIPTION = TASK_DESCRIPTION;
    }

    public String getTASK_PURPOSE_OF_CHANGE() {
        return TASK_PURPOSE_OF_CHANGE;
    }

    public void setTASK_PURPOSE_OF_CHANGE(String TASK_PURPOSE_OF_CHANGE) {
        this.TASK_PURPOSE_OF_CHANGE = TASK_PURPOSE_OF_CHANGE;
    }

    public String getPRIORITY_NAME() {
        return PRIORITY_NAME;
    }

    public void setPRIORITY_NAME(String PRIORITY_NAME) {
        this.PRIORITY_NAME = PRIORITY_NAME;
    }

    public String getTASK_UNIQUE_ID() {
        return TASK_UNIQUE_ID;
    }

    public void setTASK_UNIQUE_ID(String TASK_UNIQUE_ID) {
        this.TASK_UNIQUE_ID = TASK_UNIQUE_ID;
    }

    public String getTASK_ASSIGNED_BY() {
        return TASK_ASSIGNED_BY;
    }

    public void setTASK_ASSIGNED_BY(String TASK_ASSIGNED_BY) {
        this.TASK_ASSIGNED_BY = TASK_ASSIGNED_BY;
    }

    public String getTASK_ASSIGNED_DATE() {
        return TASK_ASSIGNED_DATE;
    }

    public void setTASK_ASSIGNED_DATE(String TASK_ASSIGNED_DATE) {
        this.TASK_ASSIGNED_DATE = TASK_ASSIGNED_DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNEWENTRY_DATE() {
        return NEWENTRY_DATE;
    }

    public void setNEWENTRY_DATE(String NEWENTRY_DATE) {
        this.NEWENTRY_DATE = NEWENTRY_DATE;
    }

    public String getDELAY_DAYS() {
        return DELAY_DAYS;
    }

    public void setDELAY_DAYS(String DELAY_DAYS) {
        this.DELAY_DAYS = DELAY_DAYS;
    }
}