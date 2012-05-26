package com.sfumobile.wifilocator;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;
import android.widget.TextView;

public class Friends extends ExpandableListActivity implements OnClickListener{
	
	private FriendAdapter mAdapter;
	private String[] friends, loc;
	private String[][] status;
	private Button addFriendButton, addButton, cancelButton;
	private EditText friendIDText;
	private Dialog addFriendDialog;
	private RequestHandler requestHandler;
	
	private final int ADD_FRIEND_DIALOG_ID = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_screen);
		
		loadList populate = new loadList();
		populate.execute();

		addFriendButton = (Button)findViewById(R.id.addFriendButton);
		addFriendButton.setOnClickListener(this);
		
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
			
			final Intent i = new Intent(getApplicationContext(), GetMap.class);
			i.putExtra("zone", getChild(groupPosition, childPosition).toString());
			TextView txtView = getGenericView();
			
			txtView.setTextSize(15);
			txtView.setText(getChild(groupPosition, childPosition).toString());
			txtView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub				
					startActivity(i);
				}
			});
			
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
		switch(v.getId()){
		case R.id.addFriendButton:
			showDialog(ADD_FRIEND_DIALOG_ID);
			break;
			
		case R.id.addButton:
			int result = requestHandler.sendFriendRequest(Integer.parseInt(friendIDText.getText().toString()));
			String message = "";
			
			switch(result){
			case -1:
				message = "Error trying to add friend";
				break;
			case 0:
				message = "Friend request sent";
				break;
			case 1:
				message = "User not found.  How are you logged in even?";
				break;
			case 2:
				message = "Friend id not found.";
				break;
			case 3:
				message = "There is already a friend request pending for that user.";
				break;
			}
			Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			break;
		
		case R.id.cancelButton:
			addFriendDialog.cancel();
			break;
			
		case R.id.friendIDText:
			friendIDText.setText("");
			friendIDText.setTextColor(Color.BLACK);
			break;
		}
		
	}
	

	class loadList extends AsyncTask<Void, Void, Void> {	
		private ProgressDialog dialog = new ProgressDialog(Friends.this);
		
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
			setListAdapter(mAdapter);
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
			friendIDText = (EditText)addFriendDialog.findViewById(R.id.friendIDText);
			
			addButton.setOnClickListener(this);
			cancelButton.setOnClickListener(this);
			friendIDText.setOnClickListener(this);
			friendIDText.setTextColor(Color.GRAY);
			
			return addFriendDialog;
		
		default:
			return null;
		}
	}

}
