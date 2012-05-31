package com.sfumobile.wifilocator.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;
import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipConfirmResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

public class ConfirmFriendshipScreen extends RequestDelegateScreen implements FieldChangeListener {
	private WifiLocatorFriendship _friendship;
	private HorizontalFieldManager hfm;
	private ButtonField _confirmButton, _cancelButton;
	private LabelField  _messageLabel, _resultLabel;
	
	private FriendshipConfirmResponse _response;
	public ConfirmFriendshipScreen( WifiLocatorFriendship friendship){
		_friendship = friendship;
		
		_messageLabel = new LabelField();
		add(_messageLabel);
		
		hfm = new HorizontalFieldManager();
		_confirmButton = new ButtonField("Confirm");
		_confirmButton.setChangeListener( this );
		
		hfm.add(_confirmButton);
		_cancelButton = new ButtonField("Cancel");
		_cancelButton.setChangeListener( this );
		
		hfm.add(_cancelButton);
		
		add(hfm);
		
		_resultLabel = new LabelField();
		add( _resultLabel);
		
		setTitle("Confirm Friendship");
		_messageLabel.setText("Would you like to accept the friendship request from "+friendship.getFriendName());
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ) {
			
			
		}
	}

	public void handleStringValue(int type, String val) {
		if ( type == RequestTypes.CONFIRM_FRIENDSHIP_REQUEST){
			_response = new FriendshipConfirmResponse( val );
			_resultLabel.setText((String)_response.handleResponse());
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

	public void fieldChanged(Field field, int context) {
		if ( field == _confirmButton ) {
			
			//UiApplication.getUiApplication().popScreen();
			
		}
		else if ( field == _cancelButton ) {
			close();
		}
		
		
	}
}
