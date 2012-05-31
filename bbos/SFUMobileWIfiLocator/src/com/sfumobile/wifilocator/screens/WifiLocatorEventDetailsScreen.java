package com.sfumobile.wifilocator.screens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;

import com.sfumobile.wifilocator.entities.WifiLocatorEvent;

public class WifiLocatorEventDetailsScreen extends RequestDelegateScreen implements FieldChangeListener {

	private WifiLocatorEvent _event;
	private ButtonField _backButton;
	public WifiLocatorEventDetailsScreen( WifiLocatorEvent event ){
		_event = event;
		
		_backButton = new ButtonField("Back", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		
		_backButton.setChangeListener( this );
		
		setStatus( _backButton );
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
