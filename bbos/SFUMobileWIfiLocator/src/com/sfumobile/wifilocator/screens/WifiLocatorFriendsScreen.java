package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import org.json.me.JSONObject;

import net.rim.device.api.system.Display;
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

import com.sfumobile.wifilocator.entities.WifiLocatorData;
import com.sfumobile.wifilocator.entities.WifiLocatorFriend;
import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendsRequest;
import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.response.FriendsResponse;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;
import com.sfumobile.wifilocator.types.RequestTypes;
import com.sfumobile.wifilocator.utils.WLANContext;

public class WifiLocatorFriendsScreen extends RequestDelegateScreen implements FieldChangeListener {
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
	private ButtonField _backButton;
	public WifiLocatorFriendsScreen(){
		_screen = this;
		
	/*	addMenuItem( new MenuItem("UPDATE FRIENDS", 110, 10){
			public void run(){
				FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
				RequestPackage _friendsRequestPackage = new RequestPackage(_screen, friendsRequest);
			
				SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
			}
			
		});
		*/
		setTitle("Friends");
		
		_backButton = new ButtonField("Back", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		
		_backButton.setChangeListener( this );
		
		setStatus( _backButton );
		
		// if we backed out of friedns andn wanna go back in we should have values before
		// the new ones get loaded in!
		
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
            g.drawText(tempFriend.getFirstName()+" "+tempFriend.getLastName(), 0, y);
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
		
		WifiLocatorFriend[] placeHolderFriends = WifiLocatorData.getInstance().getFriends();
		if ( null != placeHolderFriends ){
			_friendsList.set( placeHolderFriends );
			_friendsList.setSize( placeHolderFriends.length );
			_friendsList.setEnabled ( false );
		}
		
		
		
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ){
			
			
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
			
			addMenuItem( new MenuItem("Update Friends", 110, 10){
				public void run(){
					FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
					RequestPackage _friendsRequestPackage = new RequestPackage(_screen, friendsRequest);
				
					SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
				}
				
			});
			System.out.println("*************** Added friendsRequest from PollingService");
		}
	}
	
	public void handleStringValue(int type, String val) {
		System.out.println("[SFUMOBILE] - Handling a string - "+val);
		if ( type == RequestTypes.GET_FRIENDS){
			_friendsList.setEnabled ( true );
		_friends = (Vector)(new FriendsResponse(val).handleResponse());
		
		int length = _friends.size()+1;
		friendsArray = new WifiLocatorFriend[length];
		friendsArray[0] = new WifiLocatorFriend();
		for(int i = 1; i < length; i++){
			friendsArray[i] = (WifiLocatorFriend)_friends.elementAt(i-1);
		}
		WifiLocatorData.getInstance().setFriends( friendsArray );
		_friendsList.set(friendsArray);
		_friendsList.setSize( friendsArray.length);
		}
		
	}

	public boolean onClose(){
		WifiLocatorData.getInstance().setFriends( friendsArray );
		close();
		return true;
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

	public void fieldChanged(Field field, int context) {
		if ( field == _backButton ){
			close();
		}
		
	}

	



}
