package com.sfumobile.wifilocator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterActivity extends Activity implements OnClickListener{

	private Twitter twitter;
	private ListView tweetListView;
	private EditText tweetText;
	private Button tweetButton;
	private Button cancelButton;
	private String zone;
	private ArrayList<String> tweet_text;
	private ArrayAdapter<String> adapter;
	private AccessToken token;
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_view);
		
		prefs = getSharedPreferences(TwitterSignInActivity.TOKEN_FILE, 0);
		
		tweetListView = (ListView)this.findViewById(R.id.TweetListView);
		tweetText     = (EditText)this.findViewById(R.id.tweetText);
		cancelButton  = (Button)this.findViewById(R.id.cancelButton);
		tweetButton   = (Button)this.findViewById(R.id.tweetButton);
		
		tweetText.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		tweetButton.setOnClickListener(this);
		
		twitter = new TwitterFactory().getInstance();
		
		tweetText.setTextColor(Color.GRAY);
		
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	zone = extras.getString("zone");
        }
		twitter.setOAuthConsumer(TwitterSignInActivity.CONSUMER_KEY, TwitterSignInActivity.CONSUMER_SECRET);
        token = new AccessToken(prefs.getString("token", ""), prefs.getString("secret", ""));
		twitter.setOAuthAccessToken(token);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		try {
			Log.d("ZONE",zone.replaceAll(" ", ""));
			QueryResult result = twitter.search(new Query(zone.replaceAll(" ", "")));
			List<Tweet> tweets = result.getTweets();
			tweet_text = new ArrayList<String>();
			for(Tweet tweet : tweets){
				tweet_text.add("@" + tweet.getFromUser() + " - " + tweet.getText());
			}
			
			if(tweet_text.size() < 1){
				tweet_text.add("No Tweets");
			}
			
			adapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.tweet_row, R.id.tweetText, tweet_text);
			tweetListView.setAdapter((ListAdapter)adapter);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			Log.e("Twitter", e.getMessage());
			
			tweet_text = new ArrayList<String>();
			tweet_text.add("No Tweets");
			adapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.tweet_row, R.id.tweetText, tweet_text);
			tweetListView.setAdapter((ListAdapter)adapter);
		}
		
	}
	
	void tweet (String message){
		if (token != null) {
			Status status = null;
			try {
				status = twitter.updateStatus(message);
			} catch (Exception e) {
				Toast.makeText(this, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
				Log.d("Timeline",""+e.getMessage());
			}
			if(status != null){
				Toast.makeText(this, "Updated Twitter Status",Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tweetText:
			tweetText.setText("");
			tweetText.setTextColor(Color.BLACK);
			break;
		case R.id.cancelButton:
			tweetText.setText("Tweet");
			tweetText.setTextColor(Color.GRAY);
			break;
	
		case R.id.tweetButton:
			tweet("#" + zone + " " + tweetText.getText().toString());
			break;
		}
	}

}
