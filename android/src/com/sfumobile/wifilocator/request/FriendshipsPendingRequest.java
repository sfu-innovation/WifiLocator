package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipsPendingRequest extends Request{

	private int _user_id;
	
	public FriendshipsPendingRequest(int user_id) {
		super(RequestTypes.GET_FRIENDSHIP_REQUESTS);
		_user_id = user_id;
	}

	@Override
	public String getURL() {
		setProperty( "user_id", ""+_user_id, RequestTypes.INT_TYPE);
		return RequestConstants.GET_FRIEND_REQUESTS_URL;
	}

}
