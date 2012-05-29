package com.sfumobile.wifilocator.response;

public class LocationResponse extends ResponseHandler{

	public LocationResponse(String data) {
		super(data);
	}

	@Override
	public Object handleResponse() {
		return _data;
	}

}
