package com.sfumobile.wifilocator;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.net.wifi.ScanResult;
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
    
	private String bssid, ssid, zone, zone_name;
	private TextView bssidText, ssidText, zoneText, zoneName;
	private Button pollButton, friendButton;
	private ImageView twitterIcon;
	private AutoPoll auto;
	private RequestHandler requestHandler;
	
	public static final String USER = "Catherine";
	public static final String USER_ID = "30001";
	
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
        twitterIcon  = (ImageView)this.findViewById(R.id.twitterIcon);
        friendButton = (Button)this.findViewById(R.id.friendButton);
        
        pollButton.setOnClickListener(this);
        twitterIcon.setOnClickListener(this);
        friendButton.setOnClickListener(this);  
        
        requestHandler = new RequestHandler(this);
    }
    
    public void onStart(){
    	super.onStart();
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
			myIntent = new Intent(src.getContext(), TwitterActivity.class);
			myIntent.putExtra("zone", zone);
			startActivity(myIntent);
			break;
		}
	}
	
	public void onStop(){
		super.onStop();
    	auto.cancel(true);
	}
	
	class AutoPoll extends AsyncTask<String, JSONObject, Void> {	

		@Override
		protected Void doInBackground(String... params) {
	  
			while(!isCancelled()) {
		        try{
		            JSONObject zone_info = requestHandler.getZoneInfo();
		            publishProgress(zone_info);
		        	Thread.sleep(1000*5);
		        } catch (InterruptedException e) {
		        	Thread.currentThread().destroy();
					e.printStackTrace();
				} catch (NullPointerException npe){
					Log.d("NULLPOINTER", npe.toString());	
					
					break;
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(JSONObject... zones){

			try{
				zone_name = zones[0].getString("zone_name");
		        zone = zones[0].getString("zone_id");
		        bssid = requestHandler.getBSSID();
		        ssid = requestHandler.getSSID();
			} catch (JSONException e) {
				Log.e("JSON Error:", e.getLocalizedMessage());
				bssid = requestHandler.getBSSID();
				ssid  = requestHandler.getSSID();
			} finally {
				zoneText.setText(zone);
				zoneName.setText(zone_name);
				bssidText.setText(bssid);
				ssidText.setText(ssid);
			}
		
		}
		@Override
		protected void onPostExecute(Void result){
			new AlertDialog.Builder(WifiLocatorActivity.this).setTitle("No WiFi detected!").setMessage("You are currently not connected to any wireless network.").setNeutralButton("Close",null).show();

		}


	}
}
