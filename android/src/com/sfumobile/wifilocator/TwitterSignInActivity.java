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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TwitterSignInActivity extends Activity {
	/** Called when the activity is first created. */

	Twitter twitter;
	RequestToken requestToken;
	public final static String TOKEN_FILE = "access_token";
	public final static String CONSUMER_KEY = "5wNUxrYGUQ2SiPYrUB1w";
	public final static String CONSUMER_SECRET = "49CfLOBUnvIubWwh37FgcWJZR3SqxXkgooHHvnRF4s"; 
	private final String CALLBACKURL = "SFUMobile://wifilocator";
	private String zone;
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
        prefs = getSharedPreferences(TOKEN_FILE,0);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	zone = extras.getString("zone");
        }
	}

	public void login() {
		if(!prefs.contains("token") | !prefs.contains("secret")){
			try {
				twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
				requestToken = twitter.getOAuthRequestToken(CALLBACKURL);
				String authUrl = requestToken.getAuthorizationURL();
				this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
			} catch (TwitterException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.e("Login", e.getMessage());
			}
		}
	}

	@Override
	protected void onStart(){
		super.onStart();
		login();
		Intent myIntent = new Intent(this, TwitterActivity.class);
		myIntent.putExtra("zone", zone);
		startActivity(myIntent);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		try {
			if(uri != null){
				String verifier = uri.getQueryParameter("oauth_verifier");
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
				saveToken(accessToken);
				//displayTimeLine(accessToken);
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
	
	void saveToken(AccessToken token){
		final Editor edit = prefs.edit();
		edit.putString("token", token.getToken());
		edit.putString("secret", token.getTokenSecret());
		edit.commit();
	}
}