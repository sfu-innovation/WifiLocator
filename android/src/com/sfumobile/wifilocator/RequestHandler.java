package com.sfumobile.wifilocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import org.json.JSONException;
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

	public String getBSSID(){
		return wm.getConnectionInfo().getBSSID();
	}
	
	public String getSSID(){
		return wm.getConnectionInfo().getSSID();
	}
	
	/*
	 * Gets the 3 strongest signal strengths and picks the most common zone
	 */
	public JSONObject getStrongestAP(){
		wm.startScan();
	    while(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION == null){
	    	try {
				this.wait(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    apList = wm.getScanResults();
	    
	    Collections.sort(apList, new CompareScanResult());
	    
	    List<String> zones = new ArrayList<String>();
	    List<JSONObject> jsonResponses = new ArrayList<JSONObject>();
	    
	    //Get zone names for 3 strongest signal levels
	    if(apList.size() > 2){
		    for(ScanResult result : apList.subList(0, 3)){
		        String address =  url + WifiLocatorActivity.USER + "/" + result.BSSID;
		        JSONObject zone_info = HttpGET.connect(address);
		        try {
		        	jsonResponses.add(zone_info);
					zones.add(zone_info.getString("zone_name"));
	    			Log.d("ZONE", zone_info.getString("zone_name"));
				} catch (JSONException e) {
					Log.e("Handler JSON Error:", e.getLocalizedMessage());
	    			Log.d("ZONE", "Unknown");
					zones.add("Unknown");
				}
		    }
	    }
	    //If there aren't 3 ap's, return the strongest one
	    else{
	        String address =  url + WifiLocatorActivity.USER + "/" + apList.get(0).BSSID;
	        return HttpGET.connect(address);
	    }
	    
	    //Check for the most common zone
	    if(zones.subList(2, 3).contains(zones.get(1))){
	    	return jsonResponses.get(1);
	    }
	    else{
	    	return jsonResponses.get(0);
		}
	}
	
	public JSONObject getZoneInfo(){
		
		/* Old method of picking the ap currently connected to
        String address =  url + WifiLocatorActivity.USER + "/" + wm.getConnectionInfo().getBSSID();
        JSONObject zone_info = HttpGET.connect(address);
		*/
    	JSONObject zone_info = getStrongestAP(); //"00:1f:45:64:12:f1"; 
        return zone_info;
	}
	
}
