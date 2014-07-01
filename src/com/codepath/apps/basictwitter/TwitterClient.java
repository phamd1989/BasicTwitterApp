package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
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
	public long maxId = 0;
	public long sinceId = 0;
	String tweet = "";
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	
	public void getHomeTimelineList(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		if (maxId == 0) {
			client.get(apiUrl, null, handler);
		} else {
			RequestParams params = new RequestParams();
			params.put("max_id", Long.toString(maxId));
			client.get(apiUrl, params, handler);
		}
	}
	
	
	public void getUserMeInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
	
	public void getUserTimeline(String screen_name, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		if ( screen_name != null) {
			params.put("screen_name", screen_name);
		} else {
			params = null;
		}
		client.get(apiUrl, params, handler);
	}
	
	public void postTweet(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		client.post(apiUrl, params, handler);
	}
	
	public void setMaxId(ArrayAdapter<Tweet> aTweets) {
		if (aTweets == null || aTweets.isEmpty()) {
			return;
		}
		maxId = aTweets.getItem(aTweets.getCount() - 1).getUid();
		Log.d("debug", Long.toString(maxId));
		maxId--;
	}
	
	public void setTweetBody(String tweetBody) {
		tweet = tweetBody;
	}


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


	public void getMentionTimelineList(
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		if (maxId == 0) {
			client.get(apiUrl, null, handler);
		} else {
			RequestParams params = new RequestParams();
			params.put("max_id", Long.toString(maxId));
			client.get(apiUrl, params, handler);
		}

//		// Can specify query string params directly or through RequestParams.
//		if (maxId == 0) {
//			client.get(apiUrl, null, handler);
//		} else {
//			RequestParams params = new RequestParams();
//			params.put("max_id", Long.toString(maxId));
//			client.get(apiUrl, params, handler);
//		}
//		
	}


	public void getUserInfo(String screen_name, 
            AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);
        client.get(apiUrl, params, handler);  // if no params set, then just pass null
    }
	
}