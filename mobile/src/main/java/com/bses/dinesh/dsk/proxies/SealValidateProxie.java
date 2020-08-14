package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class SealValidateProxie implements KvmSerializable {


	String SealNo;

	public String getSealNo() {
		return SealNo;
	}

	public void setSealNo(String sealNo) {
		SealNo = sealNo;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:

				return SealNo;


			default:
				return null;
		}

	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub

		switch (arg0) {

			case 0:
				arg2.name = "SealNo";
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

				SealNo = arg1.toString();
				break;


			default:
				break;
		}

	}


}
