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
			JSONObject tempFriendship = null;
			for(int  i = 0; i < temp.length(); i++){
					tempFriendship = temp.getJSONObject(i);
					friends.addElement(new WifiLocatorFriend(
						tempFriendship.getString("first_name"),
						tempFriendship.getString("last_name"),
						tempFriendship.getString("friend_location"),
						tempFriendship.getString("last_update"),
						tempFriendship.getString("map_name")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}
}
