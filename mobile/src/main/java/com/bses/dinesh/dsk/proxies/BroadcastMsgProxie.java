package com.bses.dinesh.dsk.proxies;



import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class BroadcastMsgProxie implements KvmSerializable {


	String UPDATED_BY;
	String TASK_ALLOTED_TO;
	String TASK_STATUS;
	String TASK_FINAL_STATUS;
	String TASK_ID;
	String TASK_REMARKS;

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String UPDATED_BY) {
		this.UPDATED_BY = UPDATED_BY;
	}

	public String getTASK_ALLOTED_TO() {
		return TASK_ALLOTED_TO;
	}

	public void setTASK_ALLOTED_TO(String TASK_ALLOTED_TO) {
		this.TASK_ALLOTED_TO = TASK_ALLOTED_TO;
	}

	public String getTASK_STATUS() {
		return TASK_STATUS;
	}

	public void setTASK_STATUS(String TASK_STATUS) {
		this.TASK_STATUS = TASK_STATUS;
	}

	public String getTASK_FINAL_STATUS() {
		return TASK_FINAL_STATUS;
	}

	public void setTASK_FINAL_STATUS(String TASK_FINAL_STATUS) {
		this.TASK_FINAL_STATUS = TASK_FINAL_STATUS;
	}

	public String getTASK_ID() {
		return TASK_ID;
	}

	public void setTASK_ID(String TASK_ID) {
		this.TASK_ID = TASK_ID;
	}

	public String getTASK_REMARKS() {
		return TASK_REMARKS;
	}

	public void setTASK_REMARKS(String TASK_REMARKS) {
		this.TASK_REMARKS = TASK_REMARKS;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:

				return UPDATED_BY;

			case 1:

				return TASK_ALLOTED_TO;

			case 2:

				return TASK_STATUS;

			case 3:

				return TASK_FINAL_STATUS;


			case 4:

				return TASK_ID;

			case 5:

				return TASK_REMARKS;

			default:
				return null;
		}

	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}


	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub

		switch (arg0) {

			case 0:
				arg2.name = "UPDATED_BY";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 1:
				arg2.name = "TASK_ALLOTED_TO";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 2:
				arg2.name = "TASK_STATUS";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 3:
				arg2.name = "TASK_FINAL_STATUS";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 4:
				arg2.name = "TASK_ID";
				arg2.type = PropertyInfo.STRING_CLASS;

				break;

			case 5:
				arg2.name = "TASK_REMARKS";
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

				UPDATED_BY = arg1.toString();
				break;

			case 1:
				TASK_ALLOTED_TO = arg1.toString();
				break;

			case 2:
				TASK_STATUS = arg1.toString();
				break;

			case 3:
				TASK_FINAL_STATUS = arg1.toString();
				break;

			case 4:

				TASK_ID = arg1.toString();
				break;

			case 5:

				TASK_REMARKS = arg1.toString();
				break;





			default:
				break;
		}

	}


}
