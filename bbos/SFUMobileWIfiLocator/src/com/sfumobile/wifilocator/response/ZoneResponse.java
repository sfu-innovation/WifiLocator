package com.sfumobile.wifilocator.response;

import org.json.me.JSONException;

import com.sfumobile.wifilocator.entities.WifiLocatorUser;

public class ZoneResponse extends ResponseHandler {

	public ZoneResponse(String data){
		super(data);
	}
	public Object handleResponse() {
		String temp = null;
		try {
			WifiLocatorUser user = WifiLocatorUser.getInstance();
			user.setLastUpdate( _data.getString("last_update"));
			user.setMap( _data.getString("map_name"));
			temp =  _data.getString("zone_name");
			user.setZone( temp );
			
			
		} catch (JSONException e) {
			System.out.println("[SFUMOBILE] - unable to set user in ZoneResponse-handleResponse ");
			e.printStackTrace();
		}
		return temp;
	}
}
