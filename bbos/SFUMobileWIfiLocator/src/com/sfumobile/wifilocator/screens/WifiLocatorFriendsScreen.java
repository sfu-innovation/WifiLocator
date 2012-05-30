package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import org.json.me.JSONObject;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;

import com.sfumobile.wifilocator.entities.WifiLocatorFriend;
import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendsRequest;
import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;
import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.JSONWifiLocatorParser;
import com.sfumobile.wifilocator.utils.WLANContext;

public class WifiLocatorFriendsScreen extends RequestDelegateScreen {
	private Vector _friends = null;
	private WifiLocatorFriend[] friendsArray = null;
	private ObjectListField _friendsList;
	private boolean firstTime = true;
	private PollingService _service;
	private FriendsRequest _friendsRequest;
	private ZoneRequest    _zoneRequest;
	//private RequestPackage _friendsRequestPackage, _zoneRequestPackage;
	private WifiLocatorFriendsScreen _screen;
	private String zoneName;
	public WifiLocatorFriendsScreen(){
		_screen = this;
		//addMenuItem(WifiLocatorMenuItems.popScreenMenuItem("Tweets"));
		addMenuItem(WifiLocatorMenuItems.enablePollingMenuItem());
		addMenuItem(WifiLocatorMenuItems.disablePollingMenuItem());
		addMenuItem(WifiLocatorMenuItems.QRCodeMenuItem());
		addMenuItem(WifiLocatorMenuItems.QRViewMenuItem());
		addMenuItem(WifiLocatorMenuItems.AddFriendRequestsViewMenuItem());
		
		addMenuItem( new MenuItem("UPDATE FRIENDS", 110, 10){
			public void run(){
				FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
				RequestPackage _friendsRequestPackage = new RequestPackage(_screen, friendsRequest);
			
				SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
			}
			
		});
		
		setTitle("Friends");
		
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ){
			
			_friendsList = new ObjectListField(){
				public void drawListRow(ListField list,
                        Graphics g,
                        int index,
                        int y,
                        int width) {
            if (index == 0) {
                g.drawText("Add New Friend", 0, y);
            } else {
            	
            	WifiLocatorFriend tempFriend = friendsArray[index];
                g.drawText(tempFriend.getName(), 0, y);
            }                
        }
				protected boolean navigationClick(int status, int time) {
					
		            int index = _friendsList.getSelectedIndex();
		            if ( index == 0 ){
		            	UiApplication.getUiApplication().pushScreen( new WifiLocatorAddFriendScreen());
		            }
		            else {
		            	UiApplication.getUiApplication().pushScreen( new WifiLocatorFriendDetailScreen(friendsArray[index]));
		            }
		            return true;
		        }
			};
			_friendsList.set(friendsArray);;
			add( _friendsList );
			
			WifiLocatorUser.getInstance().setUserID(27001);
			
			_zoneRequest = new ZoneRequest( WifiLocatorUser.getInstance().getID());
			RequestPackage _zoneRequestPackage = new RequestPackage( this, _zoneRequest);
			
			FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
			RequestPackage _friendsRequestPackage = new RequestPackage(this, friendsRequest);
		
			SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
			//SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
		//	_service = PollingService.getInstance();
		//	_service.addRequest( _friendsRequestPackage );
		//	_service.addRequest( _zoneRequestPackage );
			
			System.out.println("*************** Added friendsRequest from PollingService");
		}
	}
	
	public void handleStringValue(int type, String val) {
		System.out.println("[SFUMOBILE] - Handling a string - "+val);
		if ( type == RequestTypes.GET_FRIENDS){
		_friends = JSONWifiLocatorParser.getFriends(val);
		
		int length = _friends.size()+1;
		friendsArray = new WifiLocatorFriend[length];
		friendsArray[0] = new WifiLocatorFriend();
		for(int i = 1; i < length; i++){
			friendsArray[i] = (WifiLocatorFriend)_friends.elementAt(i-1);
		}
		_friendsList.set(friendsArray);
		_friendsList.setSize( friendsArray.length);
		}
		
	}

	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	public void handleImageDataValue(int type, byte[] data) {
		// TODO Auto-generated method stub
		
	}

	



}
