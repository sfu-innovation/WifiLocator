package mypackage;

import net.rim.device.api.system.Clipboard;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;

import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */


 
public final class MyScreen extends MainScreen implements FieldChangeListener
{
	private BasicEditField _label, _currentSignalLevel , _currentSSID, _currentBSSID;
	private VerticalFieldManager _vManager;
	private ButtonField _refreshButton, _refreshWLANButton, _bssidToClipBoard, _zones, _viewZones;
	
	private LocationDataContext _dataContext;
	
	private ZonesView _viewScreen;
	
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("[SFU Mobile] Geo Location");
    }
    
    // need to get the list of data points on this screen
    public void init( ){
    	_dataContext = new LocationDataContext();
        _label = new BasicEditField( "Currently connected zone : ", "N/A", 1024, Field.NON_FOCUSABLE);
        
        
        _currentSSID = new BasicEditField( "Currently connected zone : ", "N/A", 1024, Field.NON_FOCUSABLE);
        _currentBSSID = new BasicEditField( "Currently BSSID : ", "N/A", 1024, Field.NON_FOCUSABLE);
        _currentSignalLevel = new BasicEditField( "Current signal strength : ", "N/A", 1024, Field.NON_FOCUSABLE);
        //_sanityCheck = new BasicEditField("Sanity Zone Check : ", "", 1024, Field.NON_FOCUSABLE);
        
        _vManager = new VerticalFieldManager();
        
        
        _refreshButton = new ButtonField( "Refresh", Field.USE_ALL_WIDTH );
        _refreshButton.setChangeListener( this );
        
        _refreshWLANButton = new ButtonField( "Refresh WLAN Stats", Field.USE_ALL_WIDTH );
        _refreshWLANButton.setChangeListener( this );
        
        _bssidToClipBoard = new ButtonField(" Add BSSID To Clipboard", Field.USE_ALL_WIDTH );
        _bssidToClipBoard.setChangeListener( this );
        
        
        
        _viewZones = new ButtonField("View Zones", Field.USE_ALL_WIDTH );
        _viewZones.setChangeListener( this );
        _vManager.add( _label );
        _vManager.add( _refreshButton );
        
        _vManager.add( _currentSSID );
        _vManager.add( _currentBSSID );
        _vManager.add( _currentSignalLevel );
        
        HorizontalFieldManager hfm = new HorizontalFieldManager();
        hfm.add ( _refreshWLANButton);
        hfm.add( _bssidToClipBoard );
        _vManager.add( hfm );
        _vManager.add( _viewZones );
        this.add( _vManager );
       // addDummyData();
        
    }

  
	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		
		if ( field == _refreshButton ) {
			checkZone();
		}
		else if ( field == _refreshWLANButton ){
			refreshWLAN();
		}
	
		else if ( field == _viewZones ){
			_viewScreen = new ZonesView();
			_viewScreen.init( _dataContext );
			((UiApplication)UiApplication.getApplication()).pushScreen( _viewScreen );
		}
		else if ( field == _bssidToClipBoard ){
			Clipboard.getClipboard().put(_currentBSSID.getText());
		}
	}
	
	public boolean onClose(){
		//removeDummyData();
		_dataContext.commit();
		System.out.println("[SFUMOBILE] - Closing and commiting ");
		return true;
		
	}
	
	public String checkZone(){
		String zoneStatus = "N/A";
		String bssid = WLANContext.getCurrentLowerCaseBSSID();
		String temp = _dataContext.search( bssid ); 
		if ( temp != null ) {
			zoneStatus = temp;
		}
		_label.setText( zoneStatus );
		return zoneStatus;
	}
	
	public void refreshWLAN(){
	//	if ( WLANContext.isConnected()){
			_currentSSID.setText( WLANContext.getCurrentSSID());
			_currentBSSID.setText( WLANContext.getCurrentLowerCaseBSSID());
			_currentSignalLevel.setText( WLANContext.getCurrentSignalStrength());
			checkZone();
		}
	//	else {
		//	_label.setText( "Not Connected" );
	//		_currentSSID.setText("Not Connected");
	//		_currentBSSID.setText("Not Connected");
	//	    _currentSignalLevel.setText("Not Connected");
	//	}
	}
    
	
