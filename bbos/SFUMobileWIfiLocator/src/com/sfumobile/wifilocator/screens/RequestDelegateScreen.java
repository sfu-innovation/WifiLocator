package com.sfumobile.wifilocator.screens;

import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendsRequest;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.container.MainScreen;
public abstract class  RequestDelegateScreen extends MainScreen {
	private RequestDelegateScreen _screen;
	public RequestDelegateScreen(){
		
	}
	
	/*protected void onUiEngineAttached( boolean attached ){
		if ( attached ){
			addMenuItem( new MenuItem("Update Zone", 110, 10){
				public void run(){
					ZoneRequest zoneRequest = new ZoneRequest(WifiLocatorUser.getInstance().getID());
					RequestPackage _friendsRequestPackage = new RequestPackage(_screen, zoneRequest);
				
					SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
				}
				
			});
			
			addMenuItem( new MenuItem("Update Friends", 110, 10){
				public void run(){
					FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
					RequestPackage _friendsRequestPackage = new RequestPackage(_screen, friendsRequest);
				
					SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
				}
				
			});
		}
	}*/
	public abstract void handleStringValue( int type, String val );
	public abstract void handleIntValue( int type, int val);
	public abstract void handleError( int type, int errorCode, Object errorString);
	public abstract void handleImageDataValue( int type, byte[] data);
	
	public void handleByteValue( int type, byte[] data ){
		switch( type ){
		case RequestTypes.CONFIRM_FRIENDSHIP_REQUEST:
		case RequestTypes.GET_FRIENDS:
		case RequestTypes.GET_FRIENDSHIP_REQUESTS:
		case RequestTypes.INIT_FRIENDSHIP:
		case RequestTypes.ZONE:
		case RequestTypes.EVENTS_REQUEST:
			handleStringValue( type, new String( data ));
			break;
		case RequestTypes.IMAGE_REQUEST:
			handleImageDataValue(type, data);
			break;
		}
	}
}
