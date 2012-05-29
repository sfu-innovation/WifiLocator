package com.sfumobile.wifilocator.response;


import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sfumobile.wifilocator.WifiLocatorActivity;
import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;

//import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;

public class FriendshipsPendingResponse extends ResponseHandler {
	public FriendshipsPendingResponse(String data){
		super(data);
	}

	public ArrayList<JSONObject> handleResponse() {
		int status = -1;
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
			ArrayList<JSONObject> data = parseFriendRequests(_data);
			return data;
		}else {
			return new ArrayList<JSONObject>();
		}

	}
	
	public ArrayList<JSONObject> parseFriendRequests(JSONObject response){
		Log.d("FriendRequests",response.toString());
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
