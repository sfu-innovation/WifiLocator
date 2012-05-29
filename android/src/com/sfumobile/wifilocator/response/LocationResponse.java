package com.sfumobile.wifilocator.response;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationResponse extends ResponseHandler{

	public LocationResponse(String data) {
		super(data);
	}

	@Override
	public Object handleResponse() {
		return _data;
	}

}
