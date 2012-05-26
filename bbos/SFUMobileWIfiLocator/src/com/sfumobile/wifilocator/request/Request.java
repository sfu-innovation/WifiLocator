package com.sfumobile.wifilocator.request;

import java.util.Enumeration;
import java.util.Hashtable;

import org.json.me.JSONException;
import org.json.me.JSONObject;

public abstract class Request {
	private Hashtable _properties;
	protected int _type;
	private Hashtable _errors;
	
	public Request(int type ){
		_properties = new Hashtable();
		_errors = new Hashtable();
		_type = type;
	}
	
	public void addError( String key, Object val ){
	   _errors.put( key, val);
	}
	
	protected int getType(){
		return _type;
	}
	
	
	public void setProperty( String key, String value){
		_properties.put(key, value);
	}
	
	public String getPayload(){
		
		if ( _properties.isEmpty()){
			return null;
		}
		
		JSONObject obj = new JSONObject();
		
		Enumeration e = _properties.keys();
		String key = null;
		while (e.hasMoreElements()){
			key = (String)e.nextElement();
			try {
				obj.append(key, (String)obj.get(key));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return obj.toString();
	}
	
	public abstract String getURL();
	
	
}
