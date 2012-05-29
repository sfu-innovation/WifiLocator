package com.sfumobile.wifilocator.response;

import org.json.JSONException;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class FriendshipRequestResponse extends ResponseHandler{

	public FriendshipRequestResponse(String data) {
		super(data);
	}

	@Override
	public Object handleResponse() {
		int result = -1;
		
		try {
			result = _data.getInt("status");
		} catch (JSONException e) {
			Log.e("FriendRequestResponse", e.getLocalizedMessage());
		}
		
		String message = "";
		
		switch(result){
		case -1:
			message = "Error trying to add friend";
			break;
		case 0:
			message = "Friend request sent";
			break;
		case 1:
			message = "User not found.  How are you logged in even?";
			break;
		case 2:
			message = "Friend id not found.";
			break;
		case 3:
			message = "There is already a friend request pending for that user.";
			break;
		case 4:
			message = "You can't add yourself as a friend.";
			break;
		case 5:
			message = "You are already friends with that user.";
			break;
		}	
	
		return message;
	}

}
