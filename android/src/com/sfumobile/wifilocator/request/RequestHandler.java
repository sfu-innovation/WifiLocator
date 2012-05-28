package com.sfumobile.wifilocator.request;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sfumobile.wifilocator.CompareScanResult;
import com.sfumobile.wifilocator.WifiLocatorActivity;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;


public class RequestHandler {
	
	private WifiHandler wm;
	
	public RequestHandler(Context context){
       wm = new WifiHandler(context);
	}
	
	public WifiHandler getWifiHandler(){
		return wm;
	}
	
	public JSONObject accurateScan(){
		List<ScanResult> apList = wm.getStrongestAPs();
	    List<String> zones      = new ArrayList<String>();
	    List<JSONObject> jsonResponses = new ArrayList<JSONObject>();
	    
	    LocationRequest lr;
        JSONObject body;
        JSONObject response;

	    //Get zone names for 3 strongest signal levels
	    if(apList.size() > 2){
		    for(ScanResult result : apList.subList(0, 3)){
				
				lr = new LocationRequest(WifiLocatorActivity.USER_ID, result.BSSID);
				String url = lr.getURL();
				body = lr.getPayload();
				response = postRequest(body,url);

		        try {
		        	jsonResponses.add(response);
					zones.add(response.getString("zone_name"));
				} catch (JSONException e) {
					Log.e("Handler JSON Error:", e.getLocalizedMessage());
				}
		    }
	    }
	    //If there aren't 3 ap's, return the strongest one
	    else{
	    	try{
				lr = new LocationRequest(WifiLocatorActivity.USER_ID, apList.get(0).BSSID);
				String url = lr.getURL();
				body = lr.getPayload();
				response = postRequest(body,url);
	        	return response;
	    	}
	    	catch(IndexOutOfBoundsException e){
	    		System.out.println(e.getLocalizedMessage());
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
		JSONObject response;

		/*
	    
		response = accurateScan();

		*/
		
		LocationRequest lr = new LocationRequest(WifiLocatorActivity.USER_ID, wm.getBSSID());
		String url = lr.getURL();
		JSONObject body = lr.getPayload();
		
		response = postRequest(body,url);

		return response;
	}
	
	public int sendFriendRequest(int id){

		FriendshipRequest fr = new FriendshipRequest(WifiLocatorActivity.USER_ID, id);
		JSONObject body = null;

		String url = fr.getURL();
		body = fr.getPayload();

		JSONObject response = postRequest(body,url);
		if(response!=null){
			try {
				return response.getInt("status");
			} catch (JSONException e) {
				Log.d("FriendRequest","Couldn't Convert Status to Int");
				return -1;
			}
		}
		return -1;
	}
	
	public static int acceptFriendRequest(int request_id){
		JSONObject requestBody = new JSONObject();
		JSONObject response = null;
		try {
			requestBody.put("request_id", request_id);
			response = postRequest(requestBody,	RequestConstants.ACCEPT_FRIEND_REQUEST_URL);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(response!=null){
			try {
				return response.getInt("status");
			} catch (JSONException e) {
				Log.d("AcceptFriendRequest","Couldn't Convert Status to Int");
				return -1;
			}
		}
		return -1;
	}
	
	public ArrayList<JSONObject> getFriendRequests(){
		JSONObject requestBody = new JSONObject();
		JSONObject response = null;
		ArrayList<JSONObject> result;
		try {
			requestBody.put("user_id", WifiLocatorActivity.USER_ID);
			response = postRequest(requestBody,RequestConstants.GET_FRIEND_REQUESTS_URL);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(response!=null){
			return parseFriendRequests(response);
		}
		result = new ArrayList<JSONObject>();
		return result;
	}
	
	public ArrayList<JSONObject> parseFriendRequests(JSONObject response){
		Log.d("FriendRequests",response.toString());
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		try {
			if(response.getInt("status")!=2){
				JSONArray requests = response.getJSONArray("requests");
				for(int i=0; i < requests.length(); i++){
					result.add(requests.getJSONObject(i));
				}
				return result;
			}
		} catch (JSONException e) {
			Log.d("ParseFriendRequests",e.getLocalizedMessage());
		}
		return result;
	}
	
	public static JSONObject postRequest(JSONObject body, String url){
		
		HttpClient httpClient   = new DefaultHttpClient();
		HttpPost post           = new HttpPost(url);
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