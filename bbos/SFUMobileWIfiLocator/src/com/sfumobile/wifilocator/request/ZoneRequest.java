package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.utils.WLANContext;
import com.sfumobile.wifilocator.request.WifiLocatorRequestThread;

public class ZoneRequest implements IRequest {
	
	private RequestDelegate _rd;
	private String          _bssid;
	private String          _url;
	private String          _user;
	
	WifiLocatorRequestThread _thread;
	public ZoneRequest( RequestDelegate rd){
		_rd = rd;
	}
	public void init(){
		if ( WLANContext.isAssociated() == WLANContext.WLAN_RADIO_CONNECTED){
			_user = "Alex";
			_bssid = /*"00:1f:45:64:16:c8";*/WLANContext.getBSSID();
			_url = "http://wifi-location.appspot.com/getzone/"+_user+"/"+_bssid;
			_thread = new WifiLocatorRequestThread(RequestTypes.REQUEST_ZONE_TYPE, _url, _rd);
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
