package com.sfumobile.wifilocator.screens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;

import com.sfumobile.wifilocator.entities.WifiLocatorEvent;

public class WifiLocatorEventDetailsScreen extends RequestDelegateScreen implements FieldChangeListener {

	private WifiLocatorEvent _event;
	private ButtonField _backButton;
	private BasicEditField _location, _organizer,
							_startTime, _name,
							_endTime;
	public WifiLocatorEventDetailsScreen( WifiLocatorEvent event ){
		_event = event;
		
		_backButton = new ButtonField("Back", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		
		_backButton.setChangeListener( this );
		
		_location  = new BasicEditField(" Location : ","", 1024, Field.NON_FOCUSABLE);
		_organizer = new BasicEditField(" Organizer : ","", 1024, Field.NON_FOCUSABLE);
		_startTime = new BasicEditField(" Start Time : ","", 1024, Field.NON_FOCUSABLE);
		_name      = new BasicEditField(" Name : ","", 1024, Field.NON_FOCUSABLE);
		_endTime   = new BasicEditField(" End Time : ","", 1024, Field.NON_FOCUSABLE);
		
		add( _location );
		add( _organizer );
		add( _startTime );
		add( _name );
		add( _endTime );
		refresh();
		setStatus( _backButton );
	}
	
	private void refresh(){
		
		_location.setText( _event.getLocation() );
		_organizer.setText( _event.getOrganizer() );
		_startTime.setText( _event.getStartTime() );
		_name.setText( _event.getName() );
		_endTime.setText( _event.getEndTime() );
		
	}
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ) {
			
		}
	}

	public void handleStringValue(int type, String val) {
		// TODO Auto-generated method stub
		
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
