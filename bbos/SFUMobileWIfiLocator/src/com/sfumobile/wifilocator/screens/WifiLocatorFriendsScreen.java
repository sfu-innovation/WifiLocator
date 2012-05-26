package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import org.json.me.JSONObject;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.ObjectListField;

import com.sfumobile.wifilocator.request.FriendsRequest;
import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.request.RequestDelegate;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.JSONWifiLocatorParser;

public class WifiLocatorFriendsScreen extends RequestDelegate  {
	private Vector _friends = null;
	private Object[] friendsArray = null;
	private ObjectListField _friendsList;
	private boolean firstTime = true;
	private PollingService _service;
	private FriendsRequest _friendsRequest;
	private ZoneRequest    _zoneRequest;
	//private RequestPackage _friendsRequestPackage, _zoneRequestPackage;
	
	private String zoneName;
	public WifiLocatorFriendsScreen(){
		
		//addMenuItem(WifiLocatorMenuItems.popScreenMenuItem("Tweets"));
		addMenuItem(WifiLocatorMenuItems.enablePollingMenuItem());
		addMenuItem(WifiLocatorMenuItems.disablePollingMenuItem());
		addMenuItem(WifiLocatorMenuItems.QRCodeMenuItem());
		addMenuItem(WifiLocatorMenuItems.QRViewMenuItem());
		_friendsList = new ObjectListField();
		_friendsList.set(friendsArray);;
		add( _friendsList );
		MenuItem _viewItem = new MenuItem( "Detailed View" , 110, 10){
			public void run() 
	        {
				 UiApplication.getUiApplication().pushScreen( 
						 new WifiLocatorFriendDetailScreen(_friends
								 , _friendsList.getIndex()) );
	        }
	    };
	    addMenuItem(_viewItem);
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ){
			
			
			_zoneRequest = new ZoneRequest("Alex");
			RequestPackage _zoneRequestPackage = new RequestPackage( this, _zoneRequest);
			
			_friendsRequest = new FriendsRequest("Alex");
			RequestPackage _friendsRequestPackage = new RequestPackage(this, _friendsRequest);
			
			_service = PollingService.getInstance();
			//_service.addRequest( _friendsRequestPackage );
			//_service.addRequest( _zoneRequestPackage );
			
			System.out.println("*************** Added friendsRequest from PollingService");
		}
	}
	
	private Object[] vectorToArray(Vector v){
		int length = v.size();
		Object[] objs = new Object[length];
		v.copyInto(objs);
		return objs;
	}
	public void handleStringValue(int type, String val) {
		System.out.println("[SFUMOBILE] - Handling a string - "+val);
		if ( type == RequestTypes.GET_FRIENDS){
		_friends = JSONWifiLocatorParser.getFriends(val);
		
		friendsArray = vectorToArray(_friends);
		_friendsList.set(friendsArray);
		_friendsList.setSize( friendsArray.length);
		_friendsList.invalidate();
		}
		else if ( type == RequestTypes.ZONE){
			zoneName = JSONWifiLocatorParser.getZoneName(val);
			setTitle( "#"+zoneName );
		}
		
	}

	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	



}
