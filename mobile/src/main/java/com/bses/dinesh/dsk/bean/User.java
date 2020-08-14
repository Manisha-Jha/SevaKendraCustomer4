package com.bses.dinesh.dsk.bean;

public class User {

	String USER_ID;
	String NAME;
	String ACTIVE_FLAG;
	String PASSWORD;
	String PHONE_NUMBER;
	String IMEI_NUMBER;
	String LOGIN_DATE;
	String MODULE_CODE;
	String PHONE_NUMBER1;
	String EMAIL_ID;
	String ROLE_ID;
	String ROLE_DESC;
	String COMP_CODE;
	String APP_VERSION_WEB;
	String errorMsg;




	public String getACTIVE_FLAG() {
		return ACTIVE_FLAG;
	}

	public void setACTIVE_FLAG(String ACTIVE_FLAG) {
		this.ACTIVE_FLAG = ACTIVE_FLAG;
	}

	public String getLOGIN_DATE() {
		return LOGIN_DATE;
	}

	public void setLOGIN_DATE(String LOGIN_DATE) {
		this.LOGIN_DATE = LOGIN_DATE;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}

	public String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}

	public void setPHONE_NUMBER(String PHONE_NUMBER) {
		this.PHONE_NUMBER = PHONE_NUMBER;
	}

	public String getIMEI_NUMBER() {
		return IMEI_NUMBER;
	}

	public void setIMEI_NUMBER(String IMEI_NUMBER) {
		this.IMEI_NUMBER = IMEI_NUMBER;
	}

	public String getMODULE_CODE() {
		return MODULE_CODE;
	}

	public void setMODULE_CODE(String MODULE_CODE) {
		this.MODULE_CODE = MODULE_CODE;
	}

	public String getPHONE_NUMBER1() {
		return PHONE_NUMBER1;
	}

	public void setPHONE_NUMBER1(String PHONE_NUMBER1) {
		this.PHONE_NUMBER1 = PHONE_NUMBER1;
	}

	public String getEMAIL_ID() {
		return EMAIL_ID;
	}

	public void setEMAIL_ID(String EMAIL_ID) {
		this.EMAIL_ID = EMAIL_ID;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String ROLE_ID) {
		this.ROLE_ID = ROLE_ID;
	}

	public String getROLE_DESC() {
		return ROLE_DESC;
	}

	public void setROLE_DESC(String ROLE_DESC) {
		this.ROLE_DESC = ROLE_DESC;
	}

	public String getCOMP_CODE() {
		return COMP_CODE;
	}

	public void setCOMP_CODE(String COMP_CODE) {
		this.COMP_CODE = COMP_CODE;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getAPP_VERSION_WEB() {
		return APP_VERSION_WEB;
	}

	public void setAPP_VERSION_WEB(String APP_VERSION_WEB) {
		this.APP_VERSION_WEB = APP_VERSION_WEB;
	}
}
