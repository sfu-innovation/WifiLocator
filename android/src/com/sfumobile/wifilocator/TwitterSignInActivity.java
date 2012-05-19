package com.sfumobile.wifilocator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.os.Bundle;

public class TwitterSignInActivity extends Activity{
	
	RequestToken requestToken;
    String pin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
	    try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	    AccessToken accessToken = null;
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (null == accessToken) {
	        System.out.println("Open the following URL and grant access to your account:");
	        System.out.println(requestToken.getAuthorizationURL());
	        System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			try {
				pin = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        try{
	           if(pin.length() > 0){
	             accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	           }else{
	             accessToken = twitter.getOAuthAccessToken();
	           }
	        } catch (TwitterException te) {
	          if(401 == te.getStatusCode()){
	            System.out.println("Unable to get the access token.");
	          }else{
	            te.printStackTrace();
	          }
	        }
	      }
	      //persist to the accessToken for future reference.
	      try {
			storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	      try {
			Status status = twitter.updateStatus("Test Status");
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private static void storeAccessToken(long l, AccessToken accessToken){
		//store accessToken.getToken()
		//store accessToken.getTokenSecret()
	}

}
