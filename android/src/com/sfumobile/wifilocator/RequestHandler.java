package com.sfumobile.wifilocator;

import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

public class RequestHandler {
	private WifiManager wm;
	private static String url = "http://wifi-location.appspot.com/getzone/";

	public RequestHandler(Context context){
        wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

	}

	public String getBSSID(){
        return wm.getConnectionInfo().getBSSID();
	}
	
	public JSONObject getZoneInfo(){
    	String bssid =  wm.getConnectionInfo().getBSSID(); //"00:1f:45:64:12:f1"; 
        String address =  url + WifiLocatorActivity.USER + "/" + bssid;
        Log.d("URL: ", address);
        
        JSONObject zone_info = HttpGET.connect(address);
        return zone_info;
	}
}
