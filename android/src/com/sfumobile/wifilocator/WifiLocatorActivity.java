package com.sfumobile.wifilocator;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

public class WifiLocatorActivity extends Activity implements OnClickListener{
    
	private String bssid, zone, zone_name;
	private TextView bssidText, zoneText, zoneName;
	private Button pollButton, friendButton;
	private ImageView twitterIcon;
	private AutoPoll auto;
	private WifiPoller wifiPoller;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bssidText   = (TextView)this.findViewById(R.id.bssidText);
        zoneText    = (TextView)this.findViewById(R.id.zoneText);
        zoneName    = (TextView)this.findViewById(R.id.zoneName);
        pollButton  = (Button)this.findViewById(R.id.pollButton);
        twitterIcon = (ImageView)this.findViewById(R.id.twitterIcon);
        friendButton = (Button)this.findViewById(R.id.friendButton);
        
        pollButton.setOnClickListener(this);
        twitterIcon.setOnClickListener(this);
        friendButton.setOnClickListener(this);  
        
        wifiPoller = new WifiPoller(this);
    }
    
    public void onStart(){
    	super.onStart();
    	//bssidText.setText("0");
    	auto = new AutoPoll();
    	auto.execute();
    	pollButton.setTag(1);
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
				auto = new AutoPoll();
		    	auto.execute();
				src.setTag(1);
			}
			break;
		case R.id.friendButton:
    		Intent nextScreen = new Intent(getApplicationContext(),Friends.class);
    		startActivity(nextScreen);
    		break;
		case R.id.twitterIcon:
			myIntent = new Intent(src.getContext(), TwitterSignInActivity.class);
			myIntent.putExtra("zone", zone);
			startActivity(myIntent);
			break;
		}
	}
	
	public void onStop(){
		super.onStop();
	}
	
	class AutoPoll extends AsyncTask<String, JSONObject, Void> {	

		@Override
		protected Void doInBackground(String... params) {
	  
			while(!isCancelled()) {
		        try{
		            JSONObject zone_info = wifiPoller.getZoneInfo();
		            publishProgress(zone_info);
		        	Thread.sleep(1000*30);
		        } catch (InterruptedException e) {
		        	Thread.currentThread().destroy();
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(JSONObject... zones){
			
			try{
				zone_name = zones[0].getString("zone_name");
		        zone = zones[0].getString("zone_id");
		        bssid = zones[0].getString("mac_address");
			} catch (JSONException e) {
				Log.e("JSON Error:", e.getLocalizedMessage());
				bssid = wifiPoller.getBSSID();
				zone = "Unknown";
				zone_name = "Unknown";				
			} finally {
				zoneText.setText(zone);
				zoneName.setText(zone_name);
				bssidText.setText(bssid);
			}
		}


	}
}
