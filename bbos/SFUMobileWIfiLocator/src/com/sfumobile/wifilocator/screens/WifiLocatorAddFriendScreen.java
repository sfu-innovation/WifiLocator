package com.sfumobile.wifilocator.screens;


import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.sfumobile.wifilocator.entities.WifiLocatorData;
import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.screens.test.WifiLocatorMenuItems;

import net.rim.device.api.barcodelib.BarcodeBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class WifiLocatorAddFriendScreen extends MainScreen implements FieldChangeListener {
	private static final int BARCODE_WIDTH = 400;
	
	private HorizontalFieldManager _hfm;
	private ButtonField _scanButton, _pendingButton;
	private BitmapField _barcodeField;
	private QRCode _qrCode;
	
	private static final String QR_CODE_CONTENTS = "sfumobile.";
	public WifiLocatorAddFriendScreen(){
		setTitle("Add Friend");
		
		_barcodeField = new BitmapField(new Bitmap(BARCODE_WIDTH, BARCODE_WIDTH), Field.FIELD_HCENTER);
	//	_barcodeField.setBorder(BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2)));
		this.add(_barcodeField);
		
		_hfm = new HorizontalFieldManager( Field.USE_ALL_WIDTH);
		_scanButton = new ButtonField("Scan", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth()/2;
				}
		};
		_scanButton.setChangeListener( this );
		_hfm.add( _scanButton );
		
		_pendingButton = new ButtonField("Pending", Field.USE_ALL_WIDTH){
			public int getPreferredWidth() {
				return Display.getWidth()/2;
				}
		};
		_pendingButton.setChangeListener( this );
		_hfm.add(_pendingButton );
		
		setStatus(_hfm);

		addMenuItem(WifiLocatorMenuItems.AddFriendMenuItem());
	}
	
	protected void onUiEngineAttached( boolean attached ) {
		if ( attached ) {
			
		_qrCode = new QRCode();

		// This encodes the text with a low level (%7) of error correction
		try {
			Encoder.encode(QR_CODE_CONTENTS+WifiLocatorData.getInstance().getUser().getID(),
					ErrorCorrectionLevel.L,
					_qrCode);
		} catch (WriterException e) {
			System.out.println("[SFUMOBILE] - Unable to encode QR code");
			e.printStackTrace();
		}
		Bitmap bitmap = BarcodeBitmap.createBitmap(_qrCode.getMatrix(), BARCODE_WIDTH);

		_barcodeField.setBitmap(bitmap);
		}
	}

	public boolean onClose(){
		close();
		return true;
	}
	public void fieldChanged(Field field, int context) {
		if ( field == _scanButton ){
			UiApplication.getUiApplication().pushScreen( 
					new WifiLocatorQRCodeViewerScreen());
		}
		else if ( field == _pendingButton ){
			UiApplication.getUiApplication().pushScreen( 
					new ViewPendingFriendshipsScreen());
		}
		
	}
}
