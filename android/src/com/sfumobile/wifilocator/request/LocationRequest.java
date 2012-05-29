package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class LocationRequest extends Request{

	private String _bssid;
	private int _user_id;
	
	public LocationRequest(int user_id, String bssid) {
		super(RequestTypes.ZONE);
		_user_id = user_id;
		_bssid = bssid;
	}

	@Override
	public String getURL() {
		setProperty("mac_address", ""+_bssid, RequestTypes.STRING_TYPE);
		setProperty("user_id", ""+_user_id, RequestTypes.INT_TYPE);
		return RequestConstants.GETZONE_URL;

	}

}
