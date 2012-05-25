package com.sfumobile.wifilocator.utils;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sfumobile.wifilocator.entities.WifiLocatorFriend;

public class JSONWifiLocatorParser {
	public static String getZoneName( String res ){
		JSONObject obj = null;
		String temp = null;
		try {
			obj = new JSONObject( res );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			temp =  obj.getString("zone_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("JSON VALU = "+temp);
		return temp;
	}
	
	public static Vector getFriends( String res ) {
		Vector friends = new Vector();
		JSONObject obj = null;
		JSONArray temp = null;
		try {
			obj = new JSONObject( res );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			temp =  obj.getJSONArray("Alex");
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

