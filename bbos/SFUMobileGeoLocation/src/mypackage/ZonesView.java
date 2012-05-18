package mypackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.picker.FilePicker;

public class ZonesView extends MainScreen implements FieldChangeListener {
	
	private LocationDataContext _context; 
	private Object[] _list;
	private ObjectListField _uiList;
	private ButtonField _saveToFilebutton, _loadFromFileButton , addPointButton, removePointButton;
	private ZoneAPScreen _editScreen;
	public ZonesView(){
		
		setTitle("View Zones");
	}
	
	
	public void init( LocationDataContext context ){
		
		_context = context;
		_list = context.getPoints();
		_uiList = new ObjectListField();
		
		_uiList.set( _list );
		
		
		HorizontalFieldManager _manager = new HorizontalFieldManager();
		
		addPointButton = new ButtonField("Add", Field.USE_ALL_WIDTH );
		addPointButton.setChangeListener( this );
		_manager.add( addPointButton );
		
		removePointButton = new ButtonField("Remove", Field.USE_ALL_WIDTH );
		removePointButton.setChangeListener( this );
		_manager.add( removePointButton );
		
		_saveToFilebutton = new ButtonField( "Save to file", Field.USE_ALL_WIDTH );
		_saveToFilebutton.setChangeListener( this );
		_manager.add( _saveToFilebutton );
		
		_loadFromFileButton = new ButtonField( "Load from file", Field.USE_ALL_WIDTH );
		_loadFromFileButton.setChangeListener( this );
		_manager.add( _loadFromFileButton );
		
		add( _manager );
		
		add( _uiList );
	}
	//"file:///store/home/user/data.csv"
	private String readTextFile( String fileLocation ) {
		  String result = null;
		  FileConnection fconn = null;
		  DataInputStream is = null;
		  try {
		   fconn = (FileConnection) Connector.open( fileLocation , Connector.READ_WRITE);
		   is = fconn.openDataInputStream();
		   byte[] data = IOUtilities.streamToBytes(is);
		   result = new String(data);
		  } catch (IOException e) {
		   System.out.println(e.getMessage());
		  } finally {
		   try {
		    if (null != is)

		     is.close();
		    if (null != fconn)
		     fconn.close();
		   } catch (IOException e) {
		    System.out.println(e.getMessage());
		   }
		  }
		  return result;
		 }
	
	private void refreshList(){
		_list = _context.getPoints();
		_uiList.set( _list );
	}
	public void fieldChanged(Field field, int context) {
		
		if ( field == _loadFromFileButton ){
			FilePicker filePicker = FilePicker.getInstance();
			filePicker.setFilter("csv");
			String s = filePicker.show();
			_context.addBulk(readTextFile(s));
			refreshList();
		}
		else if ( field == _saveToFilebutton ) {
			
			LocationPoint temp;
			try 
			    {
			      FileConnection fc = (FileConnection)Connector.open("file:///store/home/user/sfumobilegeolocation.txt");
			      // If no exception is thrown, then the URI is valid, but the file may or may not exist.
			      if (!fc.exists())
			      {
			          fc.create();  // create the file if it doesn't exist
			      }
			      OutputStream outStream = fc.openOutputStream(); 
			      for ( int i = 0; i < _list.length; i ++){
			    	  temp = (LocationPoint)_list[i];
			    	  outStream.write( (temp.toString() + "\n").getBytes());
			      }
			     
			      outStream.close();
			      fc.close();
			     }
			     catch (IOException ioe) 
			     {
			        System.out.println(ioe.getMessage() );
			     }
		}
		else if ( field == addPointButton ) {
			_editScreen = new ZoneAPScreen();
			_editScreen.init( _context );
			((UiApplication)UiApplication.getApplication()).pushScreen( _editScreen );
			refreshList();
		}
		else if ( field == removePointButton ) {
			LocationPoint deletionPoint = (LocationPoint)_uiList.get( _uiList, _uiList.getSelectedIndex());
			_context.remove( deletionPoint.getZone(), deletionPoint.getAddress());
			refreshList();
		}
	}
		
	
}
