package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "tweets")
public class Tweet extends Model{
	
	@Column(name = "body")
	private String body;
	@Column(name = "uid", unique = true)
	private long uid;
	@Column(name = "createdAt")
	private String createdAt;
	@Column(name = "user")
	private User user;
	
	
	public Tweet() {
		super();
	}
	
	public Tweet(JSONObject obj) {
		super();
		try {
			 this.body      = obj.getString("text");
			 this.uid       = obj.getLong("id");
			 this.createdAt = obj.getString("created_at");
			 this.user      = User.fetchUser(obj.getJSONObject("user"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			Tweet tweet = new Tweet(tweetJson);
			if (tweet != null) {
				tweet.save();
				tweets.add(tweet);
			}
		}
		Log.d("debug", Tweet.recentTweets().toString());
		Log.d("debug", User.recentUsers().toString());
		return tweets;
	}
	
	public static List<Tweet> recentTweets() {
		return new Select().from(Tweet.class).orderBy("createdAt DESC").execute();
	}
	
	@Override
	public String toString() {
		return Long.toString(this.uid);
	}

}
