package com.sfumobile.wifilocator.request;

public abstract class LocationRequest extends Request {

	public LocationRequest( int type ){
		super( type );
	}
	public abstract String getURL();

}
