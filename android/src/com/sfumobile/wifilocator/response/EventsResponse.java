package com.sfumobile.wifilocator.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sfumobile.wifilocator.Event;

public class EventsResponse extends ResponseHandler{
	private int events_size;
	public EventsResponse(String data) {
		super(data);
	}

	@Override
	public ArrayList<Event> handleResponse() {
		ArrayList<Event> events = new ArrayList<Event>();
		JSONArray event_response = null;
		try {
			System.out.println("GETTING EVENTS!");
			event_response = _data.getJSONArray("events");
			events_size = event_response.length();
		} catch (JSONException e) {
			e.printStackTrace();
			events_size = 0;
		}

		for(int i = 0; i < events_size; i++){
			try {
				JSONObject jsonEvent = event_response.getJSONObject(i);
				Event event = new Event();
				event.set_name(jsonEvent.getString("name"));
				event.set_location(jsonEvent.getString("location(superzone)"));
				event.set_organizer(jsonEvent.getString("organizer"));
				event.set_start_time(jsonEvent.getString("start_time"));
				event.set_end_time(jsonEvent.getString("end_time"));
				System.out.println("GETTING EVENTS " + event.get_name());
				events.add(event);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return events;
	}

}
