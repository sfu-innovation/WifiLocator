package com.sfumobile.wifilocator.screens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.HorizontalFieldManager;


public class WifiLocatorMainScreen extends RequestDelegateScreen {

	private ButtonField _friendsButton;
	private HorizontalFieldManager _hfm;
	private BitmapField _currentMap;
	private BasicEditField _currentZone;
	public WifiLocatorMainScreen(){
		
		
		setTitle("Wi-fi Locator");
		
		_currentZone = new BasicEditField("Current Location : ", "Unknown", 1024, Field.NON_FOCUSABLE);
		_currentMap = new BitmapField();
		add( _currentMap );
		_hfm = new HorizontalFieldManager();
		
		_friendsButton = new ButtonField("Friends", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth()/2;
				}
		};
		
		_hfm.add( _friendsButton );
		setStatus(_hfm);
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		super.onUiEngineAttached( attached );
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
}
