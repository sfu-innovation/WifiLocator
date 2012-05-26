package com.sfumobile.wifilocator;

import java.util.Comparator;

import android.net.wifi.ScanResult;

public class CompareScanResult implements Comparator<ScanResult> {

	public int compare(ScanResult arg0, ScanResult arg1) {
		if(arg0.level < arg1.level){
			return -1;	
		}
		else if(arg0.level > arg1.level){
			return 1;
		}
		else{
			return 0;
		}
	}

}