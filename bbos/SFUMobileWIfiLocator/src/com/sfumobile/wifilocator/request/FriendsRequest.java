package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.utils.WLANContext;

public class FriendsRequest implements IRequest {
	RequestDelegate _rd;
	String          _user;
	String          _url;
	WifiLocatorRequestThread _thread;
	public FriendsRequest( RequestDelegate rd){
		_rd = rd;
	}
	
	
	public void init(){
		if ( WLANContext.isAssociated() == WLANContext.WLAN_RADIO_CONNECTED){
			_user = "Alex";
			_url = "http://wifi-location.appspot.com/getfriends/"+_user;
			_thread = new WifiLocatorRequestThread(RequestTypes.REQUEST_FRIENDS_TYPE, _url, _rd);
			if (_rd.getApplication() == null ) {
				System.out.println("Unable to get instance of the application");
			}else {
			    _rd.getApplication().invokeLater( _thread );
			}
		}
		else {
			_rd.handleError(RequestTypes.REQUEST_ZONE_TYPE, 1, "WIFI NOT ACTIVE");
		}
	}
	
}
