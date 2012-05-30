package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.MainScreen;

import com.sfumobile.wifilocator.entities.WifiLocatorFriend;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;

public class WifiLocatorFriendDetailScreen extends MainScreen {

	private BasicEditField _nameField, _locationField, _timeField;
	private BitmapField _mapField;
	private WifiLocatorFriend friend;
	public WifiLocatorFriendDetailScreen(WifiLocatorFriend _friend){
		friend =_friend;
		_nameField = new BasicEditField( "Name : ", friend.getName(), 1024, Field.NON_FOCUSABLE);
		add(_nameField);
		
		_locationField = new BasicEditField( "Location : ", friend.getLocation(), 1024, Field.NON_FOCUSABLE);
		add(_locationField);
		
		_timeField = new BasicEditField( "Last updated : ",  friend.getTime(),1024, Field.NON_FOCUSABLE);
		add(_timeField);
		
		WifiLocatorMenuItems.popScreenMenuItem("Friends");
	}
	
	public void refresh(){
		_nameField.setText( friend.getName());
		_locationField.setText( friend.getLocation());
		_timeField.setText( friend.getTime());
	}
	

}
