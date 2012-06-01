package com.sfumobile.wifilocator;

import java.util.ArrayList;

import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestPackage;

import android.content.Intent;
import android.os.Handler;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class EventListAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private static ArrayList<Event> events;
	private RequestDelegateActivity _rd;
	
	public EventListAdapter(RequestDelegateActivity rd, ArrayList<Event> data){
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(rd);
		events = data;
		_rd = rd;
		
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


		if(events != null){
			convertView.setOnClickListener(new OnClickListener() {
				private int pos = position;
				 
				public void onClick(View v) {
					Intent myIntent;
					myIntent = new Intent(_rd, EventDetailsActivity.class);
					myIntent.putExtra("com.sfumobile.wifilocator.Event", events.get(pos));
					_rd.startActivity(myIntent);
					
				}
				});
	
			holder.eventText.setText(events.get(position).get_name());
		}
		else{
			holder.eventText.setText("No Events");
		}
		
		return convertView;
	}
		 
	public int getCount() {
		if(events != null){
			return events.size();
		}
		else{
			return 1;
		}
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
