package com.sfumobile.wifilocator.entities;

public class WifiLocatorFriend {
	private String _name;
	private String _location;
	private String _time;
	private String _map;
	public String toString(){
		return _name+" -> location : "+_location;
	}
	public WifiLocatorFriend( String name, String location, String time, String map){
		_name = name;
		_location = location;
		_time = time;
		_map = map;
	}
	
	public WifiLocatorFriend(){
		
	}
	
	public String getMap(){
		return _map;
	}
	
	public String getLocation(){
		return _location;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getTime(){
		return _time;
	}
	
}
