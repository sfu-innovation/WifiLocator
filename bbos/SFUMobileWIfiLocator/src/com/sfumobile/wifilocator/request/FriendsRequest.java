package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;


public class FriendsRequest extends Request {
	
	private String          _url;
	
	
	WifiLocatorRequestThread _thread;
	public FriendsRequest( String url ){
		super(RequestTypes.GET_FRIENDS);
		_url = RequestConstants.GET_FRIENDS_BASE_URL+url;
	}
	
	public String getURL() {
		
		return _url;
	}
	
}
