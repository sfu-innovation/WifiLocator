package com.sfumobile.wifilocator.request;

import net.rim.device.api.ui.container.MainScreen;
public abstract class  RequestDelegateScreen extends MainScreen {
	public abstract void handleStringValue( int type, String val );
	public abstract void handleIntValue( int type, int val);
	public abstract void handleError( int type, int errorCode, Object errorString);
}
