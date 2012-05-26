package com.sfumobile.wifilocator.request;

import net.rim.device.api.ui.UiApplication;

public class SingleRequestLauncher {
	public static SingleRequestLauncher _launcher;
	
	private SingleRequestLauncher(){
		
	}
	
	public static SingleRequestLauncher getInstance(){
		if ( _launcher == null ) {
			_launcher = new SingleRequestLauncher();
		}
		return _launcher;
	}
	
	public void sendRequest(RequestPackage request){
		UiApplication.getUiApplication().invokeLater( new SingleRequestThread( request ));
	}
	
	
}
class SingleRequestThread extends Thread{
	private RequestPackage _reqPackage;
	public SingleRequestThread( RequestPackage reqPackage ){
		_reqPackage = reqPackage;
	}
	
	public void run(){
		_reqPackage.init();
	}
}
