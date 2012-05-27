package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.ui.container.MainScreen;
public abstract class  RequestDelegateScreen extends MainScreen {
	public abstract void handleStringValue( int type, String val );
	public abstract void handleIntValue( int type, int val);
	public abstract void handleError( int type, int errorCode, Object errorString);
	public abstract void handleImageDataValue( int type, byte[] data);
	
	public void handleByteValue( int type, byte[] data ){
		switch( type ){
		case RequestTypes.CONFIRM_FRIENDSHIP_REQUEST:
		case RequestTypes.GET_FRIENDS:
		case RequestTypes.GET_FRIENDSHIP_REQUESTS:
		case RequestTypes.INIT_FRIENDSHIP:
		case RequestTypes.ZONE:
			handleStringValue( type, new String( data ));
			break;
		case RequestTypes.IMAGE_REQUEST:
			handleImageDataValue(type, data);
			break;
		}
	}
}