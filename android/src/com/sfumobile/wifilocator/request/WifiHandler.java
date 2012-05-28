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
	public Boolean wifi_check(){
		return wm.isWifiEnabled();
	}

	public String getBSSID(){
		return wm.getConnectionInfo().getBSSID().toString();
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
