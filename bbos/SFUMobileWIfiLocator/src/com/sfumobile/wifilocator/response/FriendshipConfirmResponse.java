package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class FriendshipConfirmResponse extends Response {
	public FriendshipConfirmResponse(String data ){
		super(data);
	}
	public Object doAction() {
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
