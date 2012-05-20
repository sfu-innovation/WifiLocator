package com.sfumobile.wifilocator;

import java.util.List;

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

public class TwitterSignInActivity extends Activity {
	/** Called when the activity is first created. */

	Twitter twitter;
	RequestToken requestToken;
	public final static String consumerKey = "5wNUxrYGUQ2SiPYrUB1w";
	public final static String consumerSecret = "49CfLOBUnvIubWwh37FgcWJZR3SqxXkgooHHvnRF4s"; 
	private final String CALLBACKURL = "SFUMobile://wifilocator";
	private String zone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	zone = extras.getString("zone");
        }
        
		login();
	}

	public void login() {
		try {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
			requestToken = twitter.getOAuthRequestToken(CALLBACKURL);
			String authUrl = requestToken.getAuthorizationURL();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (TwitterException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e("Login", e.getMessage());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		Log.d("RESUME", "Resuming!");
		try {
			if(uri != null){
				String verifier = uri.getQueryParameter("oauth_verifier");
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
				displayTimeLine(accessToken);
				Intent myIntent = new Intent(this.getApplicationContext(), TwitterActivity.class);
				myIntent.putExtra("zone", zone);
				startActivity(myIntent);
			}
		} catch (TwitterException e) {
			Log.e("onNewIntent", "" + e.getMessage());
		}
	}

	void displayTimeLine(AccessToken token) {
		if (token != null) {
			List<Status> statuses = null;
			try {
				twitter.setOAuthAccessToken(token);
				statuses = twitter.getHomeTimeline();
				Toast.makeText(this, statuses.get(0).getText(), Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(this, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
				Log.d("Timeline",""+e.getMessage());
			}
			
		} else {
			Toast.makeText(this, "Not Verified", Toast.LENGTH_LONG).show();
		}
	}
}