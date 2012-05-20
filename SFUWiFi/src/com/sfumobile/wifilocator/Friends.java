package com.sfumobile.wifilocator;

import android.app.ExpandableListActivity;
//import android.content.Intent; //find friends from server?
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.BaseExpandableListAdapter;
import android.widget.AbsListView;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class Friends extends ExpandableListActivity {
	private FriendAdapter mAdapter;
	private String[] friends;
	private String[][] status;
	//private ExpandableListView fname;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_screen);
		
		friends = getResources().getStringArray(R.array.friends);
		String[] status1 = getResources().getStringArray(R.array.status);
		status = new String[status1.length][1];
		
		for (int i=0; i< status1.length; i++){
			status[i][0] = status1[i];
		}
		
		mAdapter = new FriendAdapter();
		setListAdapter(mAdapter);
		//fname = (ExpandableListView) findViewById(R.id.friend);
		
		//fname.setAdapter(mAdapter);
		//fname.setGroupIndicator(null);
		
		/*fname.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			public void onGroupExpand(int groupPosition){
				int len = mAdapter.getGroupCount();
				for (int i=0; i<len; i++){
					if(i!=groupPosition) {
						fname.collapseGroup(i);
					}
				}
			}
		});
		*/
	/*	fname.setOnGroupClickListener(new onGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return false;
			}
		});
		*/
//		Intent i = getIntent();
//		String name = "test";
//		fname.set
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

			View inflatedView = View.inflate(getApplicationContext(), R.layout.friend_sub, null);
			inflatedView.setPadding(50, 0, 0, 0);
			TextView txtView = (TextView)inflatedView.findViewById(R.id.textView1);
				
			txtView.setTextSize(15);
			txtView.setText(getChild(groupPosition, childPosition).toString());
			return inflatedView;
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

}
