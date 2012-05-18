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
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterActivity extends Activity{

	private Twitter twitter;
	private SimpleCursorAdapter adapter;
	private ListView tweetListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweets);
		
		tweetListView = (ListView)this.findViewById(R.id.TweetListView);
		twitter = new TwitterFactory().getInstance();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		try {
			QueryResult result = twitter.search(new Query("SFU"));
			List<Tweet> tweets = result.getTweets();
			ArrayList<String> tweet_text = new ArrayList<String>();
			for(Tweet tweet : tweets){
				tweet_text.add("@" + tweet.getFromUser() + " - " + tweet.getText());
			}
			ArrayAdapter a = new ArrayAdapter(this.getApplicationContext(), R.layout.tweet_row, R.id.tweetText, tweet_text);
			tweetListView.setAdapter((ListAdapter)a);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			Log.e("Twitter", e.getMessage());
		}
		
	}

}
