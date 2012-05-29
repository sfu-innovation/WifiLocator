package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipRequest extends Request{

	private int _userid, _friendid;
	
	public FriendshipRequest( int userid, int friendid ){
		super(RequestTypes.FRIENDSHIP_REQUEST);
		_userid = userid;
		_friendid = friendid;
	}
	
	public String getURL() {
		setProperty( "user_id", ""+_userid, RequestTypes.INT_TYPE);
		setProperty( "friend_id", ""+_friendid, RequestTypes.INT_TYPE);
		return RequestConstants.FRIEND_REQUEST_URL;
	}
}
