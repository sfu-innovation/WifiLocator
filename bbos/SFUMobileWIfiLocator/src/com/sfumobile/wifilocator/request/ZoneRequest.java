package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.WLANContext;
import com.sfumobile.wifilocator.request.WifiLocatorRequestThread;

public class ZoneRequest extends Request {
	
	private int          _user;
	private String       _bssid;

	WifiLocatorRequestThread _thread;
	public ZoneRequest( int userid/*, String bssid*/){
		super(RequestTypes.ZONE);
		_user = userid;
		//_bssid = bssid;
	}
	
	
	public String getURL() {
		setProperty("user_id", ""+_user, RequestTypes.INT_TYPE);
		//setProperty("mac_address", _bssid, RequestTypes.STRING_TYPE);
		
		return RequestConstants.GET_ZONE_BASE_URL;
	}
	
}
