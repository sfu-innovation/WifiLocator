package com.sfumobile.wifilocator.response;


import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;

public class FriendshipsPendingResponse extends ResponseHandler {
	public FriendshipsPendingResponse(String data){
		super(data);
	}

	public Object handleResponse() {
		int status = -1;
		Vector friendshipRequests = new Vector();
		try {
			status = Integer.parseInt(_data.getString("status"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( status == 0 ) {
			JSONObject temp = null;
			try {
				JSONArray arr = (JSONArray) _data.get("requests");
				int length = arr.length();
				/*
				WifiLocatorFriendship tempFriendship;
				for(int i = 0; i < length; i++){
					temp = arr.getJSONObject(i);
					
					friendshipRequests.addElement(new WifiLocatorFriendship(
							temp.getString("friend_name"),
							temp.getInt("request_id")
					));
				}*/
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			return null;
		}
		return friendshipRequests;
	}
}
