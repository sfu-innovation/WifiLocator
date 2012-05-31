package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class FriendRequestAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private RequestDelegateActivity _rd;
	private static ArrayList<JSONObject> _data;
	private Handler handler;
	private FriendshipConfirmRequest  _req;
	private RequestPackage            _package;
	
	public FriendRequestAdapter(RequestDelegateActivity rd, ArrayList<JSONObject> data){
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(rd);
		_rd = rd;
		_data = data;
		handler = new Handler();
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid
		// unneccessary calls
		// to findViewById() on each row.
		ViewHolder holder;
		
		// When convertView is not null, we can reuse it directly, there is
		// no need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
		convertView = mInflater.inflate(R.layout.friend_request_line, null);
		 
		// Creates a ViewHolder and store references to the two children
		// views
		// we want to bind data to.
		holder = new ViewHolder();
		holder.friendText = (TextView) convertView.findViewById(R.id.friendNameText);
		holder.confirmButton = (Button) convertView.findViewById(R.id.confirmButton);
		holder.rejectButton = (Button) convertView.findViewById(R.id.rejectButton);
		
		holder.confirmButton.setOnClickListener(new OnClickListener() {
	
		public void onClick(View v) {
					
			try {
				_req = new FriendshipConfirmRequest( _data.get(position).getInt("request_id") );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			_package = new RequestPackage(_rd, _req, handler);
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest(_rd, _package);
			_data.remove(position);
			notifyDataSetChanged();

		 
		}
		});
		
		holder.rejectButton.setOnClickListener(new OnClickListener() {
		private int pos = position;
		 
		
		public void onClick(View v) {
		Toast.makeText(_rd, "Reject-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();
		 
		}
		});
		
		convertView.setTag(holder);
		} else {
		// Get the ViewHolder back to get fast access to the TextView
		// and the ImageView.
		holder = (ViewHolder) convertView.getTag();
		}
		 
		// Bind the data efficiently with the holder.
		try {
			holder.friendText.setText(_data.get(position).getString("friend_name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 
		return convertView;
	}
		 
	public int getCount() {
		return _data.size();
	}

	public Object getItem(int index) {
		return _data.get(index);
	}

	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		TextView friendText;
		Button confirmButton;
		Button rejectButton;
	}
}
