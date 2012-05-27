package com.sfumobile.wifilocator;

import java.nio.ByteBuffer;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class QRGenerator{
	
	public static Bitmap generateQR(String text){
		QRCode qr = new QRCode();
		
		try {
			Encoder.encode(text, ErrorCorrectionLevel.L, qr);
		} catch (WriterException e1) {
			e1.printStackTrace();
		}
		
		ByteMatrix br = qr.getMatrix();
		
		ByteBuffer bb = ByteBuffer.allocate(br.getHeight()*br.getWidth());
		for(int y = 0; y < br.getHeight(); y++){
			for(int x = 0; x < br.getWidth(); x++){
				bb.put(br.get(x, y));
			}
		}
		//bb.array();
		//Bitmap bitmap = Bitmap.createBitmap(br.getWidth() * 64, br.getHeight() * 64, Bitmap.Config.RGB_565);
		//bitmap.copyPixelsFromBuffer(bb);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bb.array(), 0, bb.array().length);
		return bitmap;
	}
	
}
