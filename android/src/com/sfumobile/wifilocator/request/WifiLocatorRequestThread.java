package com.sfumobile.wifilocator.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

public class WifiLocatorRequestThread extends Thread {
	private String _url;
	private RequestDelegateActivity _delegate;
	private int     _type;
	private static final int CONNECTION_FAILED = 1;
	private String _data;
	private byte[] data;
	private Handler _handler;
	public WifiLocatorRequestThread( int type,  String url, String data, RequestDelegateActivity delegate, Handler handler) {
		_url = url;
		_delegate = delegate;
		_type  = type;
		_data = data;
		_handler = handler;
	}
	public void run(){

        String url = _url;
        String data = postRequest(_data, url);
        _handler.post(new Runnable() {
            public void run() {
                _delegate.handleByteValue( _type, _data );
            }
        });
    }
	
	public static String postRequest(String body, String url){
		JSONObject _body = null;
		HttpClient httpClient   = new DefaultHttpClient();
		try {
			_body = new JSONObject(body);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		HttpPost post           = new HttpPost(url);
		HttpResponse response   = null;
		JSONObject jsonResponse = null;

		post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
		try {
			post.setEntity(new StringEntity(_body.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try {
			response= httpClient.execute(post);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String json = r.readLine();
			try {
				jsonResponse = new JSONObject(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonResponse.toString();
	}
}
