package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.sfumobile.wifilocator.FriendsActivity.AutoPoll;
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
	private AutoPoll eventPoller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.events);
		wifiHandler = new WifiHandler(this);
		handler = new Handler();
		eventsList = (ListView)this.findViewById(R.id.eventList);
		eventPoller = new AutoPoll(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(wifiHandler.wifiConnected()){
			eventPoller = new AutoPoll(this);
			eventPoller.execute();
		}
	}

	@Override
	protected void onStop(){
		super.onStop();
		eventPoller.cancel(true);
	}
	
	@Override
	public void handleStringValue(int type, String data) {
		EventsResponse response = new EventsResponse(data);
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
	
	class AutoPoll extends AsyncTask<String, JSONObject, Void> {	

		RequestDelegateActivity _rd;
		
		public AutoPoll(RequestDelegateActivity rd){
	        _rd = rd;
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			while(!isCancelled()) {
		        try{
		        	System.out.println("EventPoller");
		        	
		    		_req = new EventsRequest(UserObject.getInstance().get_userID(), wifiHandler.getBSSID());
		    		_package = new RequestPackage(_rd, _req, handler);
		    		SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
		    		launcher.sendRequest(_rd, _package);
		    		
		        	Thread.sleep(1000*30);
		        } catch (InterruptedException e) {
		        	Thread.currentThread().destroy();
					e.printStackTrace();
				}
			}
			return null;
		}
	}

}