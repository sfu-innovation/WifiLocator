package com.sfumobile.wifilocator.request;

import java.util.Collections;
import java.util.List;

import com.sfumobile.wifilocator.CompareScanResult;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WifiHandler {
	private WifiManager wm;
	private List<ScanResult> apList;
	
	public WifiHandler(Context context){
		 wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	}
	public Boolean wifiEnabled(){
		return wm.isWifiEnabled();
	}
	
	public Boolean wifiConnected(){
		if(wm.getConnectionInfo().getSSID() == null){
			return false;
		}
		return true;
	}

	public String getBSSID(){
		try{
			return wm.getConnectionInfo().getBSSID().toString();//"00:1f:45:64:17:d1";
		}
		catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}
	
	public String getSSID(){
		return wm.getConnectionInfo().getSSID().toString();
	}
	
	/*
	 * Gets the 3 strongest signal strengths and picks the most common zone
	 */
	public List<ScanResult> getStrongestAPs(){
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
	    return apList.subList(0, 3);
	}
}
