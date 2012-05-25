package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.WLANContext;
import com.sfumobile.wifilocator.request.WifiLocatorRequestThread;

public class ZoneRequest extends Request {
	
	private RequestDelegate _rd;
	private String          _bssid;
	private String          _url;
	private String          _user;
	private static final String GET_ZONE_BASE_URL = "http://wifi-location.appspot.com/getzone/";
	WifiLocatorRequestThread _thread;
	public ZoneRequest( String user ){
		super(RequestTypes.REQUEST_ZONE_TYPE);
		_user = user;
		_url = GET_ZONE_BASE_URL + _user +"/";
	}
	
	
	public String getURL() {
		
		return _url;
	}
	
}
