package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipConfirmResponse;
import com.sfumobile.wifilocator.response.FriendshipsPendingResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

public class FriendRequestsActivity extends RequestDelegateActivity{
	
	FriendRequestAdapter adapter;
	RequestHandler requestHandler;
	private static ArrayList<JSONObject> data;
	private ListView friendList;
	
	private FriendshipsPendingRequest  _req;
	private RequestPackage             _package;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		friendList = (ListView)this.findViewById(R.id.friendList);
		requestHandler = new RequestHandler(this);
		
		_req = new FriendshipsPendingRequest( WifiLocatorActivity.USER_ID );
		_package = new RequestPackage(this, _req);
	}

	@Override
	protected void onStart() {
		super.onStart();
		SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
		launcher.sendRequest(this, _package );
	}

	@Override
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.GET_FRIENDSHIP_REQUESTS){
			FriendshipsPendingResponse _response = new FriendshipsPendingResponse( val );
			
		    data = _response.handleResponse();			
			adapter = new FriendRequestAdapter(this,data);
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

	
}
