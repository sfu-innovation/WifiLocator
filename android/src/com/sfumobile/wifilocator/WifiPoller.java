package com.sfumobile.wifilocator;

import android.content.Context;
import android.net.wifi.WifiManager;

public class WifiPoller {
	private WifiManager wm;	
	public WifiPoller(Context context){
        wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

	}

	public String getBSSID(){
        return wm.getConnectionInfo().getBSSID();
	}
}
