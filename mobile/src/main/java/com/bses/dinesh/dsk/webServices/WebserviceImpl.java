package com.bses.dinesh.dsk.webServices;

import android.content.Context;

import com.bses.dinesh.dsk.bean.BroadcastMsgBean;
import com.bses.dinesh.dsk.bean.ChangePassBean;
import com.bses.dinesh.dsk.bean.McrOrederBean;
import com.bses.dinesh.dsk.bean.PunchingDataBean;
import com.bses.dinesh.dsk.bean.User;
import com.bses.dinesh.dsk.proxies.BroadcastMsgProxie;
import com.bses.dinesh.dsk.proxies.ChangePasswordProxie;
import com.bses.dinesh.dsk.proxies.EmpMessagesProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxie;
import com.bses.dinesh.dsk.proxies.McrOrderProxieOtherConn;
import com.bses.dinesh.dsk.proxies.OrderCancelProxie;
import com.bses.dinesh.dsk.proxies.SealValidateProxie;
import com.bses.dinesh.dsk.proxies.ValidateUserProxie;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.utils.ApplicationConstants;
import com.bses.dinesh.dsk.utils.ApplicationException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class WebserviceImpl implements Webservice {

    Context context;
    private static final int MAX_RETRIES = 3;
    private int timeout = 60000 * 2;

    public Object retreiveResponse(SoapObject responce, String methodname) {

        if (methodname.equalsIgnoreCase(ApplicationConstants.ADD_CA_ACCOUNT)) {
            if (responce.toString().contains("true")) {
                return true;
            } else {
                return false;
            }
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_USER)) {


            User user = new User();

            SoapObject soapObject = (SoapObject) responce.getProperty("Seva_get_Login_DetailsResult");
            SoapObject soapObject1 = (SoapObject) soapObject.getProperty("diffgram");

            if (soapObject1.toString().contains("Result_Datils")) {
                SoapObject innerObject = (SoapObject) (((SoapObject) ((SoapObject) soapObject.getProperty("diffgram")).getProperty("Result")));
                SoapObject innerInObject = (SoapObject) innerObject.getProperty("Result_Datils");

                try {


                    if (innerObject.toString().contains("USER_ID")) {
                        user.setUSER_ID(innerInObject.getProperty("USER_ID").toString());

                    }

                    if (innerObject.toString().contains("NAME")) {
                        user.setNAME(innerInObject.getProperty("NAME").toString());
                    }

                    if (innerObject.toString().contains("COMPANY")) {
                        user.setACTIVE_FLAG(innerInObject.getProperty("COMPANY").toString());
                    }


                    if (innerObject.toString().contains("DESIGNATION")) {
                        user.setIMEI_NUMBER(innerInObject.getProperty("DESIGNATION").toString());
                    }

                    if (innerObject.toString().contains("LOGIN_DATE")) {
                        user.setLOGIN_DATE(innerInObject.getProperty("LOGIN_DATE").toString());
                    }


                    if (innerObject.toString().contains("LOGIN_TYPE")) {
                        user.setROLE_ID(innerInObject.getProperty("LOGIN_TYPE").toString());
                    }

                    if (innerObject.toString().contains("ROLE_ID")) {
                        user.setROLE_ID(innerInObject.getProperty("ROLE_ID").toString());
                    }

                    if (innerObject.toString().contains("DIVISION")) {
                        user.setCOMP_CODE(innerInObject.getProperty("DIVISION").toString());
                    }

                    //user.setAPP_VERSION_WEB("1.1");
                    if (innerObject.toString().contains("APP_VERSION_WEB")) {
                        user.setAPP_VERSION_WEB(innerInObject.getProperty("APP_VERSION_WEB").toString());

                    }
                    if (innerObject.toString().contains("PHONE_NUMBER")) {
                        user.setPHONE_NUMBER(innerInObject.getProperty("PHONE_NUMBER").toString());

                    }
                    if (innerObject.toString().contains("EMAIL_ID")) {
                        user.setEMAIL_ID(innerInObject.getProperty("EMAIL_ID").toString());

                    }

                } catch (Exception er) {
                    er.getStackTrace();
                }

            }

            return user;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_CHANGE_PASS)) {


            ChangePassBean changePass = new ChangePassBean();
            String output = "";
            try {

                String val = responce.toString();

                if (val.contains("True")) {
                    output = "true";
                    changePass.setSuccessMsg("true");
                    changePass.setErrorMsg("");
                    //msgSentAck.setACK("true");
                } else {
                    output = "false";
                    changePass.setSuccessMsg("false");
                    changePass.setErrorMsg("");
                    //msgSentAck.setACK("false");
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
            //changePass.setSuccessMsg(output);

            return changePass;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_EMP_MESSAGES)) {

            ArrayList<McrOrederBean> msgs = new ArrayList<McrOrederBean>();


            SoapObject soapObject = (SoapObject) responce.getProperty("Seva_get_Order_DetailsResult");
            SoapObject soapObject1 = (SoapObject) soapObject.getProperty("diffgram");

            if (soapObject1.toString().contains("Result")) {

                SoapObject innerObject = (SoapObject) ((SoapObject) ((SoapObject) soapObject.getProperty("diffgram")).getProperty("Result"));
                //SoapObject innerInObject = (SoapObject)innerObject.getProperty("iMsgLoginCredentials");

                for (int i = 0; i < innerObject.getPropertyCount(); i++) {

                    McrOrederBean msg = new McrOrederBean();
                    SoapObject msgObj = (SoapObject) innerObject.getProperty(i);


                    if (msgObj.toString().contains("ORDERNO")) {
                        msg.setORDERNO(msgObj.getProperty("ORDERNO").toString());
                    }


                    if (msgObj.toString().contains("NAME")) {
                        msg.setNAME(msgObj.getProperty("NAME").toString());
                    }


                    if (msgObj.toString().contains("NAME_FIRST")) {
                        msg.setNAME_FIRST(msgObj.getProperty("NAME_FIRST").toString());
                    }
                    if (msgObj.toString().contains("NAME_LAST")) {
                        msg.setNAME_LAST(msgObj.getProperty("NAME_LAST").toString());
                    }


                    if (msgObj.toString().contains("FATHERNAME")) {
                        msg.setFATHERNAME(msgObj.getProperty("FATHERNAME").toString());
                    }
                    if (msgObj.toString().contains("CITY")) {
                        msg.setCITY(msgObj.getProperty("CITY").toString());
                    }
                    if (msgObj.toString().contains("PINCODE")) {
                        msg.setPINCODE(msgObj.getProperty("PINCODE").toString());
                    }
                    if (msgObj.toString().contains("DIVISION")) {
                        msg.setDIVISION(msgObj.getProperty("DIVISION").toString());
                    }
                    if (msgObj.toString().contains("REQ_TYPE")) {
                        msg.setREQ_TYPE(msgObj.getProperty("REQ_TYPE").toString());
                    }
                    if (msgObj.toString().contains("ZZ_RLOAD")) {
                        msg.setZZ_RLOAD(msgObj.getProperty("ZZ_RLOAD").toString());
                    }
                    if (msgObj.toString().contains("EMAIL")) {
                        msg.setEMAIL(msgObj.getProperty("EMAIL").toString());
                    }
                    if (msgObj.toString().contains("ADDRESS")) {
                        msg.setADDRESS(msgObj.getProperty("ADDRESS").toString());
                    }
                    if (msgObj.toString().contains("MOBILENO")) {
                        msg.setMOBILENO(msgObj.getProperty("MOBILENO").toString());
                    }
                    if (msgObj.toString().contains("FLAG")) {
                        msg.setFLAG(msgObj.getProperty("FLAG").toString());
                    }
                    if (msgObj.toString().contains("APPDT")) {
                        msg.setAPPDT(msgObj.getProperty("APPDT").toString());
                    }
                    if (msgObj.toString().contains("AUFNR")) {
                        msg.setAUFNR(msgObj.getProperty("AUFNR").toString());
                    }
                    if (msgObj.toString().contains("AUART")) {
                        msg.setAUART(msgObj.getProperty("AUART").toString());
                    }
                    if (msgObj.toString().contains("ERDAT")) {
                        msg.setERDAT(msgObj.getProperty("ERDAT").toString());
                    }

                    if (msgObj.toString().contains("BUKRS")) {
                        msg.setBUKRS(msgObj.getProperty("BUKRS").toString());
                    }

                    if (msgObj.toString().contains("VAPLZ")) {
                        msg.setVAPLZ(msgObj.getProperty("VAPLZ").toString());
                    }
                    if (msgObj.toString().contains("KUNUM")) {
                        msg.setKUNUM(msgObj.getProperty("KUNUM").toString());
                    }

                    if (msgObj.toString().contains("ILART")) {
                        msg.setILART(msgObj.getProperty("ILART").toString());
                    }

                    if (msgObj.toString().contains("GSTRP")) {
                        msg.setGSTRP(msgObj.getProperty("GSTRP").toString());
                    }
                    if (msgObj.toString().contains("GSUZP")) {
                        msg.setGSUZP(msgObj.getProperty("GSUZP").toString());
                    }

                    // new fields
                    if (msgObj.toString().contains("BPKIND")) {
                        msg.setBPKIND(msgObj.getProperty("BPKIND").toString());
                    }

                    if (msgObj.toString().contains("ZZ_VKONT")) {
                        msg.setZZ_VKONT(msgObj.getProperty("ZZ_VKONT").toString());
                    }


                    if (msgObj.toString().contains("TITLE")) {
                        msg.setTITLE(msgObj.getProperty("TITLE").toString());
                    }


                    if (msgObj.toString().contains("STREET")) {
                        if (msgObj.getProperty("STREET") == null) {
                            msg.setSTREET("NA");
                        } else {
                            msg.setSTREET(msgObj.getProperty("STREET").toString());
                        }
                    }

                    if (msgObj.toString().contains("HOUSE_NUM1")) {
                        msg.setHOUSE_NUM1(msgObj.getProperty("HOUSE_NUM1").toString());
                    }


                    if (msgObj.toString().contains("STR_SUPPL1")) {
                        msg.setSTR_SUPPL(msgObj.getProperty("STR_SUPPL1").toString());


                    }
                    if (msgObj.toString().contains("STR_SUPPL2")) {

                        msg.setSTR_SUPPL(msgObj.getProperty("STR_SUPPL2").toString());


                    }

                    if (msgObj.toString().contains("STR_SUPPL3")) {
                        msg.setSTR_SUPPL3(msgObj.getProperty("STR_SUPPL3").toString());

                    }

//

                    if (msgObj.toString().contains("getPOST_CODE1")) {
                        msg.setPOST_CODE1(msgObj.getProperty("getPOST_CODE1").toString());
                    }

                    try {
                        // existing load
                        if (msgObj.toString().contains("EXISTING_LOAD")) {
                            msg.setStrExistingLoad(msgObj.getProperty("EXISTING_LOAD").toString());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                    msgs.add(msg);
                    //System.out.println(".............OPERATIONAL_COMP_NO = "+msgObject.getProperty("OPERATIONAL_COMP_NO").toString());
                }


            } else {

            }

            return msgs;

        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_BROADCAST_MSG)) {

            BroadcastMsgBean msgSentAck = new BroadcastMsgBean();
            String output = "";


            if (responce.toString().contains("Task Alloted succesfully.")) {
                output = "true";
            } else {
                output = "false";
            }
            msgSentAck.setACK(output);

            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.SUBMIT_ALLOCATION)) {

            BroadcastMsgBean msgSentAck = new BroadcastMsgBean();
            String output = "";
            try {

                String val = responce.toString().charAt(responce.toString().length() - 4) + "";

                if (val.equalsIgnoreCase("T")) {
                    output = "true";
                    //msgSentAck.setACK("true");
                } else {
                    output = "false";
                    //msgSentAck.setACK("false");
                }
            } catch (Exception er) {
                er.printStackTrace();
            }


            msgSentAck.setACK(output);

            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.CREATE_INSTALLER)) {

            BroadcastMsgBean msgSentAck = new BroadcastMsgBean();
            String output = "";

            try {
                String val = responce.toString().charAt(responce.toString().length() - 4) + "";

                if (val.equalsIgnoreCase("T")) {
                    output = "true";
                    //msgSentAck.setACK("true");
                } else {
                    output = "false";
                    //msgSentAck.setACK("false");
                }
            } catch (Exception er) {
                er.printStackTrace();
            }


            msgSentAck.setACK(output);

            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_RV)) {


            PunchingDataBean msgSentAck = new PunchingDataBean();
            SoapObject soapObject = (SoapObject) responce.getProperty("Seva_Insert_Data_New_ConnResult");
            SoapObject soapObject1 = (SoapObject) soapObject.getProperty("diffgram");

            if (soapObject1.toString().contains("Result_Datils")) {
                SoapObject innerObject = (SoapObject) (((SoapObject) ((SoapObject) soapObject.getProperty("diffgram")).getProperty("Result")));
                SoapObject innerInObject = (SoapObject) innerObject.getProperty("Result_Datils");

                try {
                    if (innerObject.toString().contains("messageText")) {
                        msgSentAck.setACK(innerInObject.getProperty("messageText").toString());
                    } else {
                        msgSentAck.setACK("False");
                    }
                } catch (Exception er) {
                    er.getStackTrace();
                }

            }

            return msgSentAck;
        }

        if (methodname.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_RV_NEW)) {


            PunchingDataBean msgSentAck = new PunchingDataBean();
            SoapObject soapObject = (SoapObject) responce.getProperty("Seva_Insert_Data_New_ConnResult");
            SoapObject soapObject1 = (SoapObject) soapObject.getProperty("diffgram");

            if (soapObject1.toString().contains("Result_Datils")) {
                SoapObject innerObject = (SoapObject) (((SoapObject) ((SoapObject) soapObject.getProperty("diffgram")).getProperty("Result")));
                SoapObject innerInObject = (SoapObject) innerObject.getProperty("Result_Datils");

                try {
                    if (innerObject.toString().contains("messageText")) {
                        msgSentAck.setACK(innerInObject.getProperty("messageText").toString());
                    } else {
                        msgSentAck.setACK("False");
                    }
                } catch (Exception er) {
                    er.getStackTrace();
                }

            }

            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_OTHER_CONN)) {

            PunchingDataBean msgSentAck = new PunchingDataBean();

            SoapObject soapObject = (SoapObject) responce.getProperty("Seva_Insert_Data_Other_ConnectionResult");
            SoapObject soapObject1 = (SoapObject) soapObject.getProperty("diffgram");

            if (soapObject1.toString().contains("Result_Datils")) {
                SoapObject innerObject = (SoapObject) (((SoapObject) ((SoapObject) soapObject.getProperty("diffgram")).getProperty("Result")));
                SoapObject innerInObject = (SoapObject) innerObject.getProperty("Result_Datils");

                try {
                    if (innerObject.toString().contains("messageText")) {
                        msgSentAck.setACK(innerInObject.getProperty("messageText").toString());
                    } else {
                        msgSentAck.setACK("False");
                    }
                } catch (Exception er) {
                    er.getStackTrace();
                }

            }

            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_Img_RV)) {

            PunchingDataBean msgSentAck = new PunchingDataBean();
            String output = "";

            try {
                if (responce.toString().contains("T")) {
                    output = "true";
                } else {
                    output = "false";
                }
                msgSentAck.setACK(output);
            } catch (Exception er) {
                msgSentAck.setACK("false");
                er.printStackTrace();
            }

            return msgSentAck;
        }

        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_SEAL)) {

            PunchingDataBean msgSentAck = new PunchingDataBean();
            String output = "";

            //SoapObject soapObject=  (SoapObject) responce.getProperty("IMSGMsgTextTBSendResult");
            String val = responce.toString().charAt(responce.toString().length() - 4) + "";


            if (val.equalsIgnoreCase("T")) {
                output = "true";
                msgSentAck.setACK("true");
            } else if (val.equalsIgnoreCase("C")) {
                output = "consumed";
                msgSentAck.setACK("consumed");
            } else {
                output = "false";
                msgSentAck.setACK("false");
            }


            return msgSentAck;
        }


        if (methodname.equalsIgnoreCase(ApplicationConstants.GET_CANCEL_ORDER)) {

            PunchingDataBean msgSentAck = new PunchingDataBean();
            String output = "";

            //SoapObject soapObject=  (SoapObject) responce.getProperty("IMSGMsgTextTBSendResult");
            String val = responce.toString().charAt(responce.toString().length() - 4) + "";


            if (val.equalsIgnoreCase("T")) {
                output = "true";
                msgSentAck.setACK("true");
            } else if (val.equalsIgnoreCase("C")) {
                output = "consumed";
                msgSentAck.setACK("consumed");
            } else {
                output = "false";
                msgSentAck.setACK("false");
            }


            return msgSentAck;
        }


        return methodname;
    }


    public Object callWebService(Object request, String methodname, String methodType) throws ApplicationException {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Z_BAPI_DSS_ISU_CA_DISPLAY

        System.out.println("inside call " + methodname);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.implicitTypes = true;
        envelope.headerOut = new Element[1];
        envelope.headerOut[0] = buildAuthHeader();
        setMapping(envelope, methodname);


        HttpTransportSE httpTransportSE;

        try {
            httpTransportSE = new HttpTransportSE(ApplicationConstants.WEB_SERVICE_URL);
            System.out.println("..............connected with " + ApplicationConstants.WEB_SERVICE_URL);
            //AppLog appLog = new AppLog();


            httpTransportSE.call(ApplicationConstants.TARGE_NAME_SPACE + methodname, envelope);

            if (envelope.bodyIn instanceof SoapObject) {

                SoapObject result = (SoapObject) envelope.bodyIn;
                System.out.println("......response = " + result.toString());

                if (result == null) {
                    System.out.println("................call webservice 1");
                    ApplicationException exception = new ApplicationException();
                    exception.setErrorMsg(ApplicationConstants.ERROR_MSG_SERVER_DOWN);
                    throw exception;
                } else {
                    System.out.println("................call webservice 3");
                    return retreiveResponse(result, methodname);
                }
            } else {

                System.out.println("................call webservice 2");

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


    public void setMapping(SoapSerializationEnvelope envelope, String methodName) {

        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_USER)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ValidateUserProxie.class);
        }
        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_CHANGE_PASS)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ChangePasswordProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_EMP_MESSAGES)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, EmpMessagesProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_BROADCAST_MSG)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, BroadcastMsgProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_SEAL)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, SealValidateProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.GET_CANCEL_ORDER)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, OrderCancelProxie.class);
        }

        //New Mapping
        if (methodName.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_RV)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, McrOrderProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_RV_NEW)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, McrOrderProxie.class);
        }

        if (methodName.equalsIgnoreCase(ApplicationConstants.MCR_Insert_INPUT_Data_OTHER_CONN)) {
            envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, McrOrderProxieOtherConn.class);
        }


    }


    private Element buildAuthHeader() {
        Element h = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "UserCredentials");
        Element username = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "userName");
        username.addChild(Node.TEXT, "BSESGOOGLEAPP");
        h.addChild(Node.ELEMENT, username);
        Element pass = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "password");
        pass.addChild(Node.TEXT, "b$e$g00gle@pp");
        h.addChild(Node.ELEMENT, pass);

        return h;
    }


    @Override
    public User validateUser(ValidateUserProxie validateUserProxie, Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(validateUserProxie, ApplicationConstants.GET_VALIDATE_USER, ApplicationConstants.MethodType3);

        if (obj instanceof User) {
            return (User) obj;
        } else
            return new User();
    }

    @Override
    public ChangePassBean ChangePassword(ChangePasswordProxie changePasswordProxie, Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(changePasswordProxie, ApplicationConstants.GET_CHANGE_PASS, ApplicationConstants.MethodType3);
        if (obj instanceof ChangePassBean) {
            return (ChangePassBean) obj;
        } else
            return new ChangePassBean();
    }


    @Override
    public ArrayList<McrOrederBean> getEmpMessages(EmpMessagesProxie empMessagesProxie,
                                                   Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(empMessagesProxie, ApplicationConstants.GET_EMP_MESSAGES, ApplicationConstants.MethodType3);
        if (obj instanceof ArrayList<?>) {
            return (ArrayList<McrOrederBean>) obj;
        } else {
            return new ArrayList<McrOrederBean>();
        }
    }


    @Override
    public BroadcastMsgBean broadcastMsg(BroadcastMsgProxie broadcastMsgProxie, Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(broadcastMsgProxie, ApplicationConstants.GET_BROADCAST_MSG, ApplicationConstants.MethodType3);
        if (obj instanceof BroadcastMsgBean) {
            return (BroadcastMsgBean) obj;
        } else
            return new BroadcastMsgBean();
    }


    @Override
    public PunchingDataBean checkSealData(SealValidateProxie sealValidateProxie, Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(sealValidateProxie, ApplicationConstants.GET_VALIDATE_SEAL, ApplicationConstants.MethodType3);
        if (obj instanceof PunchingDataBean) {
            return (PunchingDataBean) obj;
        } else
            return new PunchingDataBean();
    }


    @Override
    public PunchingDataBean cancelOrderRequest(OrderCancelProxie orderCancelProxie, Context context) throws ApplicationException {
        // TODO Auto-generated method stub
        this.context = context;
        Object obj = callWebService(orderCancelProxie, ApplicationConstants.GET_CANCEL_ORDER, ApplicationConstants.MethodType3);
        if (obj instanceof PunchingDataBean) {
            return (PunchingDataBean) obj;
        } else
            return new PunchingDataBean();
    }


    @Override
    public PunchingDataBean sendNewPunchData(McrOrderProxie mcrOrderProxie, Context context) throws ApplicationException {
        this.context = context;
        System.out.print(mcrOrderProxie);
        Object obj = callWebService(mcrOrderProxie, ApplicationConstants.MCR_Insert_INPUT_Data_RV_NEW, ApplicationConstants.MethodType1);
        if (obj instanceof PunchingDataBean) {
            return (PunchingDataBean) obj;
        } else
            return new PunchingDataBean();
    }


    @Override
    public PunchingDataBean sendNewPunchDataOtherConn(McrOrderProxieOtherConn mcrOrderProxieOtherConn, Context context) throws ApplicationException {
        this.context = context;
        System.out.print(mcrOrderProxieOtherConn);
        Object obj = callWebService(mcrOrderProxieOtherConn, ApplicationConstants.MCR_Insert_INPUT_Data_OTHER_CONN, ApplicationConstants.MethodType1);
        if (obj instanceof PunchingDataBean) {
            return (PunchingDataBean) obj;
        } else
            return new PunchingDataBean();
    }


}
