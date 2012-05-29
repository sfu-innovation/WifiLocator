package com.sfumobile.wifilocator.request;

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
		new SingleRequestThread( request ).start();
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
