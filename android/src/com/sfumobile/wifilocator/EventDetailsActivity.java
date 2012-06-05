package com.sfumobile.wifilocator;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sfumobile.wifilocator.request.RequestDelegateActivity;

public class EventDetailsActivity extends RequestDelegateActivity{

	private Event _event;
	private TextView nameText, locationText, descriptionText, organizerText, startTimeText, endTimeText;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		
		try{
			Bundle extras = getIntent().getExtras();
			_event = extras.getParcelable("com.sfumobile.wifilocator.Event");
		}
		catch(Exception e){
			System.out.println("Failed to load event from parcel");
		}
		
		nameText = (TextView)findViewById(R.id.nameText);
		locationText = (TextView)findViewById(R.id.locationText);
		descriptionText = (TextView)findViewById(R.id.descriptionText);
		organizerText = (TextView)findViewById(R.id.organizerText);
		startTimeText = (TextView)findViewById(R.id.startTimeText);
		endTimeText = (TextView)findViewById(R.id.endTimeText);
	}



	@Override
	protected void onStart() {
		super.onStart();
		Log.d("Populating List", _event.get_description());
		Log.d("Populating List", _event.get_organizer());
		nameText.setText(_event.get_name());
		locationText.setText(_event.get_location());
		descriptionText.setText(_event.get_description());
		organizerText.setText(_event.get_organizer());
		startTimeText.setText(_event.get_start_time());
		endTimeText.setText(_event.get_end_time());
	}



	@Override
	public void handleStringValue(int type, String val) {
		// TODO Auto-generated method stub
		
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
