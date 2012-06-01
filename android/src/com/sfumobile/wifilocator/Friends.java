package com.sfumobile.wifilocator;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

import com.sfumobile.wifilocator.request.FriendListRequest;
import com.sfumobile.wifilocator.request.FriendshipRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipRequestResponse;
import com.sfumobile.wifilocator.response.FriendshipsResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class Friends extends RequestDelegateActivity implements OnClickListener{
	
	private FriendAdapter mAdapter;
	private Button addFriendButton, friendRequestsButton, addButton, cancelButton, scanButton, qrButton;
	private EditText friendIDText;
	private Dialog addFriendDialog;
	private ImageView qrImage;
	private ExpandableListView friendList;
	private final int ADD_FRIEND_DIALOG_ID = 0;
	private Handler handler;
	private static ArrayList<JSONObject> data;
	private FriendListObject fl;
	private boolean qrVisible;
	
	private FriendListRequest  _req;
	private RequestPackage     _package;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_screen);

		friendList = (ExpandableListView)this.findViewById(R.id.friendList);
		
		addFriendButton      = (Button)findViewById(R.id.addFriendButton);
		friendRequestsButton = (Button)findViewById(R.id.friendRequestsButton);
		qrButton             = (Button)findViewById(R.id.qrButton);
		qrImage              = (ImageView)findViewById(R.id.qrImage);
		
		addFriendButton.setOnClickListener(this);
		friendRequestsButton.setOnClickListener(this);
		qrButton.setOnClickListener(this);
		qrVisible = false;
		
		handler = new Handler();
		_req = new FriendListRequest(UserObject.getInstance().get_userID());
		_package = new RequestPackage(this, _req, handler);
		
		if (FriendListObject.getInstance().get_data()!=null){
			 mAdapter = new FriendAdapter(this, FriendListObject.getInstance().get_data());
			 friendList.setAdapter(mAdapter);	
		}
		
	}
	
	public void onStart(){
		super.onStart();
		SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
		launcher.sendRequest(this, _package );
	}

	public void onRestart(){
		super.onRestart();
	}

	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.addFriendButton:
			showDialog(ADD_FRIEND_DIALOG_ID);
			break;
			
		case R.id.friendRequestsButton:
			intent = new Intent(this,FriendRequestsActivity.class);
			startActivity(intent);
			break;
			
		case R.id.qrButton:
			if(!qrVisible){
				qrButton.setText(R.string.qr_code_hide);
				Bitmap bitmap = QRGenerator.generateQR("sfumobile." + UserObject.getInstance().get_userID());
				qrImage.setImageBitmap(bitmap);
				qrVisible = true;
			}
			else{
				qrButton.setText(R.string.qr_code);
				qrImage.setImageResource(android.R.color.transparent);
				qrVisible = false;
			}
			break;
			
		case R.id.addButton:
			addFriend();
			break;
		
		case R.id.cancelButton:
			addFriendDialog.cancel();
			break;
			
		case R.id.scanButton:
    		intent = new Intent("com.google.zxing.client.android.SCAN");
    		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    		startActivityForResult(intent, 0);
    		break;
		case R.id.friendIDText:
			friendIDText.setText("");
			friendIDText.setTextColor(Color.BLACK);
			break;
		}
	}

	private void addFriend() {
		try{

			System.out.println("[SFUMOBILE] - USER_ID = "+UserObject.getInstance().get_userID());
			FriendshipRequest  _req = new FriendshipRequest(UserObject.getInstance().get_userID(), Integer.parseInt(friendIDText.getText().toString()));

			RequestPackage _package = new RequestPackage(this, _req, handler);
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest(this, _package);
		}
		catch(NumberFormatException e){
			Log.e("AddFriend", "Can't convert string to int");
			Toast t = Toast.makeText(this, "Not a valid friend id", Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}

	/*
	 * Handles response from qr scanning activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			String id = extras.get("SCAN_RESULT").toString().substring(10);
			if(id != null){
				friendIDText.setText(id);
				addFriend();
			}
		}
	}


	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
		case ADD_FRIEND_DIALOG_ID:
			addFriendDialog = new Dialog(this);
			addFriendDialog.setContentView(R.layout.add_friend);
			
			addButton    = (Button)addFriendDialog.findViewById(R.id.addButton);
			cancelButton = (Button)addFriendDialog.findViewById(R.id.cancelButton);
			scanButton   = (Button)addFriendDialog.findViewById(R.id.scanButton);
			friendIDText = (EditText)addFriendDialog.findViewById(R.id.friendIDText);
			
			addButton.setOnClickListener(this);
			cancelButton.setOnClickListener(this);
			scanButton.setOnClickListener(this);
			friendIDText.setOnClickListener(this);
			friendIDText.setTextColor(Color.GRAY);
			
			return addFriendDialog;
		
		default:
			return null;
		}
	}

	@Override
	public void handleStringValue(int type, String val) {
		
		if(type == RequestTypes.GET_FRIENDS){
			FriendshipsResponse _response = new FriendshipsResponse( val, type);
		    data = _response.handleResponse();		
		    fl.getInstance().set_data(data);
		    Log.d("Friends", data.toString());
		    mAdapter = new FriendAdapter(this, data);
		    friendList.setAdapter(mAdapter);
		}
		else if(type == RequestTypes.FRIENDSHIP_REQUEST){
			FriendshipRequestResponse _response = new FriendshipRequestResponse(val);
		    String message = (String) _response.handleResponse();
			Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			addFriendDialog.cancel();
			
		}
	
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
