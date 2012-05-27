package com.sfumobile.wifilocator;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


public class GetMap extends Activity{
	private Drawable image;
	private ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	EditText url;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_map);
		
		img = (ImageView) findViewById(R.id.imageView1);
		getimg map = new getimg();
    	map.execute(img);

	}
	
	private static Drawable getImage(String url){
		try{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is,  "src");
			return d;
		} catch (Exception e){
			return null;
		}
	}
	
	private String gen_URL(String loc){
		
		//String url = "http://wifi-location.appspot.com/media/zonemaps/"+loc+".png";
		String url = "http://wifi-location.appspot.com/media/zonemaps/map2.png";
		return url;
	}
	
	class getimg extends AsyncTask<ImageView, Drawable, Void> {	
		private ProgressDialog dialog = new ProgressDialog(GetMap.this);
		
		@Override
		protected void onPreExecute(){
			dialog.setMessage("Loading...");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
		}
		@Override
		protected Void doInBackground(ImageView... params) {
			String zone = getIntent().getExtras().getString("zone");
			String[] splitter = zone.split(",");
			Log.d("URL", gen_URL(splitter[0]));
			image = getImage(gen_URL(splitter[0]));
			publishProgress(image);
			return null;
	      
		}

		@Override
		protected void onPostExecute(Void result){
			dialog.dismiss();
			img.setImageDrawable(image);
		}


	}

}
