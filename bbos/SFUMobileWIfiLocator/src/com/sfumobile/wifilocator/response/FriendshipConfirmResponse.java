package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class FriendshipConfirmResponse extends ResponseHandler {
	public FriendshipConfirmResponse(String data ){
		super(data);
	}
	public Object handleResponse() {
		String status = null;
		
		try {
			status = _data.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return status;
	}

}
