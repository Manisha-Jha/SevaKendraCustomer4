package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by Rajveer on 3/17/2018.
 */
public class McrOrderProxieOtherConn implements KvmSerializable, Serializable {


    String strKYC;
    String strORDER_NO;
    String strFIRST_NAME;
    String strMIDDLE_NAME;
    String strLAST_NAME;
    String strTITLE;
    String strGENDER;
    String strDOB;
    String strFATHER_NAME;
    String strFN_AS;
    String strMN_AS;
    String strLN_AS;
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
    String strPAN_NO;
    String strID_NO;
    String strDOA;


    String strCA_NUMBER;
    String strReqType;
    String strsMOTHER_NAME;
    String strREASON_S3;
    String strNAME_S3;
    String strGENDER_S3;
    String strDOB_S3;
    String strFATHEERNAME_S3;
    String strELOAD_S4;
    String strRLOAD_S4;
    String strMETERCHOICE_S4;
    String strVLEVEL_S4;
    String strPHASE_S4;
    String strPURPOSE_S5;
    String strDESC_S5;
    String strREASON_S6;

    String strHOUSENO_S6;
    String strBNAME_S6;
    String strSTREET_S6;
    String strAREA_S6;
    String strPIN_S6;
    String strLANDMARK_S6;
    String strLBP_S7;
    String strREASON_S8;
    String strDOV_S8;
    String strMODE_S8;
    String strREASON_S9;
    String strUPTODATE_S9;
    String strAUTO_DEBIT_S10;
    String strImgName;
    String strSignName;
    String strCompany;
    String strRemark;
    String strUser_ID ;
    String strIDName;
    String strText_Remark;
    String strExistingLoad;





    public McrOrderProxieOtherConn(){

    }


    @Override
    public Object getProperty(int arg0) {

        switch (arg0) {
            case 0:
                return strKYC;

            case 1:
                return strCA_NUMBER;

            case 2:
                return strReqType;

            case 3:
                return strORDER_NO;

            case 4:
                return strsMOTHER_NAME;

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
                return strREASON_S3;

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
                return strNAME_S3;

            case 26:
                return strGENDER_S3;

            case 27:
                return strDOB_S3;

            case 28:
                return strFATHEERNAME_S3;

            case 29:
                return strELOAD_S4;

            case 30:
                return strRLOAD_S4;

            case 31:
                return strMETERCHOICE_S4;

            case 32:
                return strVLEVEL_S4;

            case 33:
                return strPHASE_S4;

            case 34:
                return strPURPOSE_S5;

            case 35:
                return strDESC_S5;

            case 36:
                return strREASON_S6;

            case 37:
                return strHOUSENO_S6;

            case 38:
                return strBNAME_S6;

            case 39:
                return strSTREET_S6;

            case 40:
                return strAREA_S6;

            case 41:
                return strPIN_S6;

            case 42:
                return strLANDMARK_S6;

            case 43:
                return strLBP_S7;

            case 44:
                return strREASON_S8;

            case 45:
                return strPAN_NO;

            case 46:
                return strID_NO;

            case 47:
                return strDOV_S8;

            case 48:
                return strMODE_S8;

            case 49:
                return strREASON_S9;

            case 50:
                return strUPTODATE_S9;

            case 51:
                return strAUTO_DEBIT_S10;

            case 52:
                return strFN_AS;

            case 53:
                return strMN_AS;

            case 54:
                return strLN_AS;

            case 55:
                return strImgName;

            case 56:
                return strDOA;

            case 57:
                return strSignName;

            case 58:
                return strCompany;

            case 59:
                return strRemark;

            case 60:
                return strUser_ID;

            case 61:
                return strIDName;

            case 62:
                return strText_Remark;

            case 63:
                return strExistingLoad;



            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 64;
    }

    @Override
    public void setProperty(int arg0, Object arg1) {

        switch (arg0) {
            case 0:
                strKYC = arg1.toString();
                break;

            case 1:
                strCA_NUMBER = arg1.toString();
                break;

            case 2:
                strReqType = arg1.toString();
                break;

            case 3:
                strORDER_NO = arg1.toString();
                break;

            case 4:
                strsMOTHER_NAME = arg1.toString();
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
                strREASON_S3 = arg1.toString();
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
                strNAME_S3 = arg1.toString();
                break;

            case 26:
                strGENDER_S3 = arg1.toString();
                break;

            case 27:
                strDOB_S3 = arg1.toString();
                break;

            case 28:
                strFATHEERNAME_S3 = arg1.toString();
                break;

            case 29:
                strELOAD_S4 = arg1.toString();
                break;

            case 30:
                strRLOAD_S4 = arg1.toString();
                break;

            case 31:
                strMETERCHOICE_S4 = arg1.toString();
                break;

            case 32:
                strVLEVEL_S4 = arg1.toString();
                break;

            case 33:
                strPHASE_S4 = arg1.toString();
                break;

            case 34:
                strPURPOSE_S5 = arg1.toString();
                break;

            case 35:
                strDESC_S5 = arg1.toString();
                break;

            case 36:
                strREASON_S6 = arg1.toString();
                break;

            case 37:
                strHOUSENO_S6 = arg1.toString();
                break;

            case 38:
                strBNAME_S6 = arg1.toString();
                break;

            case 39:
                strSTREET_S6 = arg1.toString();
                break;

            case 40:
                strAREA_S6 = arg1.toString();
                break;

            case 41:
                strPIN_S6 = arg1.toString();
                break;

            case 42:
                strLANDMARK_S6 = arg1.toString();
                break;

            case 43:
                strLBP_S7 = arg1.toString();
                break;

            case 44:
                strREASON_S8 = arg1.toString();
                break;

            case 45:
                strPAN_NO = arg1.toString();
                break;

            case 46:
                strID_NO = arg1.toString();
                break;

            case 47:
                strDOV_S8 = arg1.toString();
                break;

            case 48:
                strMODE_S8 = arg1.toString();
                break;

            case 49:
                strREASON_S9 = arg1.toString();
                break;

            case 50:
                strUPTODATE_S9 = arg1.toString();
                break;

            case 51:
                strAUTO_DEBIT_S10 = arg1.toString();
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
                strImgName = arg1.toString();
                break;

            case 56:
                strDOA = arg1.toString();
                break;

            case 57:
                strSignName = arg1.toString();
                break;

            case 58:
                strCompany = arg1.toString();
                break;


            case 59:
                strRemark = arg1.toString();
                break;

            case 60:
                strUser_ID = arg1.toString();
                break;

            case 61:
                strIDName = arg1.toString();
                break;

            case 62:
                strText_Remark = arg1.toString();
                break;

            case 63:
                strExistingLoad = arg1.toString();
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
                arg2.name = "strCA_NUMBER";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 2:
                arg2.name = "strReqType";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 3:
                arg2.name = "strORDER_NO";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 4:
                arg2.name = "strsMOTHER_NAME";
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
                arg2.name = "strREASON_S3";
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
                arg2.name = "strNAME_S3";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 26:
                arg2.name = "strGENDER_S3";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 27:
                arg2.name = "strDOB_S3";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 28:
                arg2.name = "strFATHEERNAME_S3";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 29:
                arg2.name = "strELOAD_S4";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 30:
                arg2.name = "strRLOAD_S4";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 31:
                arg2.name = "strMETERCHOICE_S4";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 32:
                arg2.name = "strPHONE_NO_PA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 33:
                arg2.name = "strPHASE_S4z";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 34:
                arg2.name = "strPURPOSE_S5";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 35:
                arg2.name = "strDESC_S5";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 36:
                arg2.name = "strREASON_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 37:
                arg2.name = "strHOUSENO_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 38:
                arg2.name = "strBNAME_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 39:
                arg2.name = "strSTREET_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 40:
                arg2.name = "strAREA_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 41:
                arg2.name = "strPIN_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 42:
                arg2.name = "strLANDMARK_S6";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 43:
                arg2.name = "strLBP_S7";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 44:
                arg2.name = "strREASON_S8";
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
                arg2.name = "strDOV_S8";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 48:
                arg2.name = "strMODE_S8";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 49:
                arg2.name = "strREASON_S9";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 50:
                arg2.name = "strUPTODATE_S9";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 51:
                arg2.name = "strAUTO_DEBIT_S10";
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
                arg2.name = "strImgName";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 56:
                arg2.name = "strDOA";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 57:
                arg2.name = "strSignName";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 58:
                arg2.name = "strCompany";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 59:
                arg2.name = "strRemark";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;

            case 60:
                arg2.name = "strUser_ID";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 61:
                arg2.name = "strIDName";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 62:
                arg2.name = "strText_Remark";
                arg2.type = PropertyInfo.STRING_CLASS;

                break;


            case 63:
                arg2.name = "strExistingLoad";
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

    public String getStrORDER_NO() {
        return strORDER_NO;
    }

    public void setStrORDER_NO(String strORDER_NO) {
        this.strORDER_NO = strORDER_NO;
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

    public String getStrDOA() {
        return strDOA;
    }

    public void setStrDOA(String strDOA) {
        this.strDOA = strDOA;
    }

    public String getStrCA_NUMBER() {
        return strCA_NUMBER;
    }

    public void setStrCA_NUMBER(String strCA_NUMBER) {
        this.strCA_NUMBER = strCA_NUMBER;
    }

    public String getStrReqType() {
        return strReqType;
    }

    public void setStrReqType(String strReqType) {
        this.strReqType = strReqType;
    }

    public String getStrsMOTHER_NAME() {
        return strsMOTHER_NAME;
    }

    public void setStrsMOTHER_NAME(String strsMOTHER_NAME) {
        this.strsMOTHER_NAME = strsMOTHER_NAME;
    }

    public String getStrREASON_S3() {
        return strREASON_S3;
    }

    public void setStrREASON_S3(String strREASON_S3) {
        this.strREASON_S3 = strREASON_S3;
    }

    public String getStrNAME_S3() {
        return strNAME_S3;
    }

    public void setStrNAME_S3(String strNAME_S3) {
        this.strNAME_S3 = strNAME_S3;
    }

    public String getStrGENDER_S3() {
        return strGENDER_S3;
    }

    public void setStrGENDER_S3(String strGENDER_S3) {
        this.strGENDER_S3 = strGENDER_S3;
    }

    public String getStrDOB_S3() {
        return strDOB_S3;
    }

    public void setStrDOB_S3(String strDOB_S3) {
        this.strDOB_S3 = strDOB_S3;
    }

    public String getStrFATHEERNAME_S3() {
        return strFATHEERNAME_S3;
    }

    public void setStrFATHEERNAME_S3(String strFATHEERNAME_S3) {
        this.strFATHEERNAME_S3 = strFATHEERNAME_S3;
    }

    public String getStrELOAD_S4() {
        return strELOAD_S4;
    }

    public void setStrELOAD_S4(String strELOAD_S4) {
        this.strELOAD_S4 = strELOAD_S4;
    }

    public String getStrRLOAD_S4() {
        return strRLOAD_S4;
    }

    public void setStrRLOAD_S4(String strRLOAD_S4) {
        this.strRLOAD_S4 = strRLOAD_S4;
    }

    public String getStrMETERCHOICE_S4() {
        return strMETERCHOICE_S4;
    }

    public void setStrMETERCHOICE_S4(String strMETERCHOICE_S4) {
        this.strMETERCHOICE_S4 = strMETERCHOICE_S4;
    }

    public String getStrVLEVEL_S4() {
        return strVLEVEL_S4;
    }

    public void setStrVLEVEL_S4(String strVLEVEL_S4) {
        this.strVLEVEL_S4 = strVLEVEL_S4;
    }

    public String getStrPHASE_S4() {
        return strPHASE_S4;
    }

    public void setStrPHASE_S4(String strPHASE_S4) {
        this.strPHASE_S4 = strPHASE_S4;
    }

    public String getStrPURPOSE_S5() {
        return strPURPOSE_S5;
    }

    public void setStrPURPOSE_S5(String strPURPOSE_S5) {
        this.strPURPOSE_S5 = strPURPOSE_S5;
    }

    public String getStrDESC_S5() {
        return strDESC_S5;
    }

    public void setStrDESC_S5(String strDESC_S5) {
        this.strDESC_S5 = strDESC_S5;
    }

    public String getStrREASON_S6() {
        return strREASON_S6;
    }

    public void setStrREASON_S6(String strREASON_S6) {
        this.strREASON_S6 = strREASON_S6;
    }

    public String getStrHOUSENO_S6() {
        return strHOUSENO_S6;
    }

    public void setStrHOUSENO_S6(String strHOUSENO_S6) {
        this.strHOUSENO_S6 = strHOUSENO_S6;
    }

    public String getStrBNAME_S6() {
        return strBNAME_S6;
    }

    public void setStrBNAME_S6(String strBNAME_S6) {
        this.strBNAME_S6 = strBNAME_S6;
    }

    public String getStrSTREET_S6() {
        return strSTREET_S6;
    }

    public void setStrSTREET_S6(String strSTREET_S6) {
        this.strSTREET_S6 = strSTREET_S6;
    }

    public String getStrAREA_S6() {
        return strAREA_S6;
    }

    public void setStrAREA_S6(String strAREA_S6) {
        this.strAREA_S6 = strAREA_S6;
    }

    public String getStrPIN_S6() {
        return strPIN_S6;
    }

    public void setStrPIN_S6(String strPIN_S6) {
        this.strPIN_S6 = strPIN_S6;
    }

    public String getStrLANDMARK_S6() {
        return strLANDMARK_S6;
    }

    public void setStrLANDMARK_S6(String strLANDMARK_S6) {
        this.strLANDMARK_S6 = strLANDMARK_S6;
    }

    public String getStrLBP_S7() {
        return strLBP_S7;
    }

    public void setStrLBP_S7(String strLBP_S7) {
        this.strLBP_S7 = strLBP_S7;
    }

    public String getStrREASON_S8() {
        return strREASON_S8;
    }

    public void setStrREASON_S8(String strREASON_S8) {
        this.strREASON_S8 = strREASON_S8;
    }

    public String getStrDOV_S8() {
        return strDOV_S8;
    }

    public void setStrDOV_S8(String strDOV_S8) {
        this.strDOV_S8 = strDOV_S8;
    }

    public String getStrMODE_S8() {
        return strMODE_S8;
    }

    public void setStrMODE_S8(String strMODE_S8) {
        this.strMODE_S8 = strMODE_S8;
    }

    public String getStrREASON_S9() {
        return strREASON_S9;
    }

    public void setStrREASON_S9(String strREASON_S9) {
        this.strREASON_S9 = strREASON_S9;
    }

    public String getStrUPTODATE_S9() {
        return strUPTODATE_S9;
    }

    public void setStrUPTODATE_S9(String strUPTODATE_S9) {
        this.strUPTODATE_S9 = strUPTODATE_S9;
    }

    public String getStrAUTO_DEBIT_S10() {
        return strAUTO_DEBIT_S10;
    }

    public void setStrAUTO_DEBIT_S10(String strAUTO_DEBIT_S10) {
        this.strAUTO_DEBIT_S10 = strAUTO_DEBIT_S10;
    }

    public String getStrImgName() {
        return strImgName;
    }

    public void setStrImgName(String strImgName) {
        this.strImgName = strImgName;
    }

    public String getStrSignName() {
        return strSignName;
    }

    public void setStrSignName(String strSignName) {
        this.strSignName = strSignName;
    }

    public String getStrCompany() {
        return strCompany;
    }

    public void setStrCompany(String strCompany) {
        this.strCompany = strCompany;
    }

    public String getStrRemark() {
        return strRemark;
    }

    public void setStrRemark(String strRemark) {
        this.strRemark = strRemark;
    }

    public String getStrUser_ID() {
        return strUser_ID;
    }

    public void setStrUser_ID(String strUser_ID) {
        this.strUser_ID = strUser_ID;
    }

    public String getStrIDName() {
        return strIDName;
    }

    public void setStrIDName(String strIDName) {
        this.strIDName = strIDName;
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