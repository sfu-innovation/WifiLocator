package com.sfumobile.wifilocator.entities;

public class WifiLocatorFriend {
	private String _firstName;
	private String _lastName;
	private String _location;
	private String _time;
	private String _map;
	public String toString(){
		return _firstName+" "+_lastName+" ["+_location+"]";
	}
	public WifiLocatorFriend( String firstName, String lastName, String location, String time, String map){
		_firstName = firstName;
		_lastName = lastName;
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
	
	public String getFirstName(){
		return _firstName;
	}
	
	public String getLastName(){
		return _lastName;
	}
	
	public String getTime(){
		return _time;
	}
	
}
