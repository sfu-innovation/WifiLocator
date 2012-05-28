package com.sfumobile.wifilocator.request;

public class FriendshipRequest extends Request{

	private int _userid, _friendid;
	
	public FriendshipRequest( int userid, int friendid ){
		super(1);
		_userid = userid;
		_friendid = friendid;
	}
	
	public String getURL() {
		setProperty( "user_id", ""+_userid, "1");
		setProperty( "friend_id", ""+_friendid, "1");
		return RequestConstants.FRIEND_REQUEST_URL;
	}
}
