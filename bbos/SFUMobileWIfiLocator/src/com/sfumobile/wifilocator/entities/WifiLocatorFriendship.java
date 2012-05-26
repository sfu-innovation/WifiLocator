package com.sfumobile.wifilocator.entities;

public class WifiLocatorFriendship {
	private String _friendName;
	private String _id;
	
	public WifiLocatorFriendship(String friendname, String id  ){
		_friendName = friendname;
		_id = id;
	}
	
	public String getFriendName(){
		return _friendName;
	}
	
	public String getID(){
		return _id;
	}
	
	

}
