package com.sfumobile.wifilocator.screens;

import com.sfumobile.wifilocator.request.RequestDelegate;
import com.sfumobile.wifilocator.request.RequestTypes;
import com.sfumobile.wifilocator.request.ZoneRequest;
import com.sfumobile.wifilocator.utils.BrowserFieldOAuthDialogWrapper;
import com.sfumobile.wifilocator.utils.JSONWifiLocatorParser;
import com.sfumobile.wifilocator.utils.TwitterConstants;
import com.sfumobile.wifilocator.utils.WLANContext;
import com.twitterapime.rest.Credential;
import com.twitterapime.rest.TweetER;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.Query;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDevice;
import com.twitterapime.search.Tweet;
import com.twitterapime.xauth.Token;
import com.twitterapime.xauth.ui.OAuthDialogListener;
import com.twitterapime.xauth.ui.OAuthDialogWrapper;

import net.rim.device.api.browser.field.RenderingOptions;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */


public final class WifiLocatorTwitterScreen extends RequestDelegate implements  OAuthDialogListener, FieldChangeListener
{
	private BasicEditField pinField;
	private ButtonField pinButton;
	private OAuthDialogWrapper  pageWrapper;
	private ObjectListField _tweets;
	private String zoneName;
	
	private final String CONSUMER_KEY = TwitterConstants.CONSUMER_KEY;
	private final String CONSUMER_SECRET = TwitterConstants.CONSUMER_SECRET;
	private final String CALLBACK_URL = TwitterConstants.CALLBACK_URL;
    /**
     * Creates a new WifiLocatorLoginScreen object
     */
	LabelField connectedLabel;
	ButtonField connectedButton;
	VerticalFieldManager m;
	
	HorizontalFieldManager hfm;
	ButtonField bf;
	BasicEditField tweetMessage;
	VerticalFieldManager vfm;
	
	
    public WifiLocatorTwitterScreen()
    {        
    	
    	addMenuItem( WifiLocatorMenuItems.pushFriendsMenuItem());
    	addMenuItem( new MenuItem( "Refresh" , 110, 10){
    		public void run(){
    			refreshTweets();
    		}
    	});
    	setTitle("");
    	m = new VerticalFieldManager();
    	BrowserField browserMngr = new BrowserField();
		RenderingOptions rendOptions = browserMngr.getRenderingOptions();
	//	RenderingOptions rendOptions = browserMngr.getRenderingSession().getRenderingOptions();
		rendOptions.setProperty(
			RenderingOptions.CORE_OPTIONS_GUID,
			RenderingOptions.SHOW_IMAGES_IN_HTML,
			false);
		//
		 m.add(browserMngr);
		
		pinField = new BasicEditField("Enter Pin", "");
		 m.add(pinField);
		
		pinButton = new ButtonField("Login");
		pinButton.setChangeListener( this) ;
		 m.add(pinButton);
		//
		 pageWrapper = 
			new BrowserFieldOAuthDialogWrapper(browserMngr);
		//
		pageWrapper.setConsumerKey(CONSUMER_KEY);
		pageWrapper.setConsumerSecret(CONSUMER_SECRET);
		//pageWrapper.setCallbackUrl(CALLBACK_URL);
		pageWrapper.setOAuthListener(this);
		//
		pageWrapper.login();
		add(m);
		vfm = new VerticalFieldManager();
		hfm = new HorizontalFieldManager();
		bf = new ButtonField("Tweet");
		bf.setChangeListener( this );
		hfm.add(bf);
		
	    tweetMessage = new BasicEditField("Tweet", "#"+zoneName, 140, 0);
	    hfm.add(tweetMessage);
	    
    }
    protected void onUiEngineAttached( boolean attached ){
    	if (attached){
    		new ZoneRequest(this).init();
    	}
    }
	public void fieldChanged(Field field, int arg1) {
		if ( field == pinButton ){
			pageWrapper.login( pinField.getText());
		}
		else if ( field == bf){
			sendTweet();
		}
		
	}
	public void handleStringValue( int type, String val ){
		if ( type == RequestTypes.REQUEST_ZONE_TYPE){
			zoneName = JSONWifiLocatorParser.getZoneName(val);
			setTitle( "#"+zoneName );
		}
	}
	
	public void handleError(int type, int errorCode, Object errorString) {
		if ( type == RequestTypes.REQUEST_ZONE_TYPE){
			setTitle( (String)errorString );
		}
		
	}
	public void handleIntValue(int type, int val) {
		// TODO Auto-generated method stub
		
	}
	public void onAccessDenied(String arg0) {
		// TODO Auto-generated method stub
		
	}
	private void sendTweet(){
		Credential c = new Credential(CONSUMER_KEY,	CONSUMER_SECRET, TwitterConstants.tk);
		UserAccountManager uam = UserAccountManager.getInstance(c);
		//
		try {
			if (uam.verifyCredential()) {
				
				
				TweetER.getInstance(uam).post(new Tweet(tweetMessage.getText()));
			}
		} catch (Exception e) {
			System.out.println("Error by posting tweet.");
		}
	
	}
	private void refreshTweets(){
		Credential c = new Credential(CONSUMER_KEY,	CONSUMER_SECRET, TwitterConstants.tk);
		UserAccountManager uam = UserAccountManager.getInstance(c);
		//
		try {
			if (uam.verifyCredential()) {
				SearchDevice sd = SearchDevice.getInstance();
				 Query q = QueryComposer.containHashtag(zoneName);
				 Tweet[] ts = sd.searchTweets(q);
				 int length = ts.length;
				 for(int i = 0; i < length; i++){
					 System.out.println(ts[i].toString());
				 }
			}
		} catch (Exception e) {
			System.out.println("Error by posting tweet.");
		}
	}
	public void onAuthorize(Token token) {
		
			Credential c = new Credential(CONSUMER_KEY,	CONSUMER_SECRET, token);
			UserAccountManager uam = UserAccountManager.getInstance(c);
			//
			try {
				if (uam.verifyCredential()) {
					TwitterConstants.tk = token;
					this.delete(m);
					vfm.add(hfm);
				    vfm.add(_tweets);
				}
			} catch (Exception e) {
				System.out.println("Error by posting tweet.");
			
		}
	}
	public void onFail(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
}
