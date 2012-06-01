package com.sfumobile.wifilocator.response;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sfumobile.wifilocator.types.RequestTypes;

//import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;

public class FriendshipsPendingResponse extends ResponseHandler {
	private int _type;
	public FriendshipsPendingResponse(String data, int type){
		super(data);
		_type = type;
	}

	public ArrayList<JSONObject> handleResponse() {
		int status = -1;
		try {
			status = Integer.parseInt(_data.getString("status"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if ( status == 0 ) {
			ArrayList<JSONObject> data = parseFriendRequests(_data);
			return data;
		}else {
			return new ArrayList<JSONObject>();
		}
	}
	
	public ArrayList<JSONObject> parseFriendRequests(JSONObject response){
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		try {
			if(response.getInt("status")!=2){
				JSONArray requests = response.getJSONArray("requests");
				for(int i=0; i < requests.length(); i++){
					result.add(requests.getJSONObject(i));
				}
				return result;
			}
		} catch (JSONException e) {
			Log.d("ParseFriendRequests",e.getLocalizedMessage());
		}
		return result;
	}
	
}
