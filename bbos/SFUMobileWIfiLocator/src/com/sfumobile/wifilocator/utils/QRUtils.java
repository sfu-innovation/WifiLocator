package com.sfumobile.wifilocator.utils;

public class QRUtils {
	
	private static final int DATA_START_POINT = 10;
	
	public static String getTargetContact(String data){
		return data.substring( DATA_START_POINT );
	}
}
