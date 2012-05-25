package com.sfumobile.wifilocator.utils;

import net.rim.device.api.system.WLANInfo;

public class WLANContext {
	
	public static final int WLAN_RADIO_ON = 0;
	public static final int WLAN_RADIO_OFF = 1;
	
	public static final int WLAN_RADIO_CONNECTED = 2;
	public static final int WLAN_RADIO_NOT_CONNECTED = 3;
	
	
	public static String getBSSID(){
		return WLANInfo.getAPInfo().getBSSID().toLowerCase();
	}
	
	public static String getCurrentSSID(){
		return WLANInfo.getAPInfo().getSSID();
	}
	public static int getCurrentSignalLevel(){
        return WLANInfo.getAPInfo().getSignalLevel();
	}
	
	public static int isAssociated(){
		int retVal = WLAN_RADIO_ON;
		//check if the radio is on
		if (WLANInfo.getAPInfo() != null){
			//check if we are connected to a network
			if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED){
				retVal = WLAN_RADIO_CONNECTED;
			}
			else {
				retVal = WLAN_RADIO_NOT_CONNECTED;
			}
		}
		else {
			retVal = WLAN_RADIO_OFF;
		}
		return retVal;
	}
}
