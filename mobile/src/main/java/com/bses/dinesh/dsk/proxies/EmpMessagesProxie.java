package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class EmpMessagesProxie implements KvmSerializable {

	String strUser_id;
	String strDivision;

	public String getStrUser_id() {
		return strUser_id;
	}

	public void setStrUser_id(String strUser_id) {
		this.strUser_id = strUser_id;
	}

	public String getStrDivision() {
		return strDivision;
	}

	public void setStrDivision(String strDivision) {
		this.strDivision = strDivision;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:

				return strUser_id;

			case 1:

				return strDivision;

			default:
				return null;
		}

	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
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
				arg2.name = "strDivision";
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
				strDivision = arg1.toString();
				break;

			default:
				break;
		}

	}


}
