package com.sfumobile.wifilocator;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable{

	private String _location;
	private String _organizer;
	private String _name;
	private String _start_time;
	private String _end_time;
	
	public Event(){
		
	}
	
	public Event(Parcel in){
		readFromParcel(in);
	}
	
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

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flagss) {
		parcel.writeString(_name);
		parcel.writeString(_location);
		parcel.writeString(_organizer);
		parcel.writeString(_start_time);
		parcel.writeString(_end_time);
	}
	
	public void readFromParcel(Parcel parcel){
		_name = parcel.readString();
		_location = parcel.readString();
		_organizer = parcel.readString();
		_start_time = parcel.readString();
		_end_time = parcel.readString();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Event createFromParcel(Parcel in) {
			return new Event(in);
	    }
	 
	    public Event[] newArray(int size) {
	    	return new Event[size];
	    }
	};
	
}
