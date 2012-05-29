package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class FriendRequestsActivity extends RequestDelegateActivity{
	
	FriendRequestAdapter adapter;
	RequestHandler requestHandler;
	private static ArrayList<JSONObject> data;
	private ListView friendList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		friendList = (ListView)this.findViewById(R.id.friendList);
		requestHandler = new RequestHandler(this);
		data = requestHandler.getFriendRequests();
		adapter = new FriendRequestAdapter(this,data);
		friendList.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void handleStringValue(int type, String val) {
		
	}

	@Override
	public void handleIntValue(int type, int val) {
		
	}

	@Override
	public void handleError(int type, int errorCode, Object errorString) {
		
	}

	@Override
	public void handleImageDataValue(int type, byte[] data) {
		
	}

	
}
