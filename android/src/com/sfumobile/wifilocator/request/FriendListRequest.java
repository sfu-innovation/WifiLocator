package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendListRequest extends Request{
	
	private int _user_id;
	
	public FriendListRequest(int id){
		super(RequestTypes.GET_FRIENDS);
		_user_id = id;
	}
	
	@Override
	public String getURL(){
		setProperty( "user_id", ""+_user_id, RequestTypes.INT_TYPE);
		return RequestConstants.GET_FRIENDS_URL;
	}

}
