package com.sfumobile.wifilocator;

import android.app.ListActivity;
import android.os.Bundle;

public class FriendRequestsActivity extends ListActivity{
	
	RequestAdapter adapter;
	private static String[] data = new String[] { "0", "1", "2", "3", "4" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		adapter = new RequestAdapter(this,data);
		setListAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	
}
