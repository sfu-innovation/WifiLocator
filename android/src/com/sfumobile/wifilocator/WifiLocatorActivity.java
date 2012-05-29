package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.sfumobile.wifilocator.request.FriendshipsPendingRequest;
import com.sfumobile.wifilocator.request.LocationRequest;
import com.sfumobile.wifilocator.request.RequestConstants;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;
import com.sfumobile.wifilocator.request.RequestHandler;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.WifiHandler;
import com.sfumobile.wifilocator.response.FriendshipsResponse;
import com.sfumobile.wifilocator.response.LocationResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;



public class WifiLocatorActivity extends RequestDelegateActivity implements OnClickListener{
    
	private String bssid, ssid, zone, zone_name;
	private TextView bssidText, ssidText, zoneText, zoneName;
	private Button pollButton, friendButton, locButton;
	private ImageView twitterIcon;
	private AutoPoll auto;
	private RequestHandler requestHandler;
	private WifiHandler wifiHandler;
	private AlertDialog alert;
	
	private LocationRequest            _req;
	private RequestPackage             _package;
	private LocationResponse _response;
	
	//public static final String USER = "Catherine"; //Hedy, 45006
	//public static final int USER_ID = 28001;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bssidText    = (TextView)this.findViewById(R.id.bssidText);
        ssidText     = (TextView)this.findViewById(R.id.ssidText);
        zoneText     = (TextView)this.findViewById(R.id.zoneText);
        zoneName     = (TextView)this.findViewById(R.id.zoneName);
        pollButton   = (Button)this.findViewById(R.id.pollButton);
        locButton    = (Button)this.findViewById(R.id.mapbutton);
        twitterIcon  = (ImageView)this.findViewById(R.id.twitterIcon);
        friendButton = (Button)this.findViewById(R.id.friendButton);
        
        pollButton.setOnClickListener(this);
        twitterIcon.setOnClickListener(this);
        friendButton.setOnClickListener(this);  
        locButton.setOnClickListener(this);
        
        requestHandler = new RequestHandler(this);
        wifiHandler    = requestHandler.getWifiHandler();
        
        User.getInstance().set_userID(45001);
    }
    
    public void onStart(){
    	super.onStart();
    /*	alert = new AlertDialog.Builder(this).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		}).create();
    	
    	if(!wifiHandler.wifi_check()){
    		alert.setTitle("WiFi Error");
    		alert.setMessage("No WiFi connection detected.");
    		alert.show();
    	}
    	else if (!RequestConstants.SSIDs.contains(wifiHandler.getSSID())){	
    		alert.setTitle("Connection Error");
    		alert.setMessage("The network you are connected to appears to be invalid.");
    		alert.show();
    	}
    	else {*/
    		auto = new AutoPoll(this);
        	auto.execute();
        	pollButton.setTag(1);
    	//}
    }
    
	public void onClick(View src) {
		Intent myIntent;
		switch(src.getId()){
		case R.id.pollButton:
			final int status = (Integer) src.getTag();
			if(status ==1){
				pollButton.setText("Auto Poll");
				auto.cancel(true);
				src.setTag(0);
			}else{
				pollButton.setText("Stop Polling");
				auto = new AutoPoll(this);
		    	auto.execute();
				src.setTag(1);
			}
			break;
		case R.id.friendButton:
    		Intent nextScreen = new Intent(src.getContext(),Friends.class);
    		startActivity(nextScreen);
    		break;
		case R.id.twitterIcon:
			myIntent = new Intent(src.getContext(), TwitterActivity.class);
			myIntent.putExtra("zone", zone);
			startActivity(myIntent);
			break;
		case R.id.mapbutton:
			myIntent = new Intent(getApplicationContext(), MapActivity.class);
			myIntent.putExtra("zone", zone);
			startActivity(myIntent);
		}
	}
	
	public void onStop(){
		super.onStop();
		if (auto!=null) {
			auto.cancel(true);
		}
	}
	
	class AutoPoll extends AsyncTask<String, JSONObject, Void> {	

		RequestDelegateActivity _rd;
		
		public AutoPoll(RequestDelegateActivity rd){
	        _rd = rd;
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			while(!isCancelled()) {
		        try{
		        	_req     = new LocationRequest(User.getInstance().get_userID(), wifiHandler.getBSSID());
		        	_package = new RequestPackage(_rd, _req);
		        	SingleRequestLauncher sl = SingleRequestLauncher.getInstance();
		        	sl.sendRequest(_rd, _package);
		        	Thread.sleep(1000*30);
		        } catch (InterruptedException e) {
		        	Thread.currentThread().destroy();
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	@Override
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.ZONE){
			_response = new LocationResponse( val );
			 bssid     = wifiHandler.getBSSID();
		     ssid      = wifiHandler.getSSID();
		    JSONObject data = (JSONObject)_response.handleResponse();			
		    try{
				User.getInstance().set_zone(data.getString("zone_name"));
		        User.getInstance().set_map(data.getString("map_name"));		       
			} catch (JSONException e) {
				Log.e("JSON Error:", e.getLocalizedMessage());
			} finally {
			//	zoneText.setText(zone);
				zoneName.setText(User.getInstance().get_zone());
				bssidText.setText(bssid);
				ssidText.setText(ssid);
			}
			
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
