package com.sfumobile.wifilocator.response;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;

public class FriendshipRetrievalResponse extends Response {
	public FriendshipRetrievalResponse(String data){
		super(data);
	}

	public Object doAction() {
		int status = -1;
		Vector friendRequests = new Vector();
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
				JSONArray arr = (JSONArray) _data.get("Requests");
				int length = arr.length();
				for(int i = 0; i < length; i++){
					temp = arr.getJSONObject(i);
					friendRequests.addElement( new WifiLocatorFriendship(
							temp.getString("friend_name"),
							temp.getString("request_id")
					));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			return null;
		}
		return friendRequests;
	}
}
