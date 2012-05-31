package com.sfumobile.wifilocator.response;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sfumobile.wifilocator.entities.WifiLocatorEvent;

public class EventsResponse extends ResponseHandler {
	
	public EventsResponse( String data ){
		super( data );
	}
	
	public Object handleResponse(){
		int statusCode = -1;
	    JSONArray arr = null;
		Vector events = new Vector();
		
		try {
			statusCode = _data.getInt("status");
			if ( statusCode != 0 ){
				throw new JSONException("The status for the request/events was ["+statusCode+"]");
			}
			arr = _data.getJSONArray("events");
			int length = arr.length();
			JSONObject tempEvent = null;
			for( int i = 0; i < length; i++){
				tempEvent = arr.getJSONObject(i);
				events.addElement( new WifiLocatorEvent(
						tempEvent.getString("location(superzone)"),
						tempEvent.getString("organizer"),
						tempEvent.getString("start_time"),
						tempEvent.getString("name"),
						tempEvent.getString("end_time")
				));
			}
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
		return events;
	}

}
