package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineActivity extends Activity {
	
	private static final int REQUEST_CODE = 50;
	
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	private String myUserName;
	private String myScreenName;
	private String myProfileImgUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_timeline);
		client = TwitterApp.getRestClient();
		populateHomeTimeline();
		populateUserMeInfo();
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int totalItemsCount) {
				customLoadMoreDataFromApi();
			}
		});
	}
	
	private void populateUserMeInfo() {
		client.getUserMeInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObj) {
				try {
					myUserName      = jsonObj.getString("name");
					myScreenName    = jsonObj.getString("screen_name");
					myProfileImgUrl = jsonObj.getString("profile_image_url");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	protected void customLoadMoreDataFromApi() {
		client.getHomeTimelineList(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
				client.setMaxId(aTweets);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	public void populateHomeTimeline() {
		client.getHomeTimelineList(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
				client.setMaxId(aTweets);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void onCompose(MenuItem mi) {
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(HomeTimelineActivity.this, ComposeActivity.class);
		i.putExtra("user_name", this.myUserName);
		i.putExtra("screen_name", this.myScreenName);
		i.putExtra("profile_img_url", this.myProfileImgUrl);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String tweet = data.getExtras().getString("tweet");
			client.setTweetBody(tweet);
			postTweetUpdate();
		}
	}

	private void postTweetUpdate() {
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObj) {
				aTweets.insert(Tweet.fromJson(jsonObj), 0);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
		
	}
}
