package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipConfirmRequest extends Request {
	private static final String CONFIRM_FRIENDSHIP_REQUEST_BASE_URL = "http://wifi-location.appspot.com/acceptrequest/";
	int _friendshipId;
	
	public FriendshipConfirmRequest( int friendshipid ){
		super( RequestTypes.CONFIRM_FRIENDSHIP_REQUEST);
		_friendshipId = friendshipid;
	}
	
	public String getURL() {
		setProperty("request_id", ""+_friendshipId, RequestTypes.INT_TYPE);
		return CONFIRM_FRIENDSHIP_REQUEST_BASE_URL;
	}

}
