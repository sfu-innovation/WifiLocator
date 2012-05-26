package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

public class FriendshipResponse extends ResponseHandler {

	public FriendshipResponse( String data ){
		super( data );
	}
	public Object handleResponse() {
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
