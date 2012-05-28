package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;


public class FriendsRequest extends Request {
	
	private int _userid;
	
	WifiLocatorRequestThread _thread;
	public FriendsRequest( int userid ){
		super(RequestTypes.GET_FRIENDS);
		_userid = userid;
	}
	
	public String getURL() {
		setProperty("user_id", ""+_userid, RequestTypes.INT_TYPE);
		return RequestConstants.GET_FRIENDS_BASE_URL;
	}
	
}
