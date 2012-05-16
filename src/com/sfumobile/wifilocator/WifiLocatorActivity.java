package com.sfumobile.wifilocator;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.Button;

public class WifiLocatorActivity extends Activity implements OnClickListener{
    
	private String bssid, macAddr;
	private WifiManager wm;
	private WifiInfo info;
	private TextView bssidText, macText;
	private Button pollButton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        bssidText = (TextView)this.findViewById(R.id.bssidText);
        macText   = (TextView)this.findViewById(R.id.macText);
        pollButton = (Button)this.findViewById(R.id.pollButton);
        
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
    }
    
    public void poll(){
        info = wm.getConnectionInfo();
        
        //Alert user of hand-offs
        if(bssid.compareTo(info.getBSSID()) != 0){
        	bssid = info.getBSSID();
            bssidText.setText(bssid);

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