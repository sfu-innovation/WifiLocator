package com.sfumobile.wifilocator.entities;

public class WifiLocatorData {
	private static WifiLocatorData _data;
	
	private WifiLocatorFriend[] _friends;
	private WifiLocatorEvent[] _events;
	private WifiLocatorUser _user;
	private WifiLocatorFriendship[] _friendships;
	
	private WifiLocatorData(){
		
	}
	
	public synchronized static WifiLocatorData getInstance(){
		if ( _data == null ){
			_data = new WifiLocatorData();
		}
		return _data;
	}
	
	public synchronized void setFriends(WifiLocatorFriend[] friends){
		_friends = friends;
	}
	
	public synchronized void setEvents( WifiLocatorEvent[] events ){
		_events = events;
	}
	
	public synchronized void setFriendships( WifiLocatorFriendship[] friendships ) {
		_friendships = friendships;
	}
	
	public synchronized WifiLocatorUser getUser(){
		if ( _user == null ){
			_user = WifiLocatorUser.getInstance();
		}
		return _user;
	}
	
	public synchronized WifiLocatorFriend[] getFriends(){
		return _friends;
	}
	
	public synchronized WifiLocatorEvent[] getEvents(){
		return _events;
	}
	
	public synchronized WifiLocatorFriendship[] getFriendships(){
		return _friendships;
	}
	
	
}
