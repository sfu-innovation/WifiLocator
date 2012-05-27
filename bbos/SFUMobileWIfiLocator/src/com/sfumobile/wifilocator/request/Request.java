package com.sfumobile.wifilocator.request;

import java.util.Enumeration;
import java.util.Hashtable;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sfumobile.wifilocator.types.RequestTypes;

public abstract class Request {
	private Hashtable _properties;
	private Hashtable _propertiesTypes;
	protected int _type;
	private Hashtable _errors;
	
	public Request(int type ){
		_properties = new Hashtable();
		_propertiesTypes = new Hashtable();
		_errors = new Hashtable();
		_type = type;
	}
	
	public void addError( String key, Object val ){
	   _errors.put( key, val);
	}
	
	protected int getType(){
		return _type;
	}
	
	
	
	public void setProperty( String key, String value, String type){
		_properties.put(key, value);
		_propertiesTypes.put(key, type);
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
				if ( _propertiesTypes.containsKey( key )){
					String type = (String)_propertiesTypes.get( key );
					if ( type.equals( RequestTypes.INT_TYPE)){
						obj.put(key, Integer.parseInt((String)_properties.get(key)));
					}
					else {
						obj.put(key, (String)_properties.get(key));
					}
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return obj.toString();
	}
	
	public abstract String getURL();
	
	
}
