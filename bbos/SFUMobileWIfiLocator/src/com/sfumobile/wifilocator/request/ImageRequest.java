package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class ImageRequest extends Request {
	
	private String _url;
	public ImageRequest( String url){
		super( RequestTypes.IMAGE_REQUEST);
		_url = url;
	}
	public String getURL() {
		// TODO Auto-generated method stub
		return RequestConstants.BASE_IMAGE_URL+_url;
	}

}
