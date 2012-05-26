package com.sfumobile.wifilocator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.util.Log;

public class UserProfile {
	private JSONObject request;
	private JSONArray profile;
	private static String url = "http://wifi-location.appspot.com/getfriends/" + WifiLocatorActivity.USER;
	private String[] friends, loc;


	public UserProfile(){
		request = HttpGET.connect(url);
		Log.d("userProfile()", request.toString());
		extract_info();
	}

	public void extract_info() {
		try{
			Log.d("profile extract", "in extract");
			profile = request.getJSONArray(WifiLocatorActivity.USER);

			int count = profile.length();
			friends = new String[count];
			loc = new String[count];
			for (int i=0; i<count; i++){
				JSONObject friend = profile.getJSONObject(i);
				friends[i] = friend.getString("friend_name");
				loc[i] = friend.getString("friend_location")+", "+friend.getString("last_update");
			}
		} catch (JSONException jse) {
			Log.e("JSON Exception", jse.toString());
		}
	}

	public String[] get_friends(){
		return friends;
	}

	public String[] get_loc(){
		return loc;
	}

}