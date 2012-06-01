package com.sfumobile.wifilocator;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.widget.ImageView;

import com.sfumobile.wifilocator.request.MapRequest;
import com.sfumobile.wifilocator.request.RequestDelegateActivity;

public class MapUpdateThread extends Thread implements Runnable{
		
		private ImageView mapView;
		private Drawable image;
		private RequestDelegateActivity _rd;
		
		public MapUpdateThread(ImageView canvas, RequestDelegateActivity rd){
			mapView = canvas;
			_rd = rd;
		}

		
		public void run(){
			System.out.println("Updating Map");
			MapRequest req = new MapRequest(UserObject.getInstance().get_map());
			image = getImage(req.getURL());
			Display display = _rd.getWindowManager().getDefaultDisplay();
			MapDrawer m = new MapDrawer(mapView, image, display);
			_rd.runOnUiThread(m);

		}
		
		private Drawable getImage(String url){
			System.out.println("[Downloading MAP from URL] - " + url);
			try{
				InputStream is = (InputStream) new URL(url).getContent();
				Drawable d     = Drawable.createFromStream(is,  "src");
				return d;
			} catch (Exception e){
				return null;
			}
		}
	
		public class MapDrawer extends Thread implements Runnable{
			
			private ImageView _canvas;
			private Drawable  _map;
			private Display _display;
			
			public MapDrawer(ImageView canvas, Drawable map, Display display){
				_canvas = canvas;
				_map    = map;
				_display = display;
			}
			
			public void run() {
				if(_map != null){

					int width = _display.getWidth();
					int height = _display.getHeight();
					Bitmap bm = ((BitmapDrawable)_map).getBitmap();
					_canvas.setImageBitmap(Bitmap.createScaledBitmap(bm, width, height, true));
					_canvas.setImageBitmap(bm);
				}
			}
		}
		
	}