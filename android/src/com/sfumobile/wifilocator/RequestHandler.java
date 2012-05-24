package com.sfumobile.wifilocator;

import java.util.List;

import org.json.JSONObject;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

public class RequestHandler {
	private WifiManager wm;
	private static String url = "http://wifi-location.appspot.com/getzone/";
	private List<ScanResult> apList;
	
	public RequestHandler(Context context){
        wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

	}

	public ScanResult getBS(){
		wm.startScan();
	    while(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION == null){
	    	try {
				this.wait(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    apList = wm.getScanResults();
	    ScanResult strongestBs = apList.get(0);
	    for(ScanResult result : apList){
	    	if(result.level > strongestBs.level){
	    		strongestBs = result;
	    	}
	    	Log.d("SCAN", Integer.toString(result.level));
	    }
    	Log.d("STRONGEST", Integer.toString(strongestBs.level));
        return strongestBs;
	}
	
	public JSONObject getZoneInfo(){
    	String bssid = getBS().BSSID; //"00:1f:45:64:12:f1"; 
        String address =  url + WifiLocatorActivity.USER + "/" + bssid;
        Log.d("URL: ", address);
        
        JSONObject zone_info = HttpGET.connect(address);
        return zone_info;
	}
	
}
