package com.sfumobile.wifilocator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.sfumobile.wifilocator.request.RequestConstants;
import com.sfumobile.wifilocator.request.RequestHandler;

import android.util.Log;

public class UserProfile {
	private JSONObject request;
	private JSONArray profile;
	private String[] friends, loc;


	public UserProfile(){
		
		JSONObject request = new JSONObject();
		JSONObject response = null;
		
		try{
			request.put("user_id", WifiLocatorActivity.USER_ID);
			response = RequestHandler.postRequest(request,RequestConstants.GET_FRIEND_REQUESTS_URL);

		} catch (JSONException jse) {
			//some exception stuff
		}
		//request = HttpGET.connect(RequestConstants.GET_FRIEND_REQUESTS_URL);
		Log.d("userProfile()", Integer.toString(request.length()));
		
		if (response!=null)
			extract_info(response);
		else{
			friends = null;
			loc = null;
		}
	}

	public void extract_info(JSONObject response) {
		try{
			Log.d("profile extract", "in extract");
			if(response.getInt("status")==0){
				profile = response.getJSONArray("friend_list");

				int count = profile.length();
				friends = new String[count];
				loc = new String[count];
				for (int i=0; i<count; i++){
					JSONObject friend = profile.getJSONObject(i);
					friends[i] = friend.getString("friend_name");
					loc[i] = friend.getString("friend_location")+", "+friend.getString("last_update");
				}
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
