package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class ChangePasswordProxie implements KvmSerializable {
	
	String strUser_id;
	String strCurrent_Password;
	String strNew_Password;

	public String getStrUser_id() {
		return strUser_id;
	}

	public void setStrUser_id(String strUser_id) {
		this.strUser_id = strUser_id;
	}

	public String getStrCurrent_Password() {
		return strCurrent_Password;
	}

	public void setStrCurrent_Password(String strCurrent_Password) {
		this.strCurrent_Password = strCurrent_Password;
	}

	public String getStrNew_Password() {
		return strNew_Password;
	}

	public void setStrNew_Password(String strNew_Password) {
		this.strNew_Password = strNew_Password;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			
			return strUser_id;
			
		case 1:
			
			return strCurrent_Password;

		case 2:

			return strNew_Password;


		default:
			return null;
		}
		
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		case 0:
			arg2.name = "strUser_id";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;
			
		case 1:
			arg2.name = "strCurrent_Password";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;

		case 2:
			arg2.name = "strNew_Password";
			arg2.type = PropertyInfo.STRING_CLASS;

			break;

		default:
			break;
		}
		
	}
	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:

			strUser_id = arg1.toString();
			break;
			
		case 1:
			strCurrent_Password = arg1.toString();
			break;

		case 2:
			strNew_Password = arg1.toString();
			break;


		default:
			break;
		}
		
	}
	

}
