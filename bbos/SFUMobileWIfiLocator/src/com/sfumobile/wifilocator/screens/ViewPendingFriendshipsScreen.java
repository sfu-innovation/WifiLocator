package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;
import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.FriendshipRequestRetrieval;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.TemporaryPollingService;
import com.sfumobile.wifilocator.response.FriendshipRetrievalResponse;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ObjectListField;

	
public class ViewPendingFriendshipsScreen extends RequestDelegateScreen{
	private ObjectListField         _pendingFriendships;
	private TemporaryPollingService _pollService;
	private WifiLocatorFriendship[] _pendingFriendshipsData;
	private FriendshipRequestRetrieval _req;
	private RequestPackage             _package;
	private FriendshipRetrievalResponse _response;
	private ViewPendingFriendshipsScreen _self;
	private Dialog _confirmFriendshipDialog;
	
	public ViewPendingFriendshipsScreen(){
		// add the refresh button menu item
		
		setTitle("Pending Friendship Requests");
		// need a good way to uniquely start and stop polling for this class
	
	//	_pollService  = TemporaryPollingService.getInstance();
		_req = new FriendshipRequestRetrieval( WifiLocatorUser.getInstance().getID() );
		_package = new RequestPackage(this, _req);
	
	}
	
	public boolean onClose(){
	//	if ( _pollService.isActive()){
	////		_pollService.stopPolling();
	//	}
	//	_pollService = null;
	//	System.out.println("Closing polling!!!");
		close();
		return true;
	}
	protected void onUiEngineAttached(boolean attached){
	//	_pollService.addRequest( _package );
	//	_pollService.startPolling();
		_self = this;
		addMenuItem( WifiLocatorMenuItems.addGetRequests( this ));
		_pendingFriendships = new ObjectListField(){
			protected boolean navigationClick(int status, int time) {
				String[] choices = {"Accept", "Reject", "Cancel"};
	            int index = _pendingFriendships.getSelectedIndex();
	            int choice = Dialog.ask(
	            		"Would you like to accept the friendship request from\n\n"+_pendingFriendshipsData[index].getFriendName(),
	            		choices, 0);
	            switch ( choice ) {
	            case 0: 
	            	FriendshipConfirmRequest _req = new FriendshipConfirmRequest( _pendingFriendshipsData[index].getID());
	    			//System.out.println( _req.getURL() + "\n\n" + _req.getPayload());
	    			RequestPackage _package = new RequestPackage( _self , _req );
	    			SingleRequestLauncher instance = SingleRequestLauncher.getInstance();
	    			instance.sendRequest( _package);
	            	break;
	            case 1:
	            case 2: 
	            	
	            	break;
	            	
	            }
	            foo();
	            return true;
	        }
		};
		_pendingFriendshipsData = new WifiLocatorFriendship[0];
		_pendingFriendships.set(_pendingFriendshipsData);
		add( _pendingFriendships );
	}
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.GET_FRIENDSHIP_REQUESTS){
			_response = new FriendshipRetrievalResponse( val );
		    Vector tempFriendships = (Vector)_response.handleResponse();
		    int length = tempFriendships.size();
		    _pendingFriendshipsData = new WifiLocatorFriendship[length];
		    for(int i = 0; i < tempFriendships.size(); i++){
		    	_pendingFriendshipsData[i] = (WifiLocatorFriendship)tempFriendships.elementAt(i);
		    }
			_pendingFriendships.set(_pendingFriendshipsData);
			_pendingFriendships.setSize(_pendingFriendshipsData.length);
			
			
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
	
	public void foo(){
		SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
		launcher.sendRequest( _package );
		System.out.println(" Sending request now!");
	}
}
