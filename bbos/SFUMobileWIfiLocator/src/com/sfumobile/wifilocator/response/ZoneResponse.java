package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class ZoneResponse extends Response {

	public ZoneResponse(String data){
		super(data);
	}
	public Object doAction() {
		String temp = null;
		try {
			temp =  _data.getString("zone_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return temp;
	}
}
