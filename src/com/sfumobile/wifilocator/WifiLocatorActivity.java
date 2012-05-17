package com.sfumobile.wifilocator;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.widget.Button;
import com.sfumobile.wifilocator.DBAdapter;

public class WifiLocatorActivity extends Activity implements OnClickListener{
    
	private String bssid, macAddr, zone;
	private WifiManager wm;
	private WifiInfo info;
	private TextView bssidText, macText, zoneText;
	private Button pollButton;
	private Handler handler;
	private DBAdapter db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        db = new DBAdapter(this.getApplicationContext());
        db.createDatabase();
        db.openDataBase();
             
        bssidText  = (TextView)this.findViewById(R.id.bssidText);
        macText    = (TextView)this.findViewById(R.id.macText);
        zoneText   = (TextView)this.findViewById(R.id.zoneText);
        pollButton = (Button)this.findViewById(R.id.pollButton);
        handler = new Handler();
        
        pollButton.setOnClickListener(this);
        
        wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        info = wm.getConnectionInfo();
        bssid = info.getBSSID();
        macAddr = info.getMacAddress();
    }
    
    public void onStart(){
    	super.onStart();
    	bssidText.setText(bssid);
    	macText.setText(macAddr);
    	
    	zone = db.getZone(bssid);
    	zoneText.setText(zone);
    	
    	new Thread(new Runnable(){
    		public void run(){
    			while(true){
    				try {
    					Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
					}
    				handler.post(new Runnable(){
    					public void run(){
    						poll();
    					}
    				});
    			}
    		}
    	}).start();
    }
    
    public void poll(){
        info = wm.getConnectionInfo();
        
        //Alert user of hand-offs
        if(bssid.compareTo(info.getBSSID()) != 0){
        	bssid = info.getBSSID();
            bssidText.setText(bssid);
            
        	zone = db.getZone(bssid);
        	zoneText.setText(zone);
            
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(this.getApplicationContext(), "Handoff!", duration);
			toast.show();
        }
     
        macAddr = info.getMacAddress();
        macText.setText(macAddr);
        
    }

	public void onClick(View src) {
		switch(src.getId()){
		case R.id.pollButton:
			poll();
			break;
		}
		
	}
}