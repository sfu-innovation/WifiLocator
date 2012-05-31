package com.sfumobile.wifilocator.entities;

public class WifiLocatorUser {
	
	private int _userID;
	private String _lastUpdate, _zoneName, _mapName;
	private static WifiLocatorUser _self;
	private WifiLocatorUser(){
	}
	
	public  static WifiLocatorUser getInstance(){
		if ( _self == null){
			_self = new WifiLocatorUser();
		}
		return _self;
	}
	
	public void setUserID( int userID){
		_userID = userID;
	}
	public void setZone( String zoneName ){
		_zoneName = zoneName;
	}
	public void setMap( String mapName){
		_mapName = mapName;
	}
	
	public void setLastUpdate( String lastUpdate ){
		_lastUpdate = lastUpdate;
	}
	
	public String getZone(){
		return _zoneName;
	}
	
	public String getMap(){
		return _mapName;
	}
	
	public String getLastUpdate(){
		return _lastUpdate;
	}
	
	public int getID(){
		return _userID;
	}
}
	
