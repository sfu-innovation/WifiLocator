package com.sfumobile.wifilocator.types;

public class WifiLocatorTweet {
	private String _name;
	private String _message;
	private String _picture;
	
	public WifiLocatorTweet( String name, String message, String picture){
		_name = name;
		_message = message;
		_picture = picture;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getMessage(){
		return _message;
	}
	
	public String getPicture(){
		return _picture;
	}
	public String toString(){
		return _name+" - "+_message;
	}
}
