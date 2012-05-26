package com.sfumobile.wifilocator.screens;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import net.rim.device.api.barcodelib.BarcodeBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BorderFactory;

public class WifiLocatorQRCodeScreen extends MainScreen {
	private static final int BARCODE_WIDTH = 480;
	public WifiLocatorQRCodeScreen(){
		BitmapField _barcodeField = new BitmapField(new Bitmap(BARCODE_WIDTH, BARCODE_WIDTH), Field.FIELD_HCENTER);
		_barcodeField.setBorder(BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2)));
		this.add(_barcodeField);

		// Create and add the field to store the barcode contents
		JSONObject temp = new JSONObject();
		try {
			//temp.put("picture", "http://upload.wikimedia.org/wikipedia/en/thumb/1/13/BlackBerry_Bold_9900.jpg/250px-BlackBerry_Bold_9900.jpg");
			temp.put("type", "friendship");
			temp.put("userid", "27001");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EditField _barcodeTextField = new EditField("Barcode text: ", temp.toString());
		this.add(_barcodeTextField);
		
		QRCode qrCode = new QRCode();

		// This encodes the text with a low level (%7) of error correction
		try {
			Encoder.encode(_barcodeTextField.getText(), ErrorCorrectionLevel.L, qrCode);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// From there we get the actual data matrix and convert it into a
		// bitmap
		ByteMatrix barcode = qrCode.getMatrix();
		Bitmap bitmap = BarcodeBitmap.createBitmap(barcode, BARCODE_WIDTH);

		_barcodeField.setBitmap(bitmap);

	}
}
