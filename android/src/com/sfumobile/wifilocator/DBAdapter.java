package com.sfumobile.wifilocator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "wifi_locator.db";
	private static final String DATABASE_PATH_NAME = "/data/data/com.sfumobile.wifilocator/databases/";
	private static final String TABLE_ACCESSPOINTS = "access_points";
	private static final int DATABASE_VERSION = 1;
	private final Context context;
	
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = ctx;

	}
	
	public void createDatabase(){
	
		boolean dbExists = dbExists();
		
		if(!dbExists){
			db = this.getReadableDatabase();
			
			createDBFromFile();
		}
	}
	
	private boolean dbExists(){
		try{
			String db_path = DATABASE_PATH_NAME + DATABASE_NAME;
			db = SQLiteDatabase.openDatabase(db_path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}
		catch(SQLException e){
			Log.d("DBAdapter", "SQL Error: " + e.getLocalizedMessage());
		}
		if(db != null){
			db.close();
		}
		return db != null ? true : false;
	}
	
	public void createDBFromFile(){
		InputStream inputStream = null;
		OutputStream outStream = null;
		String db_path = DATABASE_PATH_NAME + DATABASE_NAME;
		try{
			inputStream = context.getAssets().open(DATABASE_NAME);
			outStream = new FileOutputStream(db_path);
			
			byte buffer[] = new byte[1024];
			int length;
			while((length = inputStream.read(buffer)) >0){
				outStream.write(buffer, 0, length);
			}
			outStream.flush();
			outStream.close();
			inputStream.close();
		}
		catch(IOException e){
			Log.d("DBAdapter", "IO Error: " + e.getLocalizedMessage());
		}
	}
	
	public Cursor getAP(){
		Cursor c;
		try{
			c = db.query(TABLE_ACCESSPOINTS, new String[]{"bssid","zone"}, null, null, null, null, null);
		}
		catch(SQLException e){
			Log.d("DBAdapter", "CursorError: " + e.getLocalizedMessage());
			c = null;
		}
		return c;
	}
	
	public String getZone(String bssid){
		String bssid_upper = bssid.toUpperCase();
		Cursor c = db.query(TABLE_ACCESSPOINTS, new String[]{"bssid","zone"}, "bssid in ('" + bssid + "', '" + bssid_upper + "')", null, null, null, null);
		if(c.getCount()<1){
			return "-1";
		}
		else{
			c.moveToFirst();
			String zone = c.getString(c.getColumnIndex("zone"));
			return zone;
		}
	}
		
	public void close(){
		try{
			db.close();
		}
		catch(SQLException e){
			Log.d("DBAdapter", "SQL Error: " + e.getLocalizedMessage());
		}
	}
	
	public void insertAP(String bssid, String zone){
		
		ContentValues args = new ContentValues();
		args.put("bssid", bssid);
		args.put("zone", zone);
		db.insert(TABLE_ACCESSPOINTS, null, args);
		
	}

    public void openDataBase() throws SQLException{
    	 
    	//Open the database
        String myPath = DATABASE_PATH_NAME + DATABASE_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
