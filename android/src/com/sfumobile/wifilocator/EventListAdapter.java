package com.sfumobile.wifilocator;

import java.util.ArrayList;

import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestPackage;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class EventListAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private static ArrayList<Event> events;
	
	public EventListAdapter(RequestDelegateActivity rd, ArrayList<Event> data){
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(rd);
		events = data;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		

		if (convertView == null) {
		convertView = mInflater.inflate(R.layout.event_line, null);
		 
		holder = new ViewHolder();
		holder.eventText = (TextView) convertView.findViewById(R.id.eventNameLabel);

		
		convertView.setTag(holder);
		} else {
		holder = (ViewHolder) convertView.getTag();
		}
		 

		holder.eventText.setText(events.get(position).get_name());

		 
		return convertView;
	}
		 
	public int getCount() {
		return events.size();
	}

	public Object getItem(int index) {
		return events.get(index);
	}

	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		TextView eventText;
	}
}
