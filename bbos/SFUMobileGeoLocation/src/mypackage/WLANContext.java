package mypackage;

import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.system.WLANInfo.WLANAPInfo;

public class WLANContext {
	
	public static String getCurrentBSSID(){
		return WLANInfo.getAPInfo().getBSSID();
	}
	
	public static String getCurrentLowerCaseBSSID(){
		return  WLANInfo.getAPInfo().getBSSID().toLowerCase();
	}
	
	public static String getCurrentSSID(){
		return  WLANInfo.getAPInfo().getSSID();
		
	}
	
	public static boolean isConnected(){
		return WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED;
	}
	public static String getCurrentSignalStrength(){
		return WLANInfo.getAPInfo().getSignalLevel()+"dBm";
	}
}
