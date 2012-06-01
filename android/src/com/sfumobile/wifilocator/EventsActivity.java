package com.sfumobile.wifilocator;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.sfumobile.wifilocator.request.EventsRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.WifiHandler;
import com.sfumobile.wifilocator.response.EventsResponse;

public class EventsActivity extends RequestDelegateActivity{

	EventsRequest _req;
	RequestPackage _package;
	WifiHandler wifiHandler;
	Handler handler;
	private static ArrayList<Event> events;
	private ListView eventsList;
	private EventListAdapter eventAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.events);
		wifiHandler = new WifiHandler(this);
		handler = new Handler();
		eventsList = (ListView)this.findViewById(R.id.eventList);
	}

	@Override
	protected void onStart() {
		super.onStart();
		_req = new EventsRequest(UserObject.getInstance().get_userID(), wifiHandler.getBSSID());
		_package = new RequestPackage(this, _req, handler);
		SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
		launcher.sendRequest(this, _package);

	}

	@Override
	public void handleStringValue(int type, String val) {
		EventsResponse response = new EventsResponse(val);
		events = response.handleResponse();
	    eventAdapter = new EventListAdapter(this, events);
	    eventsList.setAdapter(eventAdapter);
	}

	@Override
	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleImageDataValue(int type, String data) {
		// TODO Auto-generated method stub
		
	}

}
