package com.sfumobile.wifilocator.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpGET {
	public static JSONObject connect (String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		JSONObject json = new JSONObject();
		
		try{
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity!=null) {
				InputStream instream = entity.getContent();
				String res = StreamToString(instream);
				
				json = new JSONObject(res);
			}
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
		return json;
	}
	
	public static String StreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try{
			while ((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try{
				is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return sb.toString();
	}

}