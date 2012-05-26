package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipRequest extends Request {
	private static final String GET_FRIENDSHIP_BASE_URL = "http://wifi-location.appspot.com/sendrequest/ ";
	private int _userid, _friendid;
	
	
	WifiLocatorRequestThread _thread;
	public FriendshipRequest( int userid, int friendid ){
		super(RequestTypes.INIT_FRIENDSHIP);
		_userid = userid;
		_friendid = friendid;
	}
	
	public String getURL() {
		setProperty( "user_id", ""+_userid);
		setProperty( "friend_id", ""+_friendid);
		return GET_FRIENDSHIP_BASE_URL;
	}
	
}
