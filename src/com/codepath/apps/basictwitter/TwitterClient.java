package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Jy5Cw2hBY9G3LseERuUupQiGv";       // Change this
	public static final String REST_CONSUMER_SECRET = "0kMuucuCi9BKsGDgQU3b7FoouSwZHfUikfvy7urcBHQuPCSoLQ"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
	
	// maxId used for pagination
//	public long maxId = 0;
	public long sinceId = 0;
	String tweet = "";
	long tweetId = 0;
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	
	public void getHomeTimelineList(long maxTweetId, AsyncHttpResponseHandler handler){
		Log.d("debug", "maxTweetId inside getHomeTimelineList: " + Long.toString(maxTweetId));
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		if (maxTweetId == 0) {
			client.get(apiUrl, null, handler);
		} else {
			Log.d("debug", "inside making a call with maxId");
			RequestParams params = new RequestParams();
			params.put("max_id", Long.toString(maxTweetId));
			client.get(apiUrl, params, handler);
		}
		Log.d("debug", "outside");
		
	}
	
	
	public void getUserTimeline(String screen_name, long maxTweetId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		if (screen_name != null) {
			params.put("screen_name", screen_name);
		}
		if (maxTweetId != 0) {
			params.put("max_id", Long.toString(maxTweetId));
		}
		Log.d("debug", "screen_name inside getUserTimeline: " + screen_name);
		Log.d("debug", "maxTweetId inside getUserTimeline: " + Long.toString(maxTweetId));
		if (screen_name == null && maxTweetId == 0) {
			params = null;
		}
		client.get(apiUrl, params, handler);
	}
	
	/**
	 * request for posting a Tweet
	 * @param handler
	 */
	public void postTweet(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		client.post(apiUrl, params, handler);
	}
	
	/**
	 * request for retweeting a Tweet
	 * @param handler
	 */
	public void retweetTweet(AsyncHttpResponseHandler handler) {
		Log.d("debug", "Inside retweeting");
		String apiUrl = getApiUrl("statuses/retweet/" + Long.toString(tweetId) + ".json");
		RequestParams params = new RequestParams();
		params.put("id", Long.toString(tweetId));
//		Log.d("apiUrl: ", apiUrl);
//		Log.d("params: ", params.toString());
		client.post(apiUrl, params, handler);
	}
	
	
	/**
	 * set body text for a Tweet
	 * @param tweetBody
	 */
	public void setTweetBody(String tweetBody) {
		tweet = tweetBody;
	}
	
	public void setTweetId(long aId) {
		tweetId = aId;
		Log.d("debug", "tweetId: " + Long.toString(tweetId));
	}
	
	/**
	 * refresh home_timeline, taking into account the since_id
	 * @param handler
	 */
	public void refreshHomeTimeline(
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("since_id", Long.toString(sinceId));
		client.get(apiUrl, params, handler);
	}

	public void setSinceId(ArrayAdapter<Tweet> aTweets) {
		if (aTweets == null || aTweets.isEmpty()) {
			return;
		}
		sinceId = aTweets.getItem(0).getUid();
		Log.d("debug", "since_id: " + Long.toString(sinceId));
	}


	public void getMentionTimelineList(long maxTweetId, 
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		if (maxTweetId == 0) {
			client.get(apiUrl, null, handler);
		} else {
			RequestParams params = new RequestParams();
			params.put("max_id", Long.toString(maxTweetId));
			client.get(apiUrl, params, handler);
		}
	}

	
	/**
	 * Get any user information given their screen_name
	 * @param screen_name
	 * @param handler
	 */
	public void getUserInfo(String screen_name, 
            AsyncHttpResponseHandler handler) {
		Log.d("debug", "screen_name inside getUserInfo: " + screen_name); 
		if (screen_name == null) {
			String apiUrl = getApiUrl("account/verify_credentials.json");
			client.get(apiUrl, null, handler);
		} else {
			String apiUrl = getApiUrl("users/show.json");
	        RequestParams params = new RequestParams();
	        params.put("screen_name", screen_name);
	        client.get(apiUrl, params, handler);
		}
        
    }
	
}