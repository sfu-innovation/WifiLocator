package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.sfumobile.wifilocator.request.RequestDelegateActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class FriendsAdapter extends BaseExpandableListAdapter {
	private Context _rd;
	private static ArrayList<JSONObject> _data;
	private String[] friends, loc, map;
	private String[][] status;

	
	public FriendsAdapter(RequestDelegateActivity rd, ArrayList<JSONObject> data){
		_data = data;
		_rd = rd;
		int size = _data.size();
		
		friends = new String[size];
		loc = new String[size];
		map = new String[size];
		try{
			for (int i=0; i<size; i++){
				friends[i] = _data.get(i).getString("first_name")  + " " + _data.get(i).getString("last_name");
				loc[i] = _data.get(i).getString("friend_location")+", "+data.get(i).getString("last_update");
				map[i] = _data.get(i).getString("map_name");
			}
		} catch (JSONException jse) {
			Log.e("JSON Exception", jse.toString());
		}
		
		if (friends != null){
			status = new String[size][1];		
			for (int i=0; i< size; i++)
				status[i][0] = loc[i];
		}
		
	}
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
		
		TextView txtView = new TextView(_rd);
		txtView.setLayoutParams(params);
		txtView.setPadding(60, 5, 0, 5);
		return txtView;
	}
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		final Intent i = new Intent(_rd, MapActivity.class);
		i.putExtra("map_name", map[groupPosition]);
		TextView txtView = getGenericView();
		
		txtView.setTextSize(15);
		txtView.setText(getChild(groupPosition, childPosition).toString());
		txtView.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				_rd.startActivity(i);
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