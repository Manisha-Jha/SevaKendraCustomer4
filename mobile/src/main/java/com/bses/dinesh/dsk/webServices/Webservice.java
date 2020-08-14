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
import com.bses.dinesh.dsk.utils.ApplicationException;

import java.util.ArrayList;


public interface Webservice {

	//public boolean registerUser(UserRegistration userRegistration, Context context) throws ApplicationException;
	public User validateUser(ValidateUserProxie validateUserProxie, Context context)throws ApplicationException;
	public ChangePassBean ChangePassword(ChangePasswordProxie changePasswordProxie, Context context)throws ApplicationException;
	public ArrayList<McrOrederBean> getEmpMessages(EmpMessagesProxie empMessagesProxie, Context context)throws ApplicationException;
	//broadcast msg
	public BroadcastMsgBean broadcastMsg(BroadcastMsgProxie broadcastMsgProxie, Context context)throws ApplicationException;

	//seal validation
	public PunchingDataBean checkSealData(SealValidateProxie sealValidateProxie, Context context)throws ApplicationException;
	//seal validation
	public PunchingDataBean cancelOrderRequest(OrderCancelProxie orderCancelProxie, Context context)throws ApplicationException;


	public PunchingDataBean sendNewPunchData(McrOrderProxie mcrOrderProxie, Context context)throws ApplicationException;

	public PunchingDataBean sendNewPunchDataOtherConn(McrOrderProxieOtherConn mcrOrderProxieOtherConn, Context context)throws ApplicationException;


}
