package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJson(JSONObject obj) {
		Tweet tweet = new Tweet();
		try {
			 tweet.body = obj.getString("text");
			 tweet.uid = obj.getLong("id");
			 tweet.createdAt = obj.getString("created_at");
			 tweet.user = User.fromJson(obj.getJSONObject("user"));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}

}
