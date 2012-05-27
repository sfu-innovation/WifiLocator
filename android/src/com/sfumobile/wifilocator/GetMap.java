package com.sfumobile.wifilocator;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

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
	private String host = "http://wifi-location.appspot.com";
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
		String map_path;
		String url = host+"/getmap/"+loc;
		JSONObject map_addr = HttpGET.connect(url);
		try{
			map_path = host+map_addr.getString("map_name");
		} catch (JSONException jse) {
			map_path = null;
		}
		//String url = "http://wifi-location.appspot.com/media/zonemaps/map2.png";
		return map_path;
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
		//	String[] splitter = zone.split(",");
			Log.d("URL", gen_URL(zone));
			image = getImage(gen_URL(zone));
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
