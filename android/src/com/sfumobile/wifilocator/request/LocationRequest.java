package com.sfumobile.wifilocator.request;

public class LocationRequest extends Request{

	private String _bssid;
	private int _user_id;
	
	public LocationRequest(int user_id, String bssid) {
		super(1);
		_user_id = user_id;
		_bssid = bssid;
	}

	@Override
	public String getURL() {
		setProperty("mac_address", ""+_bssid, "1");
		setProperty("user_id", ""+_user_id, "1");
		return RequestConstants.GETZONE_URL;

	}

}
