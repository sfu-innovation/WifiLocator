package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.RequestHandler;

import android.app.ListActivity;
import android.os.Bundle;

public class FriendRequestsActivity extends ListActivity{
	
	FriendRequestAdapter adapter;
	RequestHandler requestHandler;
	private static ArrayList<JSONObject> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		requestHandler = new RequestHandler(this);
		data = requestHandler.getFriendRequests();
		adapter = new FriendRequestAdapter(this,data);
		setListAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	
}
