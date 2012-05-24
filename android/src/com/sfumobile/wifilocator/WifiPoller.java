package com.sfumobile.wifilocator;

import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiManager;

public class WifiPoller {
	private WifiManager wm;
	private static String url = "http://wifi-location.appspot.com/getzone/";

	public WifiPoller(Context context){
        wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

	}

	public String getBSSID(){
        return wm.getConnectionInfo().getBSSID();
	}
	
	public JSONObject getZoneInfo(){
    	String bssid =  wm.getConnectionInfo().getBSSID(); //"00:1f:45:64:12:f1"; 
        String address =  url + bssid;
        
        JSONObject zone_info = HttpGET.connect(address);
        return zone_info;
	}
}
