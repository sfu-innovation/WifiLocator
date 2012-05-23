package com.sfumobile.wifilocator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import com.sfumobile.wifilocator.HttpGET;

public class WifiLocatorActivity extends Activity implements OnClickListener{
    
	private String bssid, zone, address;
	private WifiManager wm;
	private WifiInfo info;
	private TextView bssidText, zoneText;
	private Button pollButton, friendButton;
	private Handler handler;
	private ImageView twitterIcon;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bssidText   = (TextView)this.findViewById(R.id.bssidText);
        zoneText    = (TextView)this.findViewById(R.id.zoneText);
        pollButton  = (Button)this.findViewById(R.id.pollButton);
        twitterIcon = (ImageView)this.findViewById(R.id.twitterIcon);
        friendButton = (Button)this.findViewById(R.id.friendButton);
      //  handler = new Handler();
        
        pollButton.setOnClickListener(this);
        twitterIcon.setOnClickListener(this);
        friendButton.setOnClickListener(this);
        
        wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        info = wm.getConnectionInfo();
        bssid = info.getBSSID();
    }
    
    public void onStart(){
    	super.onStart();
    	bssidText.setText(bssid);
    	
        try{
			poll();
		} catch (Exception e){
			e.printStackTrace();
		}
        
    	/*
    	new Thread(new Runnable(){
    		public void run(){
    			while(true){
    				try {
    					Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
					
    				handler.post(new Runnable(){
    					public void run(){
    						try{
    							poll();
    						} catch (Exception e){
    							e.printStackTrace();
    						}
    					}
    				});
					}
    			}
    		}
    	}).start();*/
    }
    
    public void poll(){
    	
        info = wm.getConnectionInfo();

        	bssid = info.getBSSID();
            bssidText.setText(bssid);
            address = "http://wifi-location.appspot.com/rest/BSSIDZones?feq_mac_address=" + bssid;
            
            try{
            	// JSONObject json = new JSONObject(test);
                JSONObject json1 = HttpGET.connect(address);
                JSONObject lists = json1.getJSONObject("list");
                JSONObject zones = lists.getJSONObject("BSSIDZones");
                String zone_key = zones.getString("zones");
                
                address = "http://wifi-location.appspot.com/rest/Areas/" + zone_key;
                
                JSONObject json2 = HttpGET.connect(address);
                JSONObject areas = json2.getJSONObject("Areas");
                String zone_name = areas.getString("zone_name");
                zone = areas.getString("zone_id");
	            
	        	zoneText.setText(zone);
            }
            catch(JSONException e){
            	Log.e("JSON Error:", e.getLocalizedMessage());
            	zone = "Unknown";
	        	zoneText.setText(zone);
            }   

    }

	public void onClick(View src) {
		@SuppressWarnings("unused")
		Intent myIntent;
		switch(src.getId()){
		case R.id.pollButton:
			try{
				poll();
			} catch (Exception e) {
				e.printStackTrace();
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
}
