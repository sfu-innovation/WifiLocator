package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipConfirmRequest extends Request{

	private int _request_id;
	
	public FriendshipConfirmRequest(String request_id) {
		super(RequestTypes.CONFIRM_FRIENDSHIP_REQUEST);
		_request_id = Integer.parseInt(request_id);
	}

	@Override
	public String getURL() {
		setProperty( "request_id", ""+_request_id, RequestTypes.INT_TYPE);
		return RequestConstants.ACCEPT_FRIEND_REQUEST_URL;
	}

}
