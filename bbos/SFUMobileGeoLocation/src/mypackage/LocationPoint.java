package mypackage;

public class LocationPoint {
	
	private String _zoneName, _address;

	public LocationPoint(){
		_zoneName = "";
		_address = "";
	}
	
	public LocationPoint( String zone, String address ){
		_zoneName = zone;
		_address = address;
	}
	
	public String getZone(){
		return _zoneName;
	}
	
	public String getAddress(){
		return _address;
	}
	
	public void setZone( String newZoneName){
		_zoneName = newZoneName;
	}
	
	public void setAddress(String newAddress){
		_address = newAddress;
	}
	
	public String toString(){
		return "\""+getZone()+ "\",\""+getAddress()+"\"";
	}
}
