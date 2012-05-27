package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class ZoneResponse extends ResponseHandler {

	public ZoneResponse(String data){
		super(data);
	}
	public Object handleResponse() {
		String temp = null;
		try {
			temp =  _data.getString("zone_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return temp;
	}
}
