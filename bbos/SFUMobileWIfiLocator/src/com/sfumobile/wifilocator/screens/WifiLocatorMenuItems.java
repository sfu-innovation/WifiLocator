package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.request.RequestDelegateScreen;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class WifiLocatorMenuItems {
	public static MenuItem getViewMenuItem(){
		MenuItem _viewItem = null;
		_viewItem = new MenuItem("More Info", 110, 10){
			public void run() 
	        {
	            Dialog.inform("Display more information");
	        }
	    };
	return _viewItem;
    }
	
	
	public static MenuItem pushFriendsMenuItem(){
		MenuItem _viewItem = null;
		_viewItem = new MenuItem("Friends", 110, 10){
			public void run() 
	        {
	           UiApplication.getUiApplication().pushScreen( new WifiLocatorFriendsScreen() );
	        }
	    };
	return _viewItem;
    }
	// the ability to go back
	// friendsdetail will want to go back to friends
	// friends will want to go back to tweetzone
	// 
	public static MenuItem popScreenMenuItem(  String screenName){
		MenuItem _viewItem = null;
		_viewItem = new MenuItem( screenName , 110, 10){
			public void run() 
	        {
	           UiApplication.getUiApplication().popScreen();
	        }
	    };
	return _viewItem;
    }
	
	public static MenuItem detailedMenuItem( final Vector v, final int i ){
		MenuItem _viewItem = null;
		_viewItem = new MenuItem( "Detailed View" , 110, 10){
			public void run() 
	        {
				 UiApplication.getUiApplication().pushScreen( new WifiLocatorFriendDetailScreen(v, i) );
	        }
	    };
	return _viewItem;
    }
	
	public static MenuItem enablePollingMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("Poll", 110, 10){
			public void run(){
				if ( !PollingService.getInstance().isActive())
					Dialog.inform("Enabling polling");
					PollingService.getInstance().startPolling();
			}
		};
		return _menuItem;
	}
	
	public static MenuItem disablePollingMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("Stop Poll", 110, 10){
			public void run(){
				if ( PollingService.getInstance().isActive())
					Dialog.inform("Disabling polling");
					PollingService.getInstance().stopPolling();
			}
		};
		return _menuItem;
	}
	public static MenuItem QRCodeMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("SHOW QR", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new WifiLocatorQRCodeScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem QRViewMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("SCAN QR", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new WifiLocatorQRCodeViewerAScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem AddFriendMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("Add Friend", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new WifiLocatorAddFriendScreen());
			}};
		return _menuItem;
		
	}
	
}
