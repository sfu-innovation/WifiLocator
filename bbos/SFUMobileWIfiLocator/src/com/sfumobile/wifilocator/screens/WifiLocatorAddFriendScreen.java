package com.sfumobile.wifilocator.screens;

import java.util.Vector;

import com.sfumobile.wifilocator.entities.WifiLocatorFriendship;
import com.sfumobile.wifilocator.entities.WifiLocatorUser;
import com.sfumobile.wifilocator.request.FriendshipConfirmRequest;
import com.sfumobile.wifilocator.request.FriendshipRequest;
import com.sfumobile.wifilocator.request.FriendshipRequestRetrieval;
import com.sfumobile.wifilocator.request.ImageRequest;
import com.sfumobile.wifilocator.request.RequestDelegateScreen;
import com.sfumobile.wifilocator.request.RequestPackage;
import com.sfumobile.wifilocator.request.SingleRequestLauncher;
import com.sfumobile.wifilocator.response.FriendshipConfirmResponse;
import com.sfumobile.wifilocator.response.FriendshipResponse;
import com.sfumobile.wifilocator.response.FriendshipRetrievalResponse;
import com.sfumobile.wifilocator.types.RequestTypes;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;

public class WifiLocatorAddFriendScreen extends RequestDelegateScreen implements FieldChangeListener {
	
	private static final int BRAD_PITT_ID = 40002;
	private static final String FUTURAMA_FRY_PIC = "http://i0.kym-cdn.com/photos/images/original/000/131/351/eb6.jpg?1307463786";
	private int friendshipId = 0;
	private ButtonField sendRequest, getRequest, confirmRequest, newImage;
	private ButtonField speedTest1, speedTest2, speedTest3;
	private LabelField  speedTest1Label, speedTest2Label, speedTest3Label;
	private BitmapField _bitmapField, testBitmapField, testBitmapField2;
	
	EncodedImage testImage;
	
	public WifiLocatorAddFriendScreen(){
		sendRequest = new ButtonField("Send Request");
		add(sendRequest);
		sendRequest.setChangeListener( this );
		
		getRequest = new ButtonField("Get Requests");
		add(getRequest);
		getRequest.setChangeListener( this );
		
		confirmRequest = new ButtonField("Confirm Request");
		add(confirmRequest);
		confirmRequest.setChangeListener( this );
	
		newImage = new ButtonField("Show Image");
		add( newImage );
		newImage.setChangeListener( this );
		
		speedTest1 = new ButtonField("Attempt 1");
		speedTest1.setChangeListener( this );
		add( speedTest1 );
		
		testImage = EncodedImage.getEncodedImageResource("eb6.png");
		
		speedTest2 = new ButtonField("Attempt 2");
		speedTest2.setChangeListener( this );
		add( speedTest2 );
		
		
		
		_bitmapField = new BitmapField();
		   add(_bitmapField);
		   
		   testBitmapField = new BitmapField();
		   add( testBitmapField);
	}

	
	public void handleStringValue(int type, String val) {
		switch ( type ) {
		case RequestTypes.INIT_FRIENDSHIP:
			FriendshipResponse step1 = new FriendshipResponse(val);
			String value = (String)step1.handleResponse();
			LabelField label1 = new LabelField("Step1 : "+value);
			add(label1);
			break;
		case RequestTypes.GET_FRIENDSHIP_REQUESTS :
			FriendshipRetrievalResponse step2 = new FriendshipRetrievalResponse( val );
			Vector value2 = (Vector)step2.handleResponse();
			int length = value2.size();
			for(int i = 0; i< length; i++){
				WifiLocatorFriendship friendship = (WifiLocatorFriendship)value2.elementAt(i);
				LabelField labelField2 = new LabelField("id : "+friendship.getID() + " Friend : "+friendship.getFriendName());
				add(labelField2);
				friendshipId = friendship.getID();
			}
			
			break;
		case RequestTypes.CONFIRM_FRIENDSHIP_REQUEST:
			FriendshipConfirmResponse step3 = new FriendshipConfirmResponse( val );
			String value3 = (String)step3.handleResponse();
			LabelField labelField3 = new LabelField("Status : "+value3);
			add(labelField3);
			break;
		}
		
	}

	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}

	public void handleError(int type, int errorCode, Object errorString) {
		// TODO Auto-generated method stub
		
	}

	public void fieldChanged(Field field, int context) {
		if ( field == sendRequest ) {
			FriendshipRequest req = new FriendshipRequest( BRAD_PITT_ID,
					WifiLocatorUser.getInstance().getID() );
			
			RequestPackage reqPack = new RequestPackage(this , req);
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest( reqPack );
		}
		else if ( field == getRequest ){
			FriendshipRequestRetrieval req = 
				new FriendshipRequestRetrieval(WifiLocatorUser.getInstance().getID());
			
			RequestPackage reqPack = new RequestPackage(this , req);
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest( reqPack );
		}
		else if ( field == confirmRequest ) {
			//friendshipId is set in the response for the getRequest.
			// if not friendshipId was set you will try to confirm with 0. 
			
			FriendshipConfirmRequest req = new FriendshipConfirmRequest(friendshipId);
			RequestPackage reqPack = new RequestPackage(this , req);
			SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
			launcher.sendRequest( reqPack );
			
		}
		else if ( field == newImage ) {
		//	final RequestDelegateScreen temp = this;
		//	new Thread(){
		///		public void run(){
			speedTest1Label = new LabelField("Starting image");
			add(speedTest1Label);
					ImageRequest req = new ImageRequest(FUTURAMA_FRY_PIC);
					RequestPackage reqPack = new RequestPackage( this , req );
					SingleRequestLauncher launcher = SingleRequestLauncher.getInstance();
					launcher.sendRequest( reqPack );
					speedTest2Label = new LabelField("Request Sent");
					add(speedTest2Label);
		//		}
		//	}.start();
			
		}
		else if ( field == speedTest1 ){
			testBitmapField.setImage( testImage );
		}
		else if ( field == speedTest2 ){
			testBitmapField2 = new BitmapField();
			testBitmapField2.setImage( testImage );
		}
		else if ( field == speedTest3 ){
			
		}
	}
	

	public void foo(Bitmap haiBMP){
		speedTest3Label = new LabelField("Image REturned");
		add(speedTest3Label);
		_bitmapField.setBitmap(haiBMP);
	}
	public void handleImageDataValue(final int type, final byte[] data) {
		if ( type == RequestTypes.IMAGE_REQUEST){
		
			UiApplication.getUiApplication().invokeLater(	new Thread(){
				public void run(){
							EncodedImage hai = EncodedImage.createEncodedImage(data, 0, data.length);
							Bitmap haiBMP = hai.getBitmap();
							foo( haiBMP);
					
				}
			}
			);
			
			
		}
	}
}
