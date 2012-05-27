package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipRequestRetrieval extends Request {
	private static final String GET_FRIENDSHIP_REQUESTS_BASE_URL = "http://wifi-location.appspot.com/getrequests/"; 
	int _userid;
	public FriendshipRequestRetrieval(int userid){
		super(RequestTypes.GET_FRIENDSHIP_REQUESTS);
		_userid = userid;
	}
	
	public String getURL() {
		setProperty("user_id", ""+_userid, RequestTypes.INT_TYPE);
		return GET_FRIENDSHIP_REQUESTS_BASE_URL;
	}
	
	

}
