package com.sfumobile.wifilocator;

import java.io.InputStream;
import java.net.URL;

import com.sfumobile.wifilocator.request.MapRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MapActivity extends RequestDelegateActivity{
	private Drawable image;
	private ImageView img;
	private MapRequest  _req;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_map);
		img = (ImageView) findViewById(R.id.mapview);
		
		handler = new Handler();
		_req = new MapRequest(getIntent().getExtras().getString("map_name"));
		
		image = getImage(_req.getURL());
		img.setImageDrawable(image);

	}
	
	private Drawable getImage(String url){
		try{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is,  "src");
			return d;
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public void handleStringValue(int type, String val) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleImageDataValue(int type, String data) {
		// TODO Auto-generated method stub
		
	}

}
