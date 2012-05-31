package com.sfumobile.wifilocator;

public class Event {

	private String _location;
	private String _organizer;
	private String _name;
	private String _start_time;
	private String _end_time;
	
	public Event(String name, String location, String organizer, String start_time, String end_time){
		set_name(name);
		set_location(location);
		set_organizer(organizer);
		set_start_time(start_time);
		set_end_time(end_time);
	}

	public String get_location() {
		return _location;
	}

	public void set_location(String _location) {
		this._location = _location;
	}

	public String get_organizer() {
		return _organizer;
	}

	public void set_organizer(String _organizer) {
		this._organizer = _organizer;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_start_time() {
		return _start_time;
	}

	public void set_start_time(String _start_time) {
		this._start_time = _start_time;
	}

	public String get_end_time() {
		return _end_time;
	}

	public void set_end_time(String _end_time) {
		this._end_time = _end_time;
	}
	
	
}
