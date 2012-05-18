package mypackage;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.ui.component.Dialog;

public class LocationDataContext {
	
	private static final long SFU_MOBILE_ID = 0x5abe900fd641160cL;
	private PersistentObject persistentObject;
	private Hashtable settingsTable;
	
	
	public LocationDataContext(){
		persistentObject = PersistentStore.getPersistentObject( SFU_MOBILE_ID );
		
		synchronized ( persistentObject ) {
			settingsTable = (Hashtable) persistentObject.getContents();
			if ( null == settingsTable ) {
				settingsTable = new Hashtable();
				persistentObject.setContents( settingsTable );
				persistentObject.commit();
			}
		}
	}
	
	public String search( String val ) {
		  String zoneFound = null;
		  for (Enumeration e = settingsTable.keys() ; e.hasMoreElements() ;) {
		       String s = (String)e.nextElement();
		       Vector value = (Vector)(settingsTable.get( s ));
		       if ( value.indexOf( val ) != -1 ){
		    	   zoneFound = s;
		       }
		  }
		return zoneFound;
	}
	
	public Object[] getPoints(){
		int index = 0;
		Object[] list = new Object[size()];
		 for (Enumeration e = settingsTable.keys() ; e.hasMoreElements() ;) {
		       String zoneName = (String)e.nextElement();
		       Vector value = (Vector)(settingsTable.get( zoneName ));
		       for( Enumeration vectorValues = value.elements(); vectorValues.hasMoreElements();){
		    	   String APValue = (String)vectorValues.nextElement();
		    	   list[index++] = new LocationPoint(zoneName, APValue);
		       }
		  }
		return list;
	}
	public int size(){
		int size = 0;
		 for (Enumeration e = settingsTable.keys() ; e.hasMoreElements() ;) {
		       String s = (String)e.nextElement();
		       Vector value = (Vector)(settingsTable.get( s ));
		       size += value.size();
		  }
		 return size;
	}
	public Vector get( String key ) {
		return (Vector)settingsTable.get( key );
	}
	
	public void remove( String key, String value ){
		Object[] choices = new Object[2];
		String dialogString;
		choices[0] = "Delete";
		choices[1] = "Cancel";
		dialogString = "Please confirm that would like to remove\n AP: "+value +"\nfrom\nZone: "+key;
		int c = Dialog.ask(dialogString, choices , 0);
		switch( c ) {
		case 0:
			removeInternal( key , value );
			break;
		default:
			break;
		}
	}
	private void removeInternal ( String key, String value ){
		Vector temp = null;
		if ( null != settingsTable && settingsTable.containsKey ( key )){
			if ( ( temp = (Vector)settingsTable.get( key )) != null ) {
				temp.removeElement( value );
			}
			if ( temp.size() == 0){
				settingsTable.remove( key );
			}
		}
	}
	/*
	 * if the key exists
	 *     if the vector doesnt exist
	 *          create a new vector and add the new value to the vector
	 *          add the vector to the hashtable under the key
	 *     if the vector does exist
	 *          get the vector
	 *          if the element doesnt exist in it
	 *              add the element to the vector
	 *              put the vector back into the hashtable under the key
	 * else 
	 *      create a new vector
	 *      add the element to the vector
	 *      put with new key and new vector
	 *      
	 * What if value exists under another zone? Should we rescan every zone?
	 * */
	
	public void addBulk( String s ){
		int a,b,c,d;
		int temp = 0;
		String tempString = null;
		boolean firstLine = true;
		int length = s.length();
		for ( int i = 0; i < length; i++ ){
			if ( firstLine ){
				i = s.indexOf('\n', 0);
				firstLine = false;
				continue;
			}
			temp = s.indexOf('\n', i);
			tempString = s.substring( i, temp );
			a = tempString.indexOf(',')-1;
			b = 1;
			c = tempString.indexOf(',')+2;
			d = tempString.length() - 2;
			addInternal( tempString.substring( c, d ), tempString.substring( b, a));
			//System.out.println("[MAC] - "+ tempString.substring(b, a));
			//System.out.println("[ZONE] - " + tempString.substring( c, d ));
			i = temp;
		}
	}
	
	private void addInternal( String key, String value ){
		Vector v = null;
		if ( settingsTable.containsKey( key ) ){
			if ( settingsTable.get( key ) == null ){
				v = new Vector();
				v.addElement( value );
				settingsTable.put( key, v );
			}
			else {
				v = (Vector) settingsTable.get( key ) ;
				if ( v.indexOf( value ) == -1 ){
					v.addElement( value );
					settingsTable.put( key, v );
				}
			}
		}
		else {
			v = new Vector();
			v.addElement( value);
			settingsTable.put( key, v );
		}
	}
	public void add ( String key, String value ) {
		String APAlreadyExists = sanityCheck( value );
		Object[] choices = new Object[2];
		String dialogString;
		if ( key.equals("")){
			Dialog.alert("The zone must have a name");
		}
		else if ( value.equals("")){
			Dialog.alert("The MAC must not be empty");
		}
		else if ( null != APAlreadyExists ){
			choices[0] = "Swap Zones";//
			choices[1] = "Cancel";//{"Swap Zones", "Cancel"};
			dialogString = "The AP : "+value+" is already associated with Zone : " +APAlreadyExists;
			int c = Dialog.ask(dialogString, choices , 0);
			switch( c ) {
			case 0:
				removeInternal( APAlreadyExists , value );
				addInternal( key, value ); 
				break;
				
			default:
				break;
			}
		}
		// do the dialog here
		// if AP Already exists alert the user that it already exists and they can a) remove it and add to new zone b) cancel
		else {
			choices[0] = "Save";
			choices[1] = "Cancel";
			dialogString = "Confirm you would like to associate \n AP : "+value+"  \nwith\nZone : " +key;
			int c  = Dialog.ask(dialogString, choices , 0);
			switch( c ) {
			case 0:
				addInternal( key, value ); 
				break;
				
			default:
				break;
			}
		}
		//other we should juts verify if they want to add it
		//eg. Please confirm that you would like to associate AP xxxxxxxx with Zone yyyyyyy  a) save b) cancel
	//	addInternal( key, value );
	}
	
    private String sanityCheck(String value){
    	String alreadyExistsInZone = null;
    	for ( Enumeration e = settingsTable.keys(); e.hasMoreElements() ; ){
    	    String s = (String) e.nextElement();
    	    Vector v = (Vector)(settingsTable.get( s ));
    	    if ( v != null ) {
    	    	for( Enumeration a = v.elements(); a.hasMoreElements(); ){
    	    		 String x = (String)a.nextElement();
    	    		 if ( x.equals( value )){
    	    			 alreadyExistsInZone = s;
    	    		 }
    	    	}
    	    }
    	}
		return alreadyExistsInZone;
	}
	
	public void commit() {
		persistentObject.setContents( settingsTable );
		persistentObject.commit();
	}
}
