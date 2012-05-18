package mypackage;

import net.rim.device.api.system.Clipboard;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ZoneAPScreen extends MainScreen implements FieldChangeListener {
	
	private BasicEditField _zoneField, _apField, resultField;
	private VerticalFieldManager _vfm;
	private HorizontalFieldManager _hfm;
	private ButtonField _addAP, _removeAP, _cancelAction;
	private LocationDataContext _context;
	public ZoneAPScreen(){
		setTitle("Edit SFU GeoLocation Zones");
	}
	
	public void init( LocationDataContext context ){
		_context = context;
		_vfm = new VerticalFieldManager();
		
		_zoneField = new BasicEditField( " Zone : ", "");
		_apField = new BasicEditField(" AP MAC Address : ", "");
		_apField.setText( (String)Clipboard.getClipboard().get());
		resultField = new BasicEditField(" Result : ", "");
		
		_hfm = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
		
		_addAP = new ButtonField( "Add");
		_addAP.setChangeListener( this );
		
		_removeAP = new ButtonField("Remove");
		_removeAP.setChangeListener( this );
		
		_cancelAction = new ButtonField("Cancel");
		_cancelAction.setChangeListener( this );
		
		_vfm.add( _zoneField );
		_vfm.add( _apField );
		
		_hfm.add( _addAP );
		_hfm.add ( _removeAP );
		_hfm.add ( _cancelAction );
		_vfm.add( _hfm );
		_vfm.add( resultField );
		add( _vfm );
	}
	
	private void addAP(){
		_context.add( _zoneField.getText(), _apField.getText() );
		_context.commit();
		resultField.setText(" [Added] - "+_zoneField.getText() + " - " + _apField.getText() );
	}
	
	private void removeAP(){
		_context.remove( _zoneField.getText(), _apField.getText() );
		_context.commit();
		resultField.setText(" [Removed] - "+_zoneField.getText() + " - " + _apField.getText() );
	}
	
	private void back(){
		 this.close();
	}

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		if ( field == _addAP ) {
			addAP();
		}
		else if ( field == _removeAP ) {
			removeAP();
		}
		else if ( field == _cancelAction ) {
			back();
		}
	}
}
