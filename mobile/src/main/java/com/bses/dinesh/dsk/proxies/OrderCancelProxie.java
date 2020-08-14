package com.bses.dinesh.dsk.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class OrderCancelProxie implements KvmSerializable {


	String Installer_ID;
	String Order_ID;
	String Reason;
	String Image;
	String Remarks;


	public String getInstaller_ID() {
		return Installer_ID;
	}

	public void setInstaller_ID(String installer_ID) {
		Installer_ID = installer_ID;
	}

	public String getOrder_ID() {
		return Order_ID;
	}

	public void setOrder_ID(String order_ID) {
		Order_ID = order_ID;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:

				return Installer_ID;

			case 1:

				return Order_ID;

			case 2:

				return Reason;

			case 3:

				return Image;

			case 4:

				return Remarks;


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
				arg2.name = "Installer_ID";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 1:
				arg2.name = "Order_ID";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 2:
				arg2.name = "Reason";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 3:
				arg2.name = "Image";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 4:
				arg2.name = "Remarks";
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

				Installer_ID = arg1.toString();
				break;

			case 1:

				Order_ID = arg1.toString();
				break;

			case 2:

				Reason = arg1.toString();
				break;

			case 3:

				Image = arg1.toString();
				break;

			case 4:

				Remarks = arg1.toString();
				break;


			default:
				break;
		}

	}


}
