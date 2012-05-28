package com.sfumobile.wifilocator.request;

public class ConfirmFriendshipRequest extends Request{

	private int _request_id;
	
	public ConfirmFriendshipRequest(int request_id) {
		super(1);
		_request_id = request_id;
	}

	@Override
	public String getURL() {
		setProperty( "request_id", ""+_request_id, "1");
		return RequestConstants.ACCEPT_FRIEND_REQUEST_URL;
	}

}
