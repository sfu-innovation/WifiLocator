package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

import com.sfumobile.wifilocator.entities.WifiLocatorFriend;
import com.sfumobile.wifilocator.request.ImageRequest;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;
import com.sfumobile.wifilocator.types.RequestTypes;

public class WifiLocatorFriendDetailScreen extends RequestDelegateScreen implements FieldChangeListener {

	private BasicEditField _nameField, _locationField, _timeField;
	private BitmapField _mapField;
	private WifiLocatorFriend _friend;
	private ButtonField _backButton, refreshButton;
	public WifiLocatorFriendDetailScreen(WifiLocatorFriend friend){
		_friend = friend;
		//_nameField = new BasicEditField( "Name : ", "" , 1024, Field.NON_FOCUSABLE);
		//add(_nameField);
		
		_locationField = new BasicEditField( "Location : ", "" , 1024, Field.NON_FOCUSABLE);
		add(_locationField);
		
		_mapField = new BitmapField();
		add( _mapField );
		
		_timeField = new BasicEditField( "Last updated : ",  "" ,1024, Field.NON_FOCUSABLE);
		add(_timeField);
		
		
		
		WifiLocatorMenuItems.popScreenMenuItem("Friends");
		
		//HorizontalFieldManager hfm = new HorizontalFieldManager();
		
		_backButton = new ButtonField("Back", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		
		_backButton.setChangeListener( this );
		//hfm.add(_backButton);
		setStatus( _backButton );
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		refresh();
	}
	
	public void refresh(){
		setTitle( _friend.getFirstName()+" "+_friend.getLastName());
		_locationField.setText( _friend.getLocation());
		_timeField.setText( _friend.getTime());
		
		ImageRequest  req = new ImageRequest( _friend.getMap() );
		RequestPackage imageRequest = new RequestPackage( this, req);
		SingleRequestLauncher.getInstance().sendRequest( imageRequest );
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
		if ( type == RequestTypes.IMAGE_REQUEST){
			final byte[] dat = data;
			UiApplication.getUiApplication().invokeLater(	new Thread(){
				public void run(){
					EncodedImage encodedImage = EncodedImage.createEncodedImage(dat, 0, dat.length);
					_mapField.setBitmap( encodedImage.getBitmap() );		
					invalidate();
				}
			}
			);
		}
		
	}

	public void fieldChanged(Field field, int context) {
		if ( field == _backButton ){
			close();
		}
		
	}
	

}
