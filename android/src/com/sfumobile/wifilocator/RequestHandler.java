package com.sfumobile.wifilocator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;


public class RequestHandler {
	private WifiManager wm;
	private static String GETZONE_URL = "http://wifi-location.appspot.com/getzone/";
	private static String FRIEND_REQUEST_URL = "http://wifi-location.appspot.com/sendrequest/";
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
		        String address =  GETZONE_URL + WifiLocatorActivity.USER + "/" + result.BSSID;
		        JSONObject zone_info = HttpGET.connect(address);
		        try {
		        	jsonResponses.add(zone_info);
					zones.add(zone_info.getString("zone_name"));
				} catch (JSONException e) {
					Log.e("Handler JSON Error:", e.getLocalizedMessage());
				}
		    }
	    }
	    //If there aren't 3 ap's, return the strongest one
	    else{
	    	try{
	    		String address =  GETZONE_URL + WifiLocatorActivity.USER + "/" + apList.get(0).BSSID;
	        	return HttpGET.connect(address);
	    	}
	    	catch(IndexOutOfBoundsException e){
	    		
	    	}
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
	
	public boolean sendFriendRequest(String id){

		JSONObject requestBody = new JSONObject();
		JSONObject response = null;
		try {
			requestBody.put("user_id", WifiLocatorActivity.USER_ID);
			requestBody.put("friend_id", id);
			response = postRequest(requestBody);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(response!=null){
			return true;
		}
		return false;
	}
	
	public JSONObject postRequest(JSONObject body){
		
		HttpClient httpClient   = new DefaultHttpClient();
		HttpPost post           = new HttpPost();
		HttpResponse response   = null;
		JSONObject jsonResponse = null;
		
		post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
		try {
			post.setEntity(new StringEntity(body.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try {
			response= httpClient.execute(post);
			Log.d("RESPONSE:", response.getEntity().toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String json = r.readLine();
			try {
				jsonResponse = new JSONObject(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonResponse;
	}
	
}
