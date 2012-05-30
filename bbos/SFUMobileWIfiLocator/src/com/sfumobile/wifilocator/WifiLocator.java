package com.sfumobile.wifilocator;

import net.rim.device.api.ui.UiApplication;

import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.PollingService;
import com.sfumobile.wifilocator.request.WifiChangePollingService;
import com.sfumobile.wifilocator.screens.WifiLocatorFriendsScreen;
import com.sfumobile.wifilocator.screens.WifiLocatorAddFriendScreen;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class WifiLocator extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        WifiLocator theApp = new WifiLocator();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new WifiLocator object
     */
    public WifiLocator()
    {        
    	//PollingService.getInstance().startPolling();
        // Push a screen onto the UI stack for rendering.
  //  	WifiChangePollingService pollService = WifiChangePollingService.getInstance();
      //  pushScreen(new WifiLocatorFriendsScreen());
    	//pushScreen( new WifiLocatorAddFriendScreen());
    	pushScreen( new WifiLocatorFriendsScreen());
    }


}
