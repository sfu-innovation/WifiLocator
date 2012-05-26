package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;


public class FriendsRequest extends Request {
	private static final String GET_FRIENDS_BASE_URL = "http://wifi-location.appspot.com/getfriends/";
	private String          _url;
	
	
	WifiLocatorRequestThread _thread;
	public FriendsRequest( String url ){
		super(RequestTypes.GET_FRIENDS);
		_url = GET_FRIENDS_BASE_URL+url;
	}
	
	public String getURL() {
		
		return _url;
	}
	
}
