package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by Rajveer on 3/17/2018.
 */
public class McrOrderProxie implements KvmSerializable, Serializable {


    String strKYC;
    String strProfile;
    String strTechDetails;
    String strORDER_NO;
    String strREGTYPE;
    String strFIRST_NAME;
    String strMIDDLE_NAME;
    String strLAST_NAME;
    String strTITLE;
    String strGENDER;
    String strDOB;
    String strFATHER_NAME;
    String strMOTHER_NAME;
    String strDESIGNATION_AS;
    String strTYPE_ORG;
    String strDOI;
    String strHOUSE_NO;
    String strBUILDING_NAME;

    String strSTREET;
    String strAREA;
    String strPIN;
    String strLANDMARK;
    String strMOBILE_NO;
    String strPHONE_NO;
    String strEMAIL;
    String strHOUSE_NO_PA;
    String strBUILDING_NAME_PA;
    String strSTREET_PA;
    String strAREA_PA;
    String strPIN_PA;
    String strLANDMARK_PA;
    String strMOBILE_NO_PA;
    String strPHONE_NO_PA;
    String strEMAIL_PA;
    String strAPPLIED_CATEGORY;
    String strNEWOREXISTING;
    String strSERVICE_REQ;

    String strBILLING_TYPE;
    String strAREA_TYPE;
    String strPREMISES_TYPE;
    String strPURPOSE;
    String strMETER_CHOICE;
    String strAPPLIED_LOAD;
    String strAPPLIED_VOLTAGE_LVL;
    String strAPPLIED_PHASE;
    String strPAN_NO;
    String strID_NO;
    String strPIC_NAME;
    String strSIG_NAME;
    String strCOMPANY;
    String strLOAD_TYPE;
    String strAADHAR_NO;
    String strFN_AS;
    String strMN_AS;


    String strLN_AS;
    String strFINGER_NAME;
    String strDOA;
    String strCF_REMARK;
    String strZZ_CONNTYPE;
    String strUser_id;
    String strText_Remark;

    String strExistingLoad;


    public McrOrderProxie(){

    }


    @Override
    public Object getProperty(int arg0) {

        switch (arg0) {
            case 0:
                return strKYC;

            case 1:
                return strProfile;

            case 2:
                return strTechDetails;

            case 3:
                return strORDER_NO;

            case 4:
                return strREGTYPE;

            case 5:
                return strFIRST_NAME;

            case 6:
                return strMIDDLE_NAME;

            case 7:
                return strLAST_NAME;

            case 8:
                return strTITLE;

            case 9:
                return strGENDER;

            case 10:
                return strDOB;

            case 11:
                return strFATHER_NAME;

            case 12:
                return strMOTHER_NAME;

            case 13:
                return strDESIGNATION_AS;

            case 14:
                return strTYPE_ORG;

            case 15:
                return strDOI;

            case 16:
                return strHOUSE_NO;

            case 17:
                return strBUILDING_NAME;

            case 18:
                return strSTREET;

            case 19:
                return strAREA;

            case 20:
                return strPIN;

            case 21:
                return strLANDMARK;

            case 22:
                return strMOBILE_NO;

            case 23:
                return strPHONE_NO;

            case 24:
                return strEMAIL;

            case 25:
                return strHOUSE_NO_PA;

            case 26:
                return strBUILDING_NAME_PA;

            case 27:
                return strAREA_PA;

            case 28:
                return strSTREET_PA;

            case 29:
                return strPIN_PA;

            case 30:
                return strLANDMARK_PA;

            case 31:
                return strMOBILE_NO_PA;

            case 32:
                return strPHONE_NO_PA;

            case 33:
                return strEMAIL_PA;

            case 34:
                return strAPPLIED_CATEGORY;

            case 35:
                return strNEWOREXISTING;

            case 36:
                return strSERVICE_REQ;

            case 37:
                return strBILLING_TYPE;

            case 38:
                return strAREA_TYPE;

            case 39:
                return strPREMISES_TYPE;

            case 40:
                return strPURPOSE;

            case 41:
                return strMETER_CHOICE;

            case 42:
                return strAPPLIED_LOAD;

            case 43:
                return strAPPLIED_VOLTAGE_LVL;

            case 44:
                return strAPPLIED_PHASE;

            case 45:
                return strPAN_NO;

            case 46:
                return strID_NO;

            case 47:
                return strPIC_NAME;

            case 48:
                return strSIG_NAME;

            case 49:
                return strCOMPANY;

            case 50:
                return strLOAD_TYPE;

            case 51:
                return strAADHAR_NO;

            case 52:
                return strFN_AS;

            case 53:
                return strMN_AS;

            case 54:
                return strLN_AS;

            case 55:
                return strFINGER_NAME;

            case 56:
                return strDOA;

            case 57:
                return strCF_REMARK;

            case 58:
                return strZZ_CONNTYPE;

            case 59:
                return strUser_id;

            case 60:
                return strText_Remark;




            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 61;
    }

    @Override
    public void setProperty(int arg0, Object arg1) {

        switch (arg0) {
            case 0:
                strKYC = arg1.toString();
                break;

            case 1:
                strProfile = arg1.toString();
                break;

            case 2:
                strTechDetails = arg1.toString();
                break;

            case 3:
                strORDER_NO = arg1.toString();
                break;

            case 4:
                strREGTYPE = arg1.toString();
                break;

            case 5:
                strFIRST_NAME = arg1.toString();
                break;

            case 6:
                strMIDDLE_NAME = arg1.toString();
                break;

            case 7:
                strLAST_NAME = arg1.toString();
                break;

            case 8:
                strTITLE = arg1.toString();
                break;

            case 9:
                strGENDER = arg1.toString();
                break;

            case 10:
                strDOB = arg1.toString();
                break;

            case 11:
                strFATHER_NAME = arg1.toString();
                break;

            case 12:
                strMOTHER_NAME = arg1.toString();
                break;

            case 13:
                strDESIGNATION_AS = arg1.toString();
                break;

            case 14:
                strTYPE_ORG = arg1.toString();
                break;

            case 15:
                strDOI = arg1.toString();
                break;

            case 16:
                strHOUSE_NO = arg1.toString();
                break;

            case 17:
                strBUILDING_NAME = arg1.toString();
                break;

            case 18:
                strSTREET = arg1.toString();
                break;

            case 19:
                strAREA = arg1.toString();
                break;

            case 20:
                strPIN = arg1.toString();
                break;

            case 21:
                strLANDMARK = arg1.toString();
                break;

            case 22:
                strMOBILE_NO = arg1.toString();
                break;

            case 23:
                strPHONE_NO = arg1.toString();
                break;

            case 24:
                strEMAIL = arg1.toString();
                break;

            case 25:
                strHOUSE_NO_PA = arg1.toString();
                break;

            case 26:
                strBUILDING_NAME_PA = arg1.toString();
                break;

            case 27:
                strAREA_PA = arg1.toString();
                break;

            case 28:
                strSTREET_PA = arg1.toString();
                break;

            case 29:
                strPIN_PA = arg1.toString();
                break;

            case 30:
                strLANDMARK_PA = arg1.toString();
                break;

            case 31:
                strMOBILE_NO_PA = arg1.toString();
                break;

            case 32:
                strPHONE_NO_PA = arg1.toString();
                break;

            case 33:
                strEMAIL_PA = arg1.toString();
                break;

            case 34:
                strAPPLIED_CATEGORY = arg1.toString();
                break;

            case 35:
                strNEWOREXISTING = arg1.toString();
                break;

            case 36:
                strSERVICE_REQ = arg1.toString();
                break;

            case 37:
                strBILLING_TYPE = arg1.toString();
                break;

            case 38:
                strAREA_TYPE = arg1.toString();
                break;

            case 39:
                strPREMISES_TYPE = arg1.toString();
                break;

            case 40:
                strPURPOSE = arg1.toString();
                break;

            case 41:
                strMETER_CHOICE = arg1.toString();
                break;

            case 42:
                strAPPLIED_LOAD = arg1.toString();
                break;

            case 43:
                strAPPLIED_VOLTAGE_LVL = arg1.toString();
                break;

            case 44:
                strAPPLIED_PHASE = arg1.toString();
                break;

            case 45:
                strPAN_NO = arg1.toString();
                break;

            case 46:
                strID_NO = arg1.toString();
                break;

            case 47:
                strPIC_NAME = arg1.toString();
                break;

            case 48:
                strSIG_NAME = arg1.toString();
                break;

            case 49:
                strCOMPANY = arg1.toString();
                break;

            case 50:
                strLOAD_TYPE = arg1.toString();
                break;

            case 51:
                strAADHAR_NO = arg1.toString();
                break;

            case 52:
                strFN_AS = arg1.toString();
                break;

            case 53:
                strMN_AS = arg1.toString();
                break;

            case 54:
                strLN_AS = arg1.toString();
                break;

            case 55:
                strFINGER_NAME = arg1.toString();
                break;

            case 56:
                strDOA = arg1.toString();
                break;

            case 57:
                strCF_REMARK = arg1.toString();
                break;

            case 58:
                strZZ_CONNTYPE = arg1.toString();
                break;


            case 59:
                strUser_id = arg1.toString();
                break;

            case 60:
                strText_Remark = arg1.toString();
                break;


            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {

        switch (arg0) {
            case 0:
                arg2.name = "strKYC";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 1:
                arg2.name = "strProfile";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 2:
                arg2.name = "strTechDetails";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 3:
                arg2.name = "strORDER_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 4:
                arg2.name = "strREGTYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 5:
                arg2.name = "strFIRST_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 6:
                arg2.name = "strMIDDLE_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 7:
                arg2.name = "strLAST_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 8:
                arg2.name = "strTITLE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 9:
                arg2.name = "strGENDER";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 10:
                arg2.name = "strDOB";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 11:
                arg2.name = "strFATHER_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 12:
                arg2.name = "strMOTHER_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 13:
                arg2.name = "strDESIGNATION_AS";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 14:
                arg2.name = "strTYPE_ORG";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 15:
                arg2.name = "strDOI";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 16:
                arg2.name = "strHOUSE_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 17:
                arg2.name = "strBUILDING_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 18:
                arg2.name = "strSTREET";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 19:
                arg2.name = "strAREA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 20:
                arg2.name = "strPIN";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 21:
                arg2.name = "strLANDMARK";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 22:
                arg2.name = "strMOBILE_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 23:
                arg2.name = "strPHONE_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 24:
                arg2.name = "strEMAIL";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 25:
                arg2.name = "strHOUSE_NO_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 26:
                arg2.name = "strBUILDING_NAME_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 27:
                arg2.name = "strSTREET_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 28:
                arg2.name = "METERBOXSEAL1";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 29:
                arg2.name = "strPIN_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 30:
                arg2.name = "strLANDMARK_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 31:
                arg2.name = "strMOBILE_NO_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 32:
                arg2.name = "strPHONE_NO_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 33:
                arg2.name = "strEMAIL_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 34:
                arg2.name = "strAPPLIED_CATEGORY";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 35:
                arg2.name = "strNEWOREXISTING";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 36:
                arg2.name = "strSERVICE_REQ";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 37:
                arg2.name = "strBILLING_TYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 38:
                arg2.name = "strAREA_TYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 39:
                arg2.name = "strPREMISES_TYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 40:
                arg2.name = "strPURPOSE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 41:
                arg2.name = "strMETER_CHOICE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 42:
                arg2.name = "strAPPLIED_LOAD";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 43:
                arg2.name = "strAPPLIED_VOLTAGE_LVL";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 44:
                arg2.name = "strAPPLIED_PHASE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 45:
                arg2.name = "strPAN_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 46:
                arg2.name = "strID_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 47:
                arg2.name = "strPIC_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 48:
                arg2.name = "strSIG_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 49:
                arg2.name = "strCOMPANY";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 50:
                arg2.name = "strLOAD_TYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 51:
                arg2.name = "strAADHAR_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 52:
                arg2.name = "strFN_AS";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 53:
                arg2.name = "strMN_AS";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 54:
                arg2.name = "strLN_AS";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 55:
                arg2.name = "strFINGER_NAME";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 56:
                arg2.name = "strDOA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 57:
                arg2.name = "strCF_REMARK";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 58:
                arg2.name = "strZZ_CONNTYPE";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 59:
                arg2.name = "strUser_id";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 60:
                arg2.name = "strText_Remark";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;




            default:
                break;
        }
    }


    public String getStrKYC() {
        return strKYC;
    }

    public void setStrKYC(String strKYC) {
        this.strKYC = strKYC;
    }

    public String getStrProfile() {
        return strProfile;
    }

    public void setStrProfile(String strProfile) {
        this.strProfile = strProfile;
    }

    public String getStrTechDetails() {
        return strTechDetails;
    }

    public void setStrTechDetails(String strTechDetails) {
        this.strTechDetails = strTechDetails;
    }

    public String getStrORDER_NO() {
        return strORDER_NO;
    }

    public void setStrORDER_NO(String strORDER_NO) {
        this.strORDER_NO = strORDER_NO;
    }

    public String getStrREGTYPE() {
        return strREGTYPE;
    }

    public void setStrREGTYPE(String strREGTYPE) {
        this.strREGTYPE = strREGTYPE;
    }

    public String getStrFIRST_NAME() {
        return strFIRST_NAME;
    }

    public void setStrFIRST_NAME(String strFIRST_NAME) {
        this.strFIRST_NAME = strFIRST_NAME;
    }

    public String getStrMIDDLE_NAME() {
        return strMIDDLE_NAME;
    }

    public void setStrMIDDLE_NAME(String strMIDDLE_NAME) {
        this.strMIDDLE_NAME = strMIDDLE_NAME;
    }

    public String getStrLAST_NAME() {
        return strLAST_NAME;
    }

    public void setStrLAST_NAME(String strLAST_NAME) {
        this.strLAST_NAME = strLAST_NAME;
    }

    public String getStrTITLE() {
        return strTITLE;
    }

    public void setStrTITLE(String strTITLE) {
        this.strTITLE = strTITLE;
    }

    public String getStrGENDER() {
        return strGENDER;
    }

    public void setStrGENDER(String strGENDER) {
        this.strGENDER = strGENDER;
    }

    public String getStrDOB() {
        return strDOB;
    }

    public void setStrDOB(String strDOB) {
        this.strDOB = strDOB;
    }

    public String getStrFATHER_NAME() {
        return strFATHER_NAME;
    }

    public void setStrFATHER_NAME(String strFATHER_NAME) {
        this.strFATHER_NAME = strFATHER_NAME;
    }

    public String getStrMOTHER_NAME() {
        return strMOTHER_NAME;
    }

    public void setStrMOTHER_NAME(String strMOTHER_NAME) {
        this.strMOTHER_NAME = strMOTHER_NAME;
    }

    public String getStrDESIGNATION_AS() {
        return strDESIGNATION_AS;
    }

    public void setStrDESIGNATION_AS(String strDESIGNATION_AS) {
        this.strDESIGNATION_AS = strDESIGNATION_AS;
    }

    public String getStrTYPE_ORG() {
        return strTYPE_ORG;
    }

    public void setStrTYPE_ORG(String strTYPE_ORG) {
        this.strTYPE_ORG = strTYPE_ORG;
    }

    public String getStrDOI() {
        return strDOI;
    }

    public void setStrDOI(String strDOI) {
        this.strDOI = strDOI;
    }

    public String getStrHOUSE_NO() {
        return strHOUSE_NO;
    }

    public void setStrHOUSE_NO(String strHOUSE_NO) {
        this.strHOUSE_NO = strHOUSE_NO;
    }

    public String getStrBUILDING_NAME() {
        return strBUILDING_NAME;
    }

    public void setStrBUILDING_NAME(String strBUILDING_NAME) {
        this.strBUILDING_NAME = strBUILDING_NAME;
    }

    public String getStrSTREET() {
        return strSTREET;
    }

    public void setStrSTREET(String strSTREET) {
        this.strSTREET = strSTREET;
    }

    public String getStrAREA() {
        return strAREA;
    }

    public void setStrAREA(String strAREA) {
        this.strAREA = strAREA;
    }

    public String getStrPIN() {
        return strPIN;
    }

    public void setStrPIN(String strPIN) {
        this.strPIN = strPIN;
    }

    public String getStrLANDMARK() {
        return strLANDMARK;
    }

    public void setStrLANDMARK(String strLANDMARK) {
        this.strLANDMARK = strLANDMARK;
    }

    public String getStrMOBILE_NO() {
        return strMOBILE_NO;
    }

    public void setStrMOBILE_NO(String strMOBILE_NO) {
        this.strMOBILE_NO = strMOBILE_NO;
    }

    public String getStrPHONE_NO() {
        return strPHONE_NO;
    }

    public void setStrPHONE_NO(String strPHONE_NO) {
        this.strPHONE_NO = strPHONE_NO;
    }

    public String getStrEMAIL() {
        return strEMAIL;
    }

    public void setStrEMAIL(String strEMAIL) {
        this.strEMAIL = strEMAIL;
    }

    public String getStrHOUSE_NO_PA() {
        return strHOUSE_NO_PA;
    }

    public void setStrHOUSE_NO_PA(String strHOUSE_NO_PA) {
        this.strHOUSE_NO_PA = strHOUSE_NO_PA;
    }

    public String getStrBUILDING_NAME_PA() {
        return strBUILDING_NAME_PA;
    }

    public void setStrBUILDING_NAME_PA(String strBUILDING_NAME_PA) {
        this.strBUILDING_NAME_PA = strBUILDING_NAME_PA;
    }

    public String getStrSTREET_PA() {
        return strSTREET_PA;
    }

    public void setStrSTREET_PA(String strSTREET_PA) {
        this.strSTREET_PA = strSTREET_PA;
    }

    public String getStrAREA_PA() {
        return strAREA_PA;
    }

    public void setStrAREA_PA(String strAREA_PA) {
        this.strAREA_PA = strAREA_PA;
    }

    public String getStrPIN_PA() {
        return strPIN_PA;
    }

    public void setStrPIN_PA(String strPIN_PA) {
        this.strPIN_PA = strPIN_PA;
    }

    public String getStrLANDMARK_PA() {
        return strLANDMARK_PA;
    }

    public void setStrLANDMARK_PA(String strLANDMARK_PA) {
        this.strLANDMARK_PA = strLANDMARK_PA;
    }

    public String getStrMOBILE_NO_PA() {
        return strMOBILE_NO_PA;
    }

    public void setStrMOBILE_NO_PA(String strMOBILE_NO_PA) {
        this.strMOBILE_NO_PA = strMOBILE_NO_PA;
    }

    public String getStrPHONE_NO_PA() {
        return strPHONE_NO_PA;
    }

    public void setStrPHONE_NO_PA(String strPHONE_NO_PA) {
        this.strPHONE_NO_PA = strPHONE_NO_PA;
    }

    public String getStrEMAIL_PA() {
        return strEMAIL_PA;
    }

    public void setStrEMAIL_PA(String strEMAIL_PA) {
        this.strEMAIL_PA = strEMAIL_PA;
    }

    public String getStrAPPLIED_CATEGORY() {
        return strAPPLIED_CATEGORY;
    }

    public void setStrAPPLIED_CATEGORY(String strAPPLIED_CATEGORY) {
        this.strAPPLIED_CATEGORY = strAPPLIED_CATEGORY;
    }

    public String getStrNEWOREXISTING() {
        return strNEWOREXISTING;
    }

    public void setStrNEWOREXISTING(String strNEWOREXISTING) {
        this.strNEWOREXISTING = strNEWOREXISTING;
    }

    public String getStrSERVICE_REQ() {
        return strSERVICE_REQ;
    }

    public void setStrSERVICE_REQ(String strSERVICE_REQ) {
        this.strSERVICE_REQ = strSERVICE_REQ;
    }

    public String getStrBILLING_TYPE() {
        return strBILLING_TYPE;
    }

    public void setStrBILLING_TYPE(String strBILLING_TYPE) {
        this.strBILLING_TYPE = strBILLING_TYPE;
    }

    public String getStrAREA_TYPE() {
        return strAREA_TYPE;
    }

    public void setStrAREA_TYPE(String strAREA_TYPE) {
        this.strAREA_TYPE = strAREA_TYPE;
    }

    public String getStrPREMISES_TYPE() {
        return strPREMISES_TYPE;
    }

    public void setStrPREMISES_TYPE(String strPREMISES_TYPE) {
        this.strPREMISES_TYPE = strPREMISES_TYPE;
    }

    public String getStrPURPOSE() {
        return strPURPOSE;
    }

    public void setStrPURPOSE(String strPURPOSE) {
        this.strPURPOSE = strPURPOSE;
    }

    public String getStrMETER_CHOICE() {
        return strMETER_CHOICE;
    }

    public void setStrMETER_CHOICE(String strMETER_CHOICE) {
        this.strMETER_CHOICE = strMETER_CHOICE;
    }

    public String getStrAPPLIED_LOAD() {
        return strAPPLIED_LOAD;
    }

    public void setStrAPPLIED_LOAD(String strAPPLIED_LOAD) {
        this.strAPPLIED_LOAD = strAPPLIED_LOAD;
    }

    public String getStrAPPLIED_VOLTAGE_LVL() {
        return strAPPLIED_VOLTAGE_LVL;
    }

    public void setStrAPPLIED_VOLTAGE_LVL(String strAPPLIED_VOLTAGE_LVL) {
        this.strAPPLIED_VOLTAGE_LVL = strAPPLIED_VOLTAGE_LVL;
    }

    public String getStrAPPLIED_PHASE() {
        return strAPPLIED_PHASE;
    }

    public void setStrAPPLIED_PHASE(String strAPPLIED_PHASE) {
        this.strAPPLIED_PHASE = strAPPLIED_PHASE;
    }

    public String getStrPAN_NO() {
        return strPAN_NO;
    }

    public void setStrPAN_NO(String strPAN_NO) {
        this.strPAN_NO = strPAN_NO;
    }

    public String getStrID_NO() {
        return strID_NO;
    }

    public void setStrID_NO(String strID_NO) {
        this.strID_NO = strID_NO;
    }

    public String getStrPIC_NAME() {
        return strPIC_NAME;
    }

    public void setStrPIC_NAME(String strPIC_NAME) {
        this.strPIC_NAME = strPIC_NAME;
    }

    public String getStrSIG_NAME() {
        return strSIG_NAME;
    }

    public void setStrSIG_NAME(String strSIG_NAME) {
        this.strSIG_NAME = strSIG_NAME;
    }

    public String getStrCOMPANY() {
        return strCOMPANY;
    }

    public void setStrCOMPANY(String strCOMPANY) {
        this.strCOMPANY = strCOMPANY;
    }

    public String getStrLOAD_TYPE() {
        return strLOAD_TYPE;
    }

    public void setStrLOAD_TYPE(String strLOAD_TYPE) {
        this.strLOAD_TYPE = strLOAD_TYPE;
    }

    public String getStrAADHAR_NO() {
        return strAADHAR_NO;
    }

    public void setStrAADHAR_NO(String strAADHAR_NO) {
        this.strAADHAR_NO = strAADHAR_NO;
    }

    public String getStrFN_AS() {
        return strFN_AS;
    }

    public void setStrFN_AS(String strFN_AS) {
        this.strFN_AS = strFN_AS;
    }

    public String getStrMN_AS() {
        return strMN_AS;
    }

    public void setStrMN_AS(String strMN_AS) {
        this.strMN_AS = strMN_AS;
    }

    public String getStrLN_AS() {
        return strLN_AS;
    }

    public void setStrLN_AS(String strLN_AS) {
        this.strLN_AS = strLN_AS;
    }

    public String getStrFINGER_NAME() {
        return strFINGER_NAME;
    }

    public void setStrFINGER_NAME(String strFINGER_NAME) {
        this.strFINGER_NAME = strFINGER_NAME;
    }

    public String getStrDOA() {
        return strDOA;
    }

    public void setStrDOA(String strDOA) {
        this.strDOA = strDOA;
    }

    public String getStrCF_REMARK() {
        return strCF_REMARK;
    }

    public void setStrCF_REMARK(String strCF_REMARK) {
        this.strCF_REMARK = strCF_REMARK;
    }

    public String getStrZZ_CONNTYPE() {
        return strZZ_CONNTYPE;
    }

    public void setStrZZ_CONNTYPE(String strZZ_CONNTYPE) {
        this.strZZ_CONNTYPE = strZZ_CONNTYPE;
    }

    public String getStrUser_id() {
        return strUser_id;
    }

    public void setStrUser_id(String strUser_id) {
        this.strUser_id = strUser_id;
    }

    public String getStrText_Remark() {
        return strText_Remark;
    }

    public void setStrText_Remark(String strText_Remark) {
        this.strText_Remark = strText_Remark;
    }
    public String getStrExistingLoad() {
        return strExistingLoad;
    }

    public void setStrExistingLoad(String strExistingLoad) {
        this.strExistingLoad = strExistingLoad;
    }

}