package com.sfumobile.wifilocator.request;
import com.sfumobile.wifilocator.types.RequestTypes;

public class MapRequest extends Request{
	
	private String url;
	
	public MapRequest(String path){
		super(RequestTypes.IMAGE_REQUEST);
		url = path;
	}
	
	@Override
	public String getURL(){
		return RequestConstants.host+url;
	}

}
