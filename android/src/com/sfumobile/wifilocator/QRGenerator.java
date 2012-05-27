package com.sfumobile.wifilocator;

import com.google.zxing.WriterException;
import com.sfumobile.wifilocator.QRCodeEncoder;

import android.graphics.Bitmap;

public class QRGenerator{
	
	public static Bitmap generateQR(String text){

		
		QRCodeEncoder encoder;
		Bitmap bitmap = null;
		try {
			encoder = new QRCodeEncoder(400, text);
			bitmap = encoder.encodeAsBitmap();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	
}
