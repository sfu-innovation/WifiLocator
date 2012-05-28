package com.sfumobile.wifilocator.request;

public class FriendshipsPendingRequest extends Request{

	private int _user_id;
	
	public FriendshipsPendingRequest(int user_id) {
		super(1);
		_user_id = user_id;
	}

	@Override
	public String getURL() {
		setProperty( "user_id", ""+_user_id, "1");
		return RequestConstants.GET_FRIEND_REQUESTS_URL;
	}

}
