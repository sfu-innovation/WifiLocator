package com.sfumobile.wifilocator.request;

import net.rim.device.api.ui.UiApplication;

public class TestRequest implements IRequest {
	public TestRequest(){
		
	}
	public void init(){
		UiApplication.getUiApplication().invokeLater( new HelloWorldThread());
	}
	


}
class HelloWorldThread extends Thread{
	public HelloWorldThread(){
		
	}
	 public void run(){
		 System.out.println("Hello World");
	 }
}

