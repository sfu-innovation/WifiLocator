package com.sfumobile.wifilocator;

import java.util.ArrayList;

import org.json.JSONObject;

public final class PendingListObject {
	 private static PendingListObject instance;
	 private ArrayList<JSONObject> fl = null;
	 private PendingListObject(){
		 
	 }
	 
	 public static PendingListObject getInstance(){
		 if (instance==null){
			 instance = new PendingListObject();
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
