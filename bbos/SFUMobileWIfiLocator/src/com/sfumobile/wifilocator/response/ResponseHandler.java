package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;
import org.json.me.JSONObject;

public abstract class ResponseHandler  {

	JSONObject _data;
	public ResponseHandler(String data){
		try {
			_data = new JSONObject( data );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("[SFUMOBILE] unable to create response : "+data);
			e.printStackTrace();
		}
	}
	public abstract Object handleResponse();
}
