package com.sfumobile.wifilocator.screens.test;

import java.util.Vector;

import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.screens.RequestDelegateScreen;
import com.sfumobile.wifilocator.screens.ViewPendingFriendshipsScreen;
import com.sfumobile.wifilocator.screens.WifiLocatorFriendDetailScreen;
import com.sfumobile.wifilocator.screens.WifiLocatorFriendsScreen;
import com.sfumobile.wifilocator.screens.WifiLocatorAddFriendScreen;
import com.sfumobile.wifilocator.screens.WifiLocatorQRCodeViewerScreen;

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
				UiApplication.getUiApplication().pushScreen( new WifiLocatorAddFriendScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem QRViewMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("SCAN QR", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new WifiLocatorQRCodeViewerScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem AddFriendMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("Add Friend", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new WifiLocatorAddFriendTestScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem AddFriendRequestsViewMenuItem(){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("View Requests", 110, 10){
			public void run(){
				UiApplication.getUiApplication().pushScreen( new ViewPendingFriendshipsScreen());
			}};
		return _menuItem;
		
	}
	
	public static MenuItem addGetRequests(final ViewPendingFriendshipsScreen screen){
		MenuItem _menuItem = null;
		_menuItem = new MenuItem("request requests", 110, 10){
			public void run(){
				screen.foo();
			}};
		return _menuItem;
	}
	
}
