package com.sfumobile.wifilocator.entities;

public class WifiLocatorEvent {
	private String _location, _organizer, _startTime, _name, _endTime;
	public WifiLocatorEvent( String location,
			                 String organizer,
			                 String startTime,
			                 String name,
			                 String endTime){
		_location = location;
		_organizer = organizer;
		_startTime = startTime;
		_name = name;
		_endTime = endTime;
	}
	
	public String getLocation(){
		return _location;
	}
	
	public String getOrganizer(){
		return _organizer;
	}
	
	public String getStartTime(){
		return _startTime;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getEndTime(){
		return _endTime;
	}
	
	public String toString(){
		return _name+" - "+_location;
	}
	                        
}
