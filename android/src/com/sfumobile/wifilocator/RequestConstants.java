package com.sfumobile.wifilocator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RequestConstants {
	public static final Set<String> SSIDs = new HashSet<String>(Arrays.asList(new String[] {"SFUNET", "SFUNET-SECURE", "eduroam"}));
	public static String GETZONE_URL = "http://wifi-location.appspot.com/request/updatezone";
	public static String FRIEND_REQUEST_URL = "http://wifi-location.appspot.com/request/friendship";
	public static String GET_FRIEND_REQUESTS_URL = "http://wifi-location.appspot.com/request/friendlist";
	public static String ACCEPT_FRIEND_REQUEST_URL = "http://wifi-location.appspot.com/accept/friendship";

}
