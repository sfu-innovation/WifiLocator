package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipConfirmResponse;
import com.sfumobile.wifilocator.response.FriendshipsResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FriendRequestsActivity extends RequestDelegateActivity implements OnClickListener{
	
	FriendRequestAdapter adapter;
	RequestHandler requestHandler;
	private static ArrayList<JSONObject> data;
	private ListView friendList;
	private Handler handler;
	private FriendshipsPendingRequest  _req;
	private RequestPackage             _package;
	private Button getPending;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_requests);
		friendList = (ListView)this.findViewById(R.id.friendList);
		requestHandler = new RequestHandler(this);
		
		getPending = (Button)findViewById(R.id.pendingButton);
		handler = new Handler();
		
		_req = new FriendshipsPendingRequest( User.getInstance().get_userID() );
		_package = new RequestPackage(this, _req, handler);
	}

	@Override
	protected void onStart() {
		super.onStart();
	//	SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
	//	launcher.sendRequest(this, _package );
	}

	
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.pendingButton:
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest(this, _package );
			break;
		}
	}
	
	
	@Override
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.GET_FRIENDSHIP_REQUESTS){
			FriendshipsResponse _response = new FriendshipsResponse( val, type);
			
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
