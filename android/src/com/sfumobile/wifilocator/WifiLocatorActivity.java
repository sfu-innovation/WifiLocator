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
import android.os.Handler;
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
    
	private String bssid, ssid;
	private TextView bssidText, ssidText, zoneName;
	private Button pollButton, friendButton, locButton;
	private ImageView twitterIcon;
	private AutoPoll auto;
	private RequestHandler requestHandler;
	private WifiHandler wifiHandler;
	private AlertDialog alert;
	private Handler handler;
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
        zoneName     = (TextView)this.findViewById(R.id.zoneName);
        pollButton   = (Button)this.findViewById(R.id.pollButton);
        locButton    = (Button)this.findViewById(R.id.mapbutton);
        twitterIcon  = (ImageView)this.findViewById(R.id.twitterIcon);
        friendButton = (Button)this.findViewById(R.id.friendButton);
        
        pollButton.setOnClickListener(this);
        twitterIcon.setOnClickListener(this);
        friendButton.setOnClickListener(this);  
        locButton.setOnClickListener(this);
        
        handler        = new Handler();
        requestHandler = new RequestHandler(this);
        wifiHandler    = requestHandler.getWifiHandler();
        bssid          = "";
        
    	auto = new AutoPoll(this);
    	
        User.getInstance().set_userID(45006);
    }
    
    public void onStart(){
    	super.onStart();
    	if(!wifiHandler.wifiEnabled()){
    		alert = AlertDialogBuilder.createDialog(this, "Wifi isn't turned on");
    		alert.show();
    		if(auto.getStatus() == AsyncTask.Status.RUNNING){
    			auto.cancel(true);
    		}
    	}
    	else if(!wifiHandler.wifiConnected()){
    		alert = AlertDialogBuilder.createDialog(this, "You aren't connected to any networks.");
    		alert.show();
    		if(auto.getStatus() == AsyncTask.Status.RUNNING){
    			auto.cancel(true);
    		}
    	}
    	else{
    		if(auto.getStatus() == AsyncTask.Status.PENDING || auto.isCancelled()){
		        auto = (AutoPoll) new AutoPoll(this).execute();
		        pollButton.setTag(1);
    		}
    	}
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
			myIntent.putExtra("zone", User.getInstance().get_zone());
			startActivity(myIntent);
			break;
		case R.id.mapbutton:
			myIntent = new Intent(getApplicationContext(), MapActivity.class);
			myIntent.putExtra("zone", User.getInstance().get_zone());
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
		        	//Check every second to see if the bssid has changed
		        	//Only poll the server if it has
		        	System.out.println("Running");
		        	if(bssidChanged()){
		        		updateZoneInfo(_rd);
		        	}
		        	Thread.sleep(1000);
		        } catch (InterruptedException e) {
		        	Thread.currentThread().destroy();
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	public boolean bssidChanged(){
		String current_bssid = wifiHandler.getBSSID();
		if(current_bssid.hashCode() != bssid.hashCode()){
			bssid = current_bssid;
			return true;
		}
		return false;
	}
	
	public void updateZoneInfo(RequestDelegateActivity rd){
    	_req     = new LocationRequest(User.getInstance().get_userID(), wifiHandler.getBSSID());
    	_package = new RequestPackage(rd, _req, handler);
    	SingleRequestLauncher sl = SingleRequestLauncher.getInstance();
    	sl.sendRequest(rd, _package);
	}
	
	@Override
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.ZONE){
			_response = new LocationResponse( val );
			 bssid     = wifiHandler.getBSSID();
		     ssid      = wifiHandler.getSSID();
		    JSONObject data = (JSONObject)_response.handleResponse();			
		    try{
		    	Log.d("zone request", data.toString());
				User.getInstance().set_zone(data.getString("zone_name"));
		        User.getInstance().set_map(data.getString("map_name"));		       
			} catch (JSONException e) {
				Log.e("JSON Error:", e.getLocalizedMessage());
			} finally {
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
