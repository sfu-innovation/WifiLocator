package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class FriendshipResponse extends Response {

	public FriendshipResponse( String data ){
		super( data );
	}
	public Object doAction() {
		String statusCode = null;
		try {
			statusCode =  _data.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statusCode;
	}

}
