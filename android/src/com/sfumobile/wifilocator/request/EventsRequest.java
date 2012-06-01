package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class EventsRequest extends Request{

	private int _id;
	private String _mac_address;
	
	public EventsRequest(int id, String mac_address) {
		super(RequestTypes.EVENTS_REQUEST);
		_id = id;
		_mac_address = mac_address;
	}

	@Override
	public String getURL() {
		setProperty( "user_id", ""+_id, RequestTypes.INT_TYPE);
		setProperty( "mac_address", ""+_mac_address, RequestTypes.STRING_TYPE);
		return RequestConstants.GET_EVENTS_URL;
	}

}
