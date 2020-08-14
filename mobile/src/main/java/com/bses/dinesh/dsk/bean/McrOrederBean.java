package com.bses.dinesh.dsk.bean;

import java.io.Serializable;

/**
 * Created by Krishna on 4/27/2016.
 */
public class McrOrederBean implements Serializable {


    String ORDERNO;      //=008002791125;
    String NAME;        // =Ms. JANKI  MISHRA;
    String FATHERNAME;        //=W/O SH DEVI DATT MISHRA;
    String CITY;        //=NEW DELHI;
    String PINCODE;        //=110048;
    String DIVISION;        //=S1NHP;
    String REQ_TYPE;        //= Change of Name Request;
    String ZZ_RLOAD;        //=0;
    String EMAIL;        //=teststt@h.com;
    String ADDRESS;        //=Second FLOOR S-436 GREATER KAILASH PART-1  .;
    String MOBILENO;        //=562354;
    String FLAG;        //=1;
    String APPDT;        //=07-05-2018 08:00:00;
    String REAPPDT;        //=07-05-2017 ;
    String AUFNR;        //=008002791125;
    String AUART;        //=ZDSS;
    String ERDAT;        //=201-10-27;
    String BUKRS;        //=BRPL;
    String VAPLZ;        //=S1NHP;
    String KUNUM;        //=0905973215;
    String ILART;        //=U02;
    String GSTRP;        //=2017-04-20;
    String GSUZP;        //=08:00:00;
    String BPKIND;        //=0010;
    String TITLE;        //=Ms.;
    String NAME_LAST;     //=MISHRA;
    String NAME_FIRST;   //=JANKI;
    String SECONDNAME;        //=W/O SH DEVI DATT MISHRA;
    String NAME1;        //=MISHRA;
    String NAME2;        //=JANKI;
    String CITY_CODE;        //=NEW DELHI;
    String POST_CODE1;        //=110048;
    String TEL_NUMBER;        //=562354;
    String TEL_EXTENS;        //=SMS;
    String E_MAIL;        //=teststt@h.com;
    String STREET;        //=Second FLOOR;
    String HOUSE_NUM1;        //=S-436;
    String STR_SUPPL;        //1=GREATER KAILASH PART-1;
    String STR_SUPPL3;        //=.;
    String STRMN;        //=2018-05-07;
    String STRUR;        //=08:00:00;
    String LTRMN;        //=2017-04-20;
    String LTRUR;        //=10:00:00;
    String RUN_FOR_DATE;        //=20.04.2017;
    String RUN_ON_DATE;        //=20.04.2017;
    String RESTRMN;        //=2017-05-07;
    String ZZ_RLOAD1;        //=0;
    String ZZ_RLOADKVA;      //existing load  //=0;
    String ZZ_VKONT;        //=000151746728;

    String strExistingLoad;


    public McrOrederBean(){

    }

    public McrOrederBean(String ORDERNO, String NAME, String FATHERNAME, String CITY, String PINCODE, String DIVISION, String REQ_TYPE, String ZZ_RLOAD, String EMAIL, String ADDRESS, String MOBILENO, String FLAG, String APPDT, String REAPPDT, String AUFNR, String AUART, String ERDAT, String BUKRS, String VAPLZ, String KUNUM, String ILART, String GSTRP, String GSUZP, String BPKIND, String TITLE, String NAME_LAST, String NAME_FIRST, String SECONDNAME, String NAME1, String NAME2, String CITY_CODE, String POST_CODE1, String TEL_NUMBER, String TEL_EXTENS, String e_MAIL, String STREET, String HOUSE_NUM1, String STR_SUPPL, String STR_SUPPL3, String STRMN, String STRUR, String LTRMN, String LTRUR, String RUN_FOR_DATE, String RUN_ON_DATE, String RESTRMN, String ZZ_RLOAD1, String ZZ_RLOADKVA, String ZZ_VKONT) {
        this.ORDERNO = ORDERNO;
        this.NAME = NAME;
        this.FATHERNAME = FATHERNAME;
        this.CITY = CITY;
        this.PINCODE = PINCODE;
        this.DIVISION = DIVISION;
        this.REQ_TYPE = REQ_TYPE;
        this.ZZ_RLOAD = ZZ_RLOAD;
        this.EMAIL = EMAIL;
        this.ADDRESS = ADDRESS;
        this.MOBILENO = MOBILENO;
        this.FLAG = FLAG;
        this.APPDT = APPDT;
        this.REAPPDT = REAPPDT;
        this.AUFNR = AUFNR;
        this.AUART = AUART;
        this.ERDAT = ERDAT;
        this.BUKRS = BUKRS;
        this.VAPLZ = VAPLZ;
        this.KUNUM = KUNUM;
        this.ILART = ILART;
        this.GSTRP = GSTRP;
        this.GSUZP = GSUZP;
        this.BPKIND = BPKIND;
        this.TITLE = TITLE;
        this.NAME_LAST = NAME_LAST;
        this.NAME_FIRST = NAME_FIRST;
        this.SECONDNAME = SECONDNAME;
        this.NAME1 = NAME1;
        this.NAME2 = NAME2;
        this.CITY_CODE = CITY_CODE;
        this.POST_CODE1 = POST_CODE1;
        this.TEL_NUMBER = TEL_NUMBER;
        this.TEL_EXTENS = TEL_EXTENS;
        E_MAIL = e_MAIL;
        this.STREET = STREET;
        this.HOUSE_NUM1 = HOUSE_NUM1;
        this.STR_SUPPL = STR_SUPPL;
        this.STR_SUPPL3 = STR_SUPPL3;
        this.STRMN = STRMN;
        this.STRUR = STRUR;
        this.LTRMN = LTRMN;
        this.LTRUR = LTRUR;
        this.RUN_FOR_DATE = RUN_FOR_DATE;
        this.RUN_ON_DATE = RUN_ON_DATE;
        this.RESTRMN = RESTRMN;
        this.ZZ_RLOAD1 = ZZ_RLOAD1;
        this.ZZ_RLOADKVA = ZZ_RLOADKVA;
        this.ZZ_VKONT = ZZ_VKONT;
    }

    /*  public McrOrederBean(String ORDERNO, String STICKERNO, String STARTDATE, String FINISHDATE, String NAME, String FATHERNAME, String ADDRESS, String MOBILENO, String POLENO, String POLESIZE, String DIVISIONOFFICE, String REQUESTTYPE, String CABLESIZE, String CABLELENGTH, String PLANNERGROUP, String OTHER1, String OTHER2, String OTHER3, String OTHER8, String PUNCHED, String COMP_CODE, String PSTING_DATE, String VENDOR_CODE, String BP_NO, String CA_NO, String METER_NO, String UNIQUE_REF_NO, String ORDER_TYPE, String ACCOUNT_CLASS, String SANCTIONED_LOAD, String ZDSS, String PM_ACTIVITY, String OLD_M_READING) {
        this.ORDERNO = ORDERNO;
        this.STICKERNO = STICKERNO;
        this.STARTDATE = STARTDATE;
        this.FINISHDATE = FINISHDATE;
        this.NAME = NAME;
        this.FATHERNAME = FATHERNAME;
        this.ADDRESS = ADDRESS;
        this.MOBILENO = MOBILENO;
        this.POLENO = POLENO;
        this.POLESIZE = POLESIZE;
        this.DIVISIONOFFICE = DIVISIONOFFICE;
        this.REQUESTTYPE = REQUESTTYPE;
        this.CABLESIZE = CABLESIZE;
        this.CABLELENGTH = CABLELENGTH;
        this.PLANNERGROUP = PLANNERGROUP;
        this.OTHER1 = OTHER1;
        this.OTHER2 = OTHER2;
        this.OTHER3 = OTHER3;
        this.OTHER8 = OTHER8;
        this.PUNCHED = PUNCHED;
        this.COMP_CODE = COMP_CODE;
        this.PSTING_DATE = PSTING_DATE;
        this.VENDOR_CODE = VENDOR_CODE;
        this.BP_NO = BP_NO;
        this.CA_NO = CA_NO;
        this.METER_NO = METER_NO;
        this.UNIQUE_REF_NO = UNIQUE_REF_NO;
        this.ORDER_TYPE = ORDER_TYPE;
        this.ACCOUNT_CLASS = ACCOUNT_CLASS;
        this.SANCTIONED_LOAD = SANCTIONED_LOAD;
        this.ZDSS = ZDSS;
        this.PM_ACTIVITY = PM_ACTIVITY;
        this.OLD_M_READING = OLD_M_READING;
    }*/

    public String getORDERNO() {
        return ORDERNO;
    }

    public void setORDERNO(String ORDERNO) {
        this.ORDERNO = ORDERNO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getFATHERNAME() {
        return FATHERNAME;
    }

    public void setFATHERNAME(String FATHERNAME) {
        this.FATHERNAME = FATHERNAME;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }

    public String getDIVISION() {
        return DIVISION;
    }

    public void setDIVISION(String DIVISION) {
        this.DIVISION = DIVISION;
    }

    public String getREQ_TYPE() {
        return REQ_TYPE;
    }

    public void setREQ_TYPE(String REQ_TYPE) {
        this.REQ_TYPE = REQ_TYPE;
    }

    public String getZZ_RLOAD() {
        return ZZ_RLOAD;
    }

    public void setZZ_RLOAD(String ZZ_RLOAD) {
        this.ZZ_RLOAD = ZZ_RLOAD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getAPPDT() {
        return APPDT;
    }

    public void setAPPDT(String APPDT) {
        this.APPDT = APPDT;
    }

    public String getREAPPDT() {
        return REAPPDT;
    }

    public void setREAPPDT(String REAPPDT) {
        this.REAPPDT = REAPPDT;
    }

    public String getAUFNR() {
        return AUFNR;
    }

    public void setAUFNR(String AUFNR) {
        this.AUFNR = AUFNR;
    }

    public String getAUART() {
        return AUART;
    }

    public void setAUART(String AUART) {
        this.AUART = AUART;
    }

    public String getERDAT() {
        return ERDAT;
    }

    public void setERDAT(String ERDAT) {
        this.ERDAT = ERDAT;
    }

    public String getBUKRS() {
        return BUKRS;
    }

    public void setBUKRS(String BUKRS) {
        this.BUKRS = BUKRS;
    }

    public String getVAPLZ() {
        return VAPLZ;
    }

    public void setVAPLZ(String VAPLZ) {
        this.VAPLZ = VAPLZ;
    }

    public String getKUNUM() {
        return KUNUM;
    }

    public void setKUNUM(String KUNUM) {
        this.KUNUM = KUNUM;
    }

    public String getILART() {
        return ILART;
    }

    public void setILART(String ILART) {
        this.ILART = ILART;
    }

    public String getGSTRP() {
        return GSTRP;
    }

    public void setGSTRP(String GSTRP) {
        this.GSTRP = GSTRP;
    }

    public String getGSUZP() {
        return GSUZP;
    }

    public void setGSUZP(String GSUZP) {
        this.GSUZP = GSUZP;
    }

    public String getBPKIND() {
        return BPKIND;
    }

    public void setBPKIND(String BPKIND) {
        this.BPKIND = BPKIND;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getNAME_LAST() {
        return NAME_LAST;
    }

    public void setNAME_LAST(String NAME_LAST) {
        this.NAME_LAST = NAME_LAST;
    }

    public String getNAME_FIRST() {
        return NAME_FIRST;
    }

    public void setNAME_FIRST(String NAME_FIRST) {
        this.NAME_FIRST = NAME_FIRST;
    }

    public String getSECONDNAME() {
        return SECONDNAME;
    }

    public void setSECONDNAME(String SECONDNAME) {
        this.SECONDNAME = SECONDNAME;
    }

    public String getNAME1() {
        return NAME1;
    }

    public void setNAME1(String NAME1) {
        this.NAME1 = NAME1;
    }

    public String getNAME2() {
        return NAME2;
    }

    public void setNAME2(String NAME2) {
        this.NAME2 = NAME2;
    }

    public String getCITY_CODE() {
        return CITY_CODE;
    }

    public void setCITY_CODE(String CITY_CODE) {
        this.CITY_CODE = CITY_CODE;
    }

    public String getPOST_CODE1() {
        return POST_CODE1;
    }

    public void setPOST_CODE1(String POST_CODE1) {
        this.POST_CODE1 = POST_CODE1;
    }

    public String getTEL_NUMBER() {
        return TEL_NUMBER;
    }

    public void setTEL_NUMBER(String TEL_NUMBER) {
        this.TEL_NUMBER = TEL_NUMBER;
    }

    public String getTEL_EXTENS() {
        return TEL_EXTENS;
    }

    public void setTEL_EXTENS(String TEL_EXTENS) {
        this.TEL_EXTENS = TEL_EXTENS;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getHOUSE_NUM1() {
        return HOUSE_NUM1;
    }

    public void setHOUSE_NUM1(String HOUSE_NUM1) {
        this.HOUSE_NUM1 = HOUSE_NUM1;
    }

    public String getSTR_SUPPL() {
        return STR_SUPPL;
    }

    public void setSTR_SUPPL(String STR_SUPPL) {
        this.STR_SUPPL = STR_SUPPL;
    }

    public String getSTR_SUPPL3() {
        return STR_SUPPL3;
    }

    public void setSTR_SUPPL3(String STR_SUPPL3) {
        this.STR_SUPPL3 = STR_SUPPL3;
    }

    public String getSTRMN() {
        return STRMN;
    }

    public void setSTRMN(String STRMN) {
        this.STRMN = STRMN;
    }

    public String getSTRUR() {
        return STRUR;
    }

    public void setSTRUR(String STRUR) {
        this.STRUR = STRUR;
    }

    public String getLTRMN() {
        return LTRMN;
    }

    public void setLTRMN(String LTRMN) {
        this.LTRMN = LTRMN;
    }

    public String getLTRUR() {
        return LTRUR;
    }

    public void setLTRUR(String LTRUR) {
        this.LTRUR = LTRUR;
    }

    public String getRUN_FOR_DATE() {
        return RUN_FOR_DATE;
    }

    public void setRUN_FOR_DATE(String RUN_FOR_DATE) {
        this.RUN_FOR_DATE = RUN_FOR_DATE;
    }

    public String getRUN_ON_DATE() {
        return RUN_ON_DATE;
    }

    public void setRUN_ON_DATE(String RUN_ON_DATE) {
        this.RUN_ON_DATE = RUN_ON_DATE;
    }

    public String getRESTRMN() {
        return RESTRMN;
    }

    public void setRESTRMN(String RESTRMN) {
        this.RESTRMN = RESTRMN;
    }

    public String getZZ_RLOAD1() {
        return ZZ_RLOAD1;
    }

    public void setZZ_RLOAD1(String ZZ_RLOAD1) {
        this.ZZ_RLOAD1 = ZZ_RLOAD1;
    }

    public String getZZ_RLOADKVA() {
        return ZZ_RLOADKVA;
    }

    public void setZZ_RLOADKVA(String ZZ_RLOADKVA) {
        this.ZZ_RLOADKVA = ZZ_RLOADKVA;
    }

    public String getZZ_VKONT() {
        return ZZ_VKONT;
    }

    public void setZZ_VKONT(String ZZ_VKONT) {
        this.ZZ_VKONT = ZZ_VKONT;
    }

    public String getStrExistingLoad() {
        return strExistingLoad;
    }

    public void setStrExistingLoad(String strExistingLoad) {
        this.strExistingLoad = strExistingLoad;
    }
}