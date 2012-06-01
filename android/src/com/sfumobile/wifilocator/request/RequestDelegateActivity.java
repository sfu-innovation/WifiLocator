package com.sfumobile.wifilocator.request;

import android.app.Activity;

import com.sfumobile.wifilocator.types.RequestTypes;

public abstract class  RequestDelegateActivity extends Activity {
	public abstract void handleStringValue( int type, String val );
	public abstract void handleIntValue( int type, int val);
	public abstract void handleError( int type, int errorCode, Object errorString);
	//Changed this to String from byte[] for a quick workaround to get it working
	public abstract void handleImageDataValue( int type, String data);
	
	public void handleByteValue( int type, String data ){
		System.out.println("Handle byte type: " + Integer.toString(type));

		switch( type ){
		case RequestTypes.CONFIRM_FRIENDSHIP_REQUEST:
			handleStringValue( type, data);
			break;
		case RequestTypes.GET_FRIENDS:
			handleStringValue( type, data);
			break;
		case RequestTypes.GET_FRIENDSHIP_REQUESTS:
			handleStringValue( type, data);
			break;
		case RequestTypes.FRIENDSHIP_REQUEST:
			handleStringValue( type, data);
			break;
		case RequestTypes.EVENTS_REQUEST:
			handleStringValue( type, data);
			break;
		case RequestTypes.ZONE:
			handleStringValue( type, data);
			break;
		case RequestTypes.IMAGE_REQUEST:
			handleImageDataValue(type, data);
			break;
		}
	}
}
