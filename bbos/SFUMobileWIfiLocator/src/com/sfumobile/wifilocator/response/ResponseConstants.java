package com.sfumobile.wifilocator.response;

public class ResponseConstants {
	
	//Status codes general across all responses
	
	public static final int SERVER_NO_JSON_RECIEVED = 11;
	public static final int SERVER_REQUEST_NOT_RECOGNIZED = 12;
	
	//Status codes for each response
	
	public static final int ZONE_RESPONSE_SUCCESS = 0;
	public static final int ZONE_RESPONSE_USER_NOT_FOUND = 1;
	public static final int ZONE_RESPONSE_ZONE_NOT_FOUND = 2;
	public static final int ZONE_RESPONSE_MAP_NOT_FOUND = 3;
	
	public static final int FRIENDS_RESPONSE_SUCCESS = 0;
	public static final int FRIENDS_RESPONSE_USER_NOT_FOUND = 1;

	public static final int SEND_FRIENDSHIP_REQUEST_SUCCESS = 0;
	public static final int SEND_FRIENDSHIP_REQUEST_USER_NOT_FOUND = 1;
	public static final int SEND_FRIENDSHIP_REQUEST_FRIEND_NOT_FOUND = 2;
	public static final int SEND_FRIENDSHIP_REQUEST_DUPLICATION = 3;
	public static final int SEND_FRIENDSHIP_REQUEST_ADD_SELF = 4;
	
	public static final int CHECK_FRIEND_REQUEST_SUCCESS = 0;
	public static final int CHECK_FRIEND_REQUEST_USER_NOT_FOUND = 1;
	public static final int CHECK_FRIEND_REQUEST_NO_REQUEST_FOUND = 2;
	
	public static final int CONFIRM_FRIENDSHIP_SUCCESS = 0;
	public static final int CONFIRM_FRIENDSHIP_FRIENDSHIP_NOT_FOUND = 1;
	
}
