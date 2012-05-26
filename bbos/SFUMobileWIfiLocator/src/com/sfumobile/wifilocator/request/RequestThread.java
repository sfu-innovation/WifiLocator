package com.sfumobile.wifilocator.request;

class RequestThread extends Thread{
	
	public void run(){
		System.out.println("[SFUMobile] Polling now!");
		for(int i = 0; i < _actions.size(); i++){
			RequestPackage currentRequest = (RequestPackage)_actions.elementAt( i );
			currentRequest.init();
		}
	}
}
