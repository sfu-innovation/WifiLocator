package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.EventsRequest;
import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipConfirmResponse;
import com.sfumobile.wifilocator.response.FriendshipsPendingResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

public class FriendRequestsActivity extends RequestDelegateActivity{
	
	FriendRequestAdapter adapter;
	RequestHandler requestHandler;
	private static ArrayList<JSONObject> data;
	private ListView friendList;
	private Handler handler;
	private FriendshipsPendingRequest  _req;
	private RequestPackage _package;
	private AutoPoll requestPoller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		friendList = (ListView)this.findViewById(R.id.friendList);
		requestHandler = new RequestHandler(this);
		
		handler = new Handler();
		
		_req = new FriendshipsPendingRequest( UserObject.getInstance().get_userID() );
		_package = new RequestPackage(this, _req, handler);
		
		

	}

	@Override
	protected void onStart() {
		super.onStart();
		requestPoller = new AutoPoll(this);
		requestPoller.execute();
	}

	@Override
	protected void onStop(){
		super.onStop();
		requestPoller.cancel(true);
	}
	
	@Override
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.GET_FRIENDSHIP_REQUESTS){
			FriendshipsPendingResponse _response = new FriendshipsPendingResponse( val, type);
		    data = _response.handleResponse();		
		    PendingListObject.getInstance().set_data(data);
			adapter = new FriendRequestAdapter(this, data);
			friendList.setAdapter(adapter);	
		}
		else if(type == RequestTypes.CONFIRM_FRIENDSHIP_REQUEST){
			FriendshipConfirmResponse _response = new FriendshipConfirmResponse( val );
		    String message = (String) _response.handleResponse();
			Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}

	@Override
	public void handleIntValue(int type, int val) {
		
	}

	@Override
	public void handleError(int type, int errorCode, Object errorString) {
		
	}

	@Override
	public void handleImageDataValue(int type, String data) {
		
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
		        	System.out.println("FriendRequestPoller");
		        	
		    		_req = new FriendshipsPendingRequest(UserObject.getInstance().get_userID());
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
