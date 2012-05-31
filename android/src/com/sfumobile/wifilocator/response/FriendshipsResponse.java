package com.sfumobile.wifilocator.response;


import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sfumobile.wifilocator.WifiLocatorActivity;
import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.types.RequestTypes;

//import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;

public class FriendshipsResponse extends ResponseHandler {
	private int _type;
	public FriendshipsResponse(String data, int type){
		super(data);
		_type = type;
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
			e.printStackTrace();			//	zoneText.setText(zone);
		}
		if ( status == 0 ) {
			ArrayList<JSONObject> data = parseFriendRequests(_data, _type);
			return data;
		}else {
			return new ArrayList<JSONObject>();
		}
	}
	
	public ArrayList<JSONObject> parseFriendRequests(JSONObject response, int type){
		Log.d("FriendRequests",response.toString());
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		String req_type = "";
		switch (type){
		case RequestTypes.GET_FRIENDS:
			req_type = "friend_list";
			break;
		case RequestTypes.GET_FRIENDSHIP_REQUESTS:
			req_type = "requests";
			break;
		}
		Log.d("Type", req_type);
		try {
			if(response.getInt("status")!=2){
				JSONArray requests = response.getJSONArray(req_type);
				Log.d("length", Integer.toString(requests.length()));
				for(int i=0; i < requests.length(); i++){
					result.add(requests.getJSONObject(i));
				}
				Log.d("result", result.toString());
				return result;
			}
		} catch (JSONException e) {
			Log.d("ParseFriendRequests",e.getLocalizedMessage());
		}
		return result;
	}
	
}
