package com.sfumobile.wifilocator.request;

public class FriendListRequest extends Request{
	
	private int _user_id;
	
	public FriendListRequest(int id){
		super(RequestTypes.FRIENDLIST);
		_user_id = id;
	}
	
	@Override
	public String getURL(){
		setProperty( "user_id", ""+_user_id, RequestTypes.INT_TYPE);
		return RequestConstants.GET_FRIENDS_URL;
	}

}
