package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;
import com.sfumobile.wifilocator.request.FriendshipRequestRetrieval;
import com.sfumobile.wifilocator.request.RequestDelegateScreen;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.TemporaryPollingService;
import com.sfumobile.wifilocator.response.FriendshipRetrievalResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ObjectListField;

	
public class ViewPendingFriendshipsScreen extends RequestDelegateScreen implements FieldChangeListener{
	private ObjectListField         _pendingFriendships;
	private TemporaryPollingService _pollService;
	private WifiLocatorFriendship[] _pendingFriendshipsData;
	private int                     _userid;
	private FriendshipRequestRetrieval _req;
	private RequestPackage             _package;
	private FriendshipRetrievalResponse _response;
	
	public ViewPendingFriendshipsScreen(){
		// add the refresh button menu item
		
		
		// need a good way to uniquely start and stop polling for this class
		_pendingFriendships = new ObjectListField();
		_pendingFriendshipsData = new WifiLocatorFriendship[0];
		_pendingFriendships.set(_pendingFriendshipsData);
		_pendingFriendships.setChangeListener( this );
		add( _pendingFriendships );
		_userid = 27001;
		_pollService  = TemporaryPollingService.getInstance();
		_req = new FriendshipRequestRetrieval( _userid );
		_package = new RequestPackage(this, _req);
		
	}
	
	public boolean onClose(){
		if ( _pollService.isActive()){
			_pollService.stopPolling();
		}
		_pollService = null;
		System.out.println("Closing polling!!!");
		close();
		return true;
	}
	protected void onUiEngineAttached(boolean attached){
	//	_pollService.addRequest( _package );
	//	_pollService.startPolling();
		addMenuItem( WifiLocatorMenuItems.addGetRequests( this ));
		addMenuItem( new MenuItem("select request", 110, 10){
			public void run(){
				WifiLocatorFriendship temp = null;
				temp = (WifiLocatorFriendship)_pendingFriendshipsData[_pendingFriendships.getSelectedIndex()];
				if ( temp instanceof WifiLocatorFriendship ) {
					UiApplication.getUiApplication().pushScreen(( new ConfirmFriendshipScreen( temp )));
				}
			}
		});
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

	public void fieldChanged(Field field, int arg1) {
	/*	if ( field == _pendingFriendships ) {
			
		}*/
		
	}
	
}
