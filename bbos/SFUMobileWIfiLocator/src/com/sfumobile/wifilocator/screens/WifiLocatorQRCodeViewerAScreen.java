package com.sfumobile.wifilocator.screens;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.media.MediaException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.sfumobile.wifilocator.request.FriendshipRequest;
import com.sfumobile.wifilocator.request.RequestDelegateScreen;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipResponse;
import com.sfumobile.wifilocator.utils.QRUtils;


import net.rim.device.api.amms.control.camera.ImageDecoder;
import net.rim.device.api.amms.control.camera.ImageDecoderListener;
import net.rim.device.api.amms.control.camera.ImageScanner;
import net.rim.device.api.barcodelib.BarcodeDecoder;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class WifiLocatorQRCodeViewerAScreen extends RequestDelegateScreen {
	ImageDecoderListener _imageDecoderListener;
	private ImageScanner _scanner;
	private FriendshipRequest _req;
	private RequestPackage    _package;
	private WifiLocatorQRCodeViewerAScreen _screen;
	
	public WifiLocatorQRCodeViewerAScreen(){
	}
	
    public boolean onClose(){
    	closeScanner();
    	return super.onClose();
    }
    
    protected void onUiEngineAttached( boolean attached ){
    	if (attached ) {
    	Vector formats = new Vector();
    	_screen = this;
		Hashtable hints = new Hashtable();
		formats.addElement(BarcodeFormat.QR_CODE);
		hints.put(DecodeHintType.POSSIBLE_FORMATS, formats);
		_imageDecoderListener = new ImageDecoderListener()
	    {

	        /**
	         * @see net.rim.device.api.amms.control.camera.ImageDecoderListener#imageDecoded(Object)
	         */
	        public void imageDecoded(final Object decoded)
	        {
	            UiApplication.getUiApplication().invokeLater(new Runnable()
	            {
	                public void run()
	                {
	                	closeScanner();
	                	Result res = (Result) decoded;
	                	int alexuserid = 27001;
	                	int friendid = Integer.parseInt(QRUtils.getTargetContact(res.getText()));
	                    _req = new FriendshipRequest( alexuserid, friendid );
	                    _package = new RequestPackage( _screen, _req );
	                    SingleRequestLauncher.getInstance().sendRequest(_package);
	                	inform((Result)decoded);
	                    
	                }
	            });
	        }
	    };
    	Field cameraField = initializeCamera(new BarcodeDecoder(hints), _imageDecoderListener);
		
		
	        if(cameraField != null)
	        {
	        	this.add(cameraField);
	        }
    	}
	
    }
	private Field initializeCamera(ImageDecoder decoder, ImageDecoderListener listener)
	{
	    try
	    {
	        if(_scanner != null)
	        {
	        	closeScanner();
	        }
	        
	        _scanner = new ImageScanner(decoder, listener);
	        _scanner.getVideoControl().setDisplayFullScreen(true);
	        _scanner.startScan();
	        return _scanner.getViewfinder();
	    }
	    catch(Exception e)
	    {
	    }
	    return null;
	}

	//close the scanner
	public void closeScanner()
	{  
	if ( _scanner.getPlayer() != null) {
	  try {
	    _scanner.getPlayer().stop();
	  } catch (MediaException e) {
	      //handle exception
		  System.out.println("[SFUMOBILE] Error stopping the QR code reader");
	  }
	  //de allocate and close player
	  _scanner.getPlayer().deallocate();
	  _scanner.getPlayer().close();
	  System.out.println("[SFUMOBILE] Successfully closed QR code reader");
	}
	}

	public void inform(Result result)
	{
	    // Close the viewfinder first
	    UiApplication.getUiApplication().popScreen();
	
	 /*   MainScreen resultsScreen = new MainScreen();
	
	    LabelField text = new LabelField("Barcode Text: " + result.getText());
	    text.setPadding(4, 4, 4, 4);
	
	    LabelField type = new LabelField("Barcode Type: " + result.getBarcodeFormat().toString());
	    type.setPadding(4, 4, 4, 4);
	
	    TransitionContext context = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	    context.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.KIND_OUT);
	
	    Ui.getUiEngineInstance().setTransition(null, resultsScreen, UiEngineInstance.TRIGGER_PUSH, context);
	
	    resultsScreen.add(text);
	    resultsScreen.add(type);
	
	    UiApplication.getUiApplication().pushScreen(resultsScreen);*/
	}

	public void handleStringValue(int type, String val) {
		FriendshipResponse response = new FriendshipResponse( val );
		System.out.println(response.handleResponse());
		
	}

	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	public void handleImageDataValue(int type, byte[] data) {
		// TODO Auto-generated method stub
		
	}




}
