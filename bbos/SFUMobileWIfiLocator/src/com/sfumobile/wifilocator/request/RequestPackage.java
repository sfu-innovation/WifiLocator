package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.WLANContext;

public class RequestPackage {

	private RequestDelegateScreen _rd;
	private Request _request;
	private WifiLocatorRequestThread _thread;
	
	public RequestPackage( RequestDelegateScreen rd, Request req ){
		_rd = rd;
		_request = req;
	}
	private String[] allowedSSIDs = {"BlueEagle"};
	
	
	private boolean contains( String[] list, String val ){
		int length = list.length;
		boolean retVal = false;
		for(int i = 0; i < length; i++){
			if ( list[i].equals(val)){
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	public void init() {
		System.out.println("[SFUMOBILE] - starting init");
		if ( WLANContext.isAssociated() == WLANContext.WLAN_RADIO_CONNECTED){
			System.out.println("[SFUMOBILE] - connected to an SSID");
			//if ( contains( allowedSSIDs , WLANContext.getCurrentSSID())){
			
			int type = _request.getType();
			String url = _request.getURL();
			String payload = _request.getPayload();
			System.out.println( "[SFUMOBILE] The payload - "+payload);
			//special case since this request is dynamic
			if ( type == RequestTypes.ZONE){
				url += WLANContext.getBSSID();
			}
			System.out.println("[SFUMOBILE] - testing out this url "+url);
			_thread = new WifiLocatorRequestThread(type,
					url,
					payload,
					_rd);
			
			if (_rd.getApplication() == null ) {
				System.out.println("Unable to get instance of the application");
			}else {
			    _rd.getApplication().invokeLater( _thread );
			}
			//} else {
			// _rd.handleError(RequestTypes.REQUEST_ZONE_TYPE, 1, "INCORRECT NETWORK");
			//}
		}
		else {
			String reasonString = null;
			switch( WLANContext.isAssociated()){
			case WLANContext.WLAN_RADIO_NOT_CONNECTED:
					reasonString = "Not Connected to a network"; break;
			case WLANContext.WLAN_RADIO_OFF:
				    reasonString = "Wifi not on"; break;
			}
			_rd.handleError(RequestTypes.ZONE, WLANContext.isAssociated(), reasonString);
		}
	}
}
