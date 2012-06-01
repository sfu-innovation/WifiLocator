package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import com.sfumobile.wifilocator.entities.WifiLocatorData;
import com.sfumobile.wifilocator.entities.WifiLocatorEvent;
import com.sfumobile.wifilocator.request.EventsRequest;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.EventsResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ObjectListField;

public class WifiLocatorEventsScreen extends RequestDelegateScreen implements FieldChangeListener {

	private ButtonField _backButton;
	private EventsRequest _request;
	private RequestPackage _package;
	private EventsResponse _response;
	private WifiLocatorEvent[] _eventsArr;
	private ObjectListField _eventsList;
	public WifiLocatorEventsScreen(){
		_backButton = new ButtonField("Back", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		
		_backButton.setChangeListener( this );
		
		setStatus( _backButton );
		
		_request = new EventsRequest( WifiLocatorData.getInstance().getUser().getID());
		_package = new RequestPackage( this, _request );
		
		_eventsList = new ObjectListField(){
			protected boolean navigationClick(int status, int time) {
				
	            int index = _eventsList.getSelectedIndex();
	            UiApplication.getUiApplication().pushScreen( 
	            		new WifiLocatorEventDetailsScreen(_eventsArr[index]));
	            
	            return true;
	        }
		};
		add( _eventsList );
		
		WifiLocatorEvent[] preloadValues = WifiLocatorData.getInstance().getEvents();
		if ( preloadValues != null ){
			_eventsArr = preloadValues;
			_eventsList.set( _eventsArr );
			_eventsList.setSize( preloadValues.length );
			_eventsList.setEnabled( false );
		}
		setTitle("Events");
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ){
			SingleRequestLauncher.getInstance().sendRequest( _package );
		}
	}
	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.EVENTS_REQUEST){
			_response = new EventsResponse( val );
			Vector tempEvents = (Vector)_response.handleResponse();
			int length = tempEvents.size();
			_eventsArr = new WifiLocatorEvent[length];
			_eventsList.setEnabled( true );
			for(int i = 0; i < length; i++ ){
				_eventsArr[i] = (WifiLocatorEvent)tempEvents.elementAt(i);
			}
			_eventsList.set( _eventsArr );
			_eventsList.setSize( length );
		}
	}

	public boolean onClose(){
		WifiLocatorData.getInstance().setEvents( _eventsArr );
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
		// TODO Auto-generated method stub
		if ( field == _backButton ) {
			close();
		}
	}

}
