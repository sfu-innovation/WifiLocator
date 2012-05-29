package com.sfumobile.wifilocator;

import com.sfumobile.wifilocator.request.FriendshipRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipRequestResponse;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

public class Friends extends RequestDelegateActivity implements OnClickListener{
	
	private FriendAdapter mAdapter;
	private String[] friends, loc;
	private String[][] status;
	private Button addFriendButton, friendRequestsButton, addButton, cancelButton, scanButton, qrButton;
	private EditText friendIDText;
	private Dialog addFriendDialog;
	private RequestHandler requestHandler;
	private ImageView qrImage;
	
	private final int ADD_FRIEND_DIALOG_ID = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_screen);

		requestHandler = new RequestHandler(this);
		
		addFriendButton      = (Button)findViewById(R.id.addFriendButton);
		friendRequestsButton = (Button)findViewById(R.id.friendRequestsButton);
		qrButton             = (Button)findViewById(R.id.qrButton);
		qrImage              = (ImageView)findViewById(R.id.qrImage);
		
		addFriendButton.setOnClickListener(this);
		friendRequestsButton.setOnClickListener(this);
		qrButton.setOnClickListener(this);
		
	}
	
	public void onStart(){
		super.onStart();
		loadList populate = new loadList(this);
		populate.execute();
	}
	
	public class FriendAdapter extends BaseExpandableListAdapter {
		public Object getChild(int groupPosition, int childPosition) {
			return status[groupPosition][childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public TextView getGenericView() {
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			
			TextView txtView = new TextView(Friends.this);
			txtView.setLayoutParams(params);
			txtView.setPadding(60, 5, 0, 5);
			return txtView;
		}
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			//View inflatedView = View.inflate(getApplicationContext(), R.layout.friend_sub, null);
			//inflatedView.setPadding(50, 0, 0, 0);
			//TextView txtView = (TextView)inflatedView.findViewById(R.id.textView1);
			
		//	final Intent i = new Intent(getApplicationContext(), GetMap.class);
		//	i.putExtra("zone", getChild(groupPosition, childPosition).toString());
			TextView txtView = getGenericView();
			
			txtView.setTextSize(15);
			txtView.setText(getChild(groupPosition, childPosition).toString());
		/*	txtView.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub				
					startActivity(i);
				}
			});
			*/
			return txtView;
		}

		public int getChildrenCount(int groupPosition) {
			return status[groupPosition].length;
		}

		public Object getGroup(int groupPosition) {
			return friends[groupPosition];
		}

		public int getGroupCount() {
			return friends.length;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
			TextView txtView = getGenericView();
			txtView.setText(getGroup(groupPosition).toString());
			return txtView;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
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
			Bitmap bitmap = QRGenerator.generateQR("sfumobile." + WifiLocatorActivity.USER_ID);
			qrImage.setImageBitmap(bitmap);
			break;
			
		case R.id.addButton:
			int result = -1;
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
			FriendshipRequest  _req = new FriendshipRequest(WifiLocatorActivity.USER_ID, Integer.parseInt(friendIDText.getText().toString()));
			RequestPackage _package = new RequestPackage(this, _req);
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

	class loadList extends AsyncTask<Void, Void, Void> {	
		private ProgressDialog dialog = new ProgressDialog(Friends.this);
		
		ExpandableListView list;
		
		public loadList(Activity ctx){
			list = (ExpandableListView)ctx.findViewById(R.id.friendList);
		}
		
		@Override
		protected void onPreExecute(){
			dialog.setMessage("Loading...");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			

			UserProfile id = new UserProfile();

			friends = id.get_friends();	
			loc = id.get_loc();

			
			if (friends != null){
				status = new String[loc.length][1];		
				for (int i=0; i< loc.length; i++)
					status[i][0] = loc[i];
			}
			
			mAdapter = new FriendAdapter();
			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			dialog.dismiss();
			list.setAdapter(mAdapter);
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
		FriendshipRequestResponse _response = new FriendshipRequestResponse(val);
		String message = (String)_response.handleResponse();
		
		Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
		
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
