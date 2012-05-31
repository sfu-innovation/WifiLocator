package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class EventsRequest extends LocationRequest{
	
	private int _id;
	public EventsRequest( int userid){
		super( RequestTypes.EVENTS_REQUEST);
		_id = userid;
	}
	
	public String getURL() {
		setProperty("user_id", ""+_id, RequestTypes.INT_TYPE);
		// TODO Auto-generated method stub
		return RequestConstants.GET_EVENTS_BASE_URL;
	}
}
