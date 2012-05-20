package com.sfumobile.wifilocator;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TwitterSignInActivity extends Activity{
	
	private final static String CONSUMER_KEY    = "5wNUxrYGUQ2SiPYrUB1w";
	private final static String CONSUMER_SECRET = "49CfLOBUnvIubWwh37FgcWJZR3SqxXkgooHHvnRF4s";
	private final String CALLBACK_URI    = "SFUMobile://twittersigninactivity?";
	private Twitter twitter;
	RequestToken requestToken;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweets);
		login();
	}
		
	    
	    /*
	             accessToken = twitter.getOAuthAccessToken(requestToken, pin);

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
		*/
	
	void login() {
		try {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			requestToken = twitter.getOAuthRequestToken(CALLBACK_URI);
			String authUrl = requestToken.getAuthenticationURL();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (TwitterException ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			Log.e("in login", ex.getMessage());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = this.getIntent();
		try {
			Uri uri = intent.getData();
			if(uri != null){
				String verifier = uri.getQueryParameter("oauth_verifier");
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken,
						verifier);
				String token = accessToken.getToken(), secret = accessToken
						.getTokenSecret();
			}
		} catch (TwitterException ex) {
			Log.e("Main.onNewIntent", "" + ex.getMessage());
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
