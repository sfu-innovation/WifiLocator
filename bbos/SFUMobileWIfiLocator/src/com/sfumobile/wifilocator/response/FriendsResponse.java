package com.sfumobile.wifilocator.response;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sfumobile.wifilocator.entities.WifiLocatorFriend;

public class FriendsResponse extends ResponseHandler {

	public FriendsResponse(String data){
		super( data );
	}
	public Object handleResponse() {
		Vector friends = new Vector();
		JSONArray temp = null;
		try {
			
			temp =  _data.getJSONArray("friend_list");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject temp2 = null;
		for(int  i = 0; i < temp.length(); i++){
			try {
				temp2 = temp.getJSONObject(i);
				friends.addElement(new WifiLocatorFriend(
						  temp2.getString("friend_name"),
						  temp2.getString("friend_location"),
						  temp2.getString("last_update")));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return friends;
	}
}
