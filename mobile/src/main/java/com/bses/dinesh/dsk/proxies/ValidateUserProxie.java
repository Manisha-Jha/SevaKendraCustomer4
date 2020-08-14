package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class ValidateUserProxie implements KvmSerializable {

	String strUser_id;
	String strPassword;
	String strIMEI;
	String strLongitude;
	String strLatitude;


	public String getStrUser_id() {
		return strUser_id;
	}

	public void setStrUser_id(String strUser_id) {
		this.strUser_id = strUser_id;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrIMEI() {
		return strIMEI;
	}

	public void setStrIMEI(String strIMEI) {
		this.strIMEI = strIMEI;
	}

	public String getStrLongitude() {
		return strLongitude;
	}

	public void setStrLongitude(String strLongitude) {
		this.strLongitude = strLongitude;
	}

	public String getStrLatitude() {
		return strLatitude;
	}

	public void setStrLatitude(String strLatitude) {
		this.strLatitude = strLatitude;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:

				return strUser_id;

			case 1:

				return strPassword;


			case 2:

				return strIMEI;


			case 3:

				return strLongitude;


			case 4:

				return strLatitude;



			default:
				return null;
		}

	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
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
				arg2.name = "strPassword";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 2:
				arg2.name = "strIMEI";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;


			case 3:
				arg2.name = "strLongitude";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;


			case 4:
				arg2.name = "strLatitude";
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
				strPassword = arg1.toString();
				break;

			case 2:
				strIMEI = arg1.toString();
				break;


			case 3:
				strLongitude = arg1.toString();
				break;


			case 5:
				strLatitude = arg1.toString();
				break;


			default:
				break;
		}

	}


}
