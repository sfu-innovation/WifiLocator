package com.sfumobile.wifilocator.screens;

import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendsRequest;
import com.sfumobile.wifilocator.request.ImageRequest;
import com.sfumobile.wifilocator.request.RequestConstants;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.request.WifiChangePollingService;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.response.ZoneResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.HorizontalFieldManager;


public class WifiLocatorMainScreen extends RequestDelegateScreen implements FieldChangeListener {

	private ButtonField _friendsButton;
	private HorizontalFieldManager _hfm;
	private BitmapField _currentMap;
	private BasicEditField _currentZone, _lastUpdatedField;
	public WifiLocatorMainScreen(){
		
		
		setTitle("Wi-fi Locator");
		
		_currentZone = new BasicEditField("Current Location : ", "Unknown", 1024, Field.NON_FOCUSABLE);
	     add(_currentZone);
		_currentMap = new BitmapField(null, Field.FIELD_HCENTER);
		
		add( _currentMap );
		
		_lastUpdatedField = new BasicEditField("Last Updated : ", "Unknown", 1024, Field.NON_FOCUSABLE);
		add(_lastUpdatedField);
		_hfm = new HorizontalFieldManager();
		
		_friendsButton = new ButtonField("Friends", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth();
				}
		};
		_friendsButton.setChangeListener( this );
		
		_hfm.add( _friendsButton );
		setStatus(_hfm);
		WifiLocatorUser.getInstance().setUserID(27001);
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		//super.onUiEngineAttached( attached );
		if ( attached ) {
			final RequestDelegateScreen _screen = this;
		//	addMenuItem( new MenuItem("Update Zone", 110, 10){
		//		public void run(){
					ZoneRequest zoneRequest = new ZoneRequest(WifiLocatorUser.getInstance().getID());
					RequestPackage _zoneRequestPackage = new RequestPackage(_screen, zoneRequest);
				WifiChangePollingService.getInstance().addRequest( _zoneRequestPackage );
				WifiChangePollingService.getInstance().startPolling();
				//	SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
		//		}
				
		//	});
			
			addMenuItem( new MenuItem("Update Friends", 110, 10){
				public void run(){
					FriendsRequest friendsRequest = new FriendsRequest(WifiLocatorUser.getInstance().getID());
					RequestPackage _friendsRequestPackage = new RequestPackage(_screen, friendsRequest);
				
					SingleRequestLauncher.getInstance().sendRequest(_friendsRequestPackage);
				}
				
			});
		}
		
	}

	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.ZONE ){
			new ZoneResponse(val).handleResponse();//edits user singleton
			WifiLocatorUser user = WifiLocatorUser.getInstance();
			_currentZone.setText(user.getZone());
			_lastUpdatedField.setText(user.getLastUpdate());
			ImageRequest request = new ImageRequest( user.getMap());
			RequestPackage reqPackage = new RequestPackage( this, request );
			SingleRequestLauncher.getInstance().sendRequest(reqPackage);
		}
		
	}

	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	public boolean onClose(){
		WifiChangePollingService.getInstance().stopPolling();
		super.close();
		return true;
		
	}
	public void handleImageDataValue(int type, byte[] data) {
		if ( type == RequestTypes.IMAGE_REQUEST){
			final byte[] dat = data;
			UiApplication.getUiApplication().invokeLater(	new Thread(){
				public void run(){
							EncodedImage hai = EncodedImage.createEncodedImage(dat, 0, dat.length);
							;
							Bitmap bitmap = hai.getBitmap();
							
							_currentMap.setBitmap( bitmap );
							invalidate();
				}
			}
			);
			
			
		}
		
	}

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		if ( field == _friendsButton){
			UiApplication.getUiApplication().pushScreen( new WifiLocatorFriendsScreen());
		}
	}
}
