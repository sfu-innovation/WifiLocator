package com.sfumobile.wifilocator.entities;

public class WifiLocatorFriendship {
	private String _friendName;
	private int _id;
	
	public WifiLocatorFriendship(String friendname, int id  ){
		_friendName = friendname;
		_id = id;
	}
	
	public String getFriendName(){
		return _friendName;
	}
	
	public int getID(){
		return _id;
	}
	
	

}
