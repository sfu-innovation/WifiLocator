package com.sfumobile.wifilocator.response;

import org.json.JSONException;

public class FriendshipConfirmResponse extends ResponseHandler{

	public FriendshipConfirmResponse(String data) {
		super(data);
	}

	@Override
	public Object handleResponse() {
		int result = -1;
		String message = "";
		try {
			result = _data.getInt("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		switch(result){
		case -1:
			message = "Error sending friend request";
			break;
		case 0:
			message = "Friend added!";
			break;
		case 1:
			message = "Request not found";
			break;
		}
		return message;
	}

}
