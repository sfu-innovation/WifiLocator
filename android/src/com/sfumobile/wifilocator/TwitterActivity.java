package com.sfumobile.wifilocator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterActivity extends Activity{

	private Twitter twitter;
	private SimpleCursorAdapter adapter;
	private ListView tweetListView;
	private String zone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweets);
		
		tweetListView = (ListView)this.findViewById(R.id.TweetListView);
		twitter = new TwitterFactory().getInstance();
		
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	zone = extras.getString("zone");
        }
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		try {
			QueryResult result = twitter.search(new Query(zone.replaceAll(" ", "")));
			Log.d("ZONE",zone.replaceAll(" ", ""));
			List<Tweet> tweets = result.getTweets();
			ArrayList<String> tweet_text = new ArrayList<String>();
			for(Tweet tweet : tweets){
				tweet_text.add("@" + tweet.getFromUser() + " - " + tweet.getText());
			}
			
			if(tweet_text.size() < 1){
				tweet_text.add("No Tweets");
			}
			
			ArrayAdapter a = new ArrayAdapter(this.getApplicationContext(), R.layout.tweet_row, R.id.tweetText, tweet_text);
			tweetListView.setAdapter((ListAdapter)a);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			Log.e("Twitter", e.getMessage());
		}
		
	}
	
	void tweet (AccessToken token, String message){
		Log.d("TWEET", "Tweeting!");
		if (token != null) {
			try {
				twitter.setOAuthAccessToken(token);
				twitter.updateStatus(message);
			} catch (Exception e) {
				Toast.makeText(this, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
				Log.d("Timeline",""+e.getMessage());
			}
		}
	}

}
