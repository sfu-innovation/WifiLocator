package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

public final class FriendListObject {
	 private static FriendListObject instance;
	 private ArrayList<JSONObject> fl;
	 private FriendListObject(){
		 
	 }
	 
	 public static FriendListObject getInstance(){
		 if (instance==null){
			 instance = new FriendListObject();
		 } 
		 return instance;
	 }
	 
	 public void set_data(ArrayList<JSONObject> list) {
		 fl = list;
	 }
	 
	 
	 public ArrayList<JSONObject> get_data(){
		 return fl;
	 }
	 
}
