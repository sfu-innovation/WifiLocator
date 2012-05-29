package com.sfumobile.wifilocator.request;

public class MapRequest extends Request{
	
	private String url;
	
	public MapRequest(String path){
		super(RequestTypes.MAP);
		url = path;
	}
	
	@Override
	public String getURL(){
		return url;
	}

}
