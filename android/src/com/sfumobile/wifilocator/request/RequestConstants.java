package com.sfumobile.wifilocator.request;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RequestConstants {
	public static final Set<String> SSIDs = new HashSet<String>(Arrays.asList(new String[] {"SFUNET", "SFUNET-SECURE", "eduroam"}));
	public static String GETZONE_URL = "http://wifi-location.appspot.com/request/updatezone";
	public static String FRIEND_REQUEST_URL = "http://wifi-location.appspot.com/request/friendship";
	public static String GET_FRIENDS_URL = "http://wifi-location.appspot.com/request/friendlist";
	public static String GET_FRIEND_REQUESTS_URL = "http://wifi-location.appspot.com/request/pending/friendships";
	public static String ACCEPT_FRIEND_REQUEST_URL = "http://wifi-location.appspot.com/accept/friendship";
	public static String host = "http://wifi-location.appspot.com";

}
