package com.sfumobile.wifilocator.response;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class ResponseHandler  {

	JSONObject _data;
	public ResponseHandler(String data){
		try {
			_data = new JSONObject( data );
		} catch (JSONException e) {
			Log.e("ResponseHandler",e.getLocalizedMessage());
		}
	}
	public abstract Object handleResponse();
}
