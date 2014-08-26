package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "tweets")
public class Tweet extends Model implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "body")
	private String body;
	@Column(name = "uid", unique = true)
	private long uid;
	@Column(name = "createdAt")
	private String createdAt;
	@Column(name = "epoch")
	private long epoch;
	@Column(name = "user")
	private User user;
	@Column(name = "userScreenName")
	private String userScreenName;
	@Column(name = "contentImgUrl")
	private String contentImgUrl;
	@Column(name = "favorite")
    public boolean favorite;
	@Column(name = "retweet")
    public boolean retweet;
	
	public Tweet() {
		super();
	}
	
	public Tweet(JSONObject obj) {
		super();
		try {
			 this.body           = obj.getString("text");
			 this.uid            = obj.getLong("id");
			 this.createdAt      = obj.getString("created_at");
			 this.user           = User.fetchUser(obj.getJSONObject("user"));
			 this.userScreenName = this.user.getScreenName();
			 this.favorite       = false;
			 this.retweet        = false;
			 JSONObject temp     = obj.getJSONObject("extended_entities");
			 if (temp != null) {
//				 Log.d("debug", "temp.toString(): " + temp.toString());
				 this.contentImgUrl = temp.getJSONArray("media").getJSONObject(0).getString("media_url") + ":medium";
//				 Log.d("debug", "contentImgUrl: " + contentImgUrl);
			 }
			 
			 // doesn't seem to be the right place to do this
			 // do this instead when Tweet object is created
			 if (this.createdAt != null) {
				 this.epoch = getEpoch(this.createdAt);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getContentImgUrl() {
		return contentImgUrl;
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
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public boolean isRetweet() {
		return retweet;
	}
	
	public void setFavorite(boolean newFavorite) {
		favorite = newFavorite;
	}
	
	public void setRetweet(boolean newRetweet) {
		retweet = newRetweet;
	}
	
	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		
//		Log.d("debug", "json: " + json.toString());
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
				long tweetUid = tweetJson.getLong("id");
				if (getTweet(tweetUid) == null) {
					Tweet tweet = new Tweet(tweetJson);
					if (tweet != null) {
						tweet.epoch = getEpoch(tweet.createdAt);
						tweet.save();
						tweets.add(tweet);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}
	
	
	public static Tweet getTweet(JSONObject jsonObj) {
		return null;
		
	}
	
	public static List<Tweet> recentTweets() {
		
		List<Tweet> allTweets = new Select().from(Tweet.class).orderBy("epoch DESC").execute();
		for (Tweet tweet: allTweets) {
//			Log.d("Debug", "epoch: " + Long.toString(tweet.epoch));
		}
		return allTweets;
		
		
	}
	
	public static List<Tweet> getMentionedTweets() {
		return new Select().from(Tweet.class).where("userScreenName = ?", "DungPhamd1989").execute();
	}
 	
	@Override
	public String toString() {
		return Long.toString(this.uid);
	}
	
	private static Tweet getTweet(long uid) {
		return (Tweet) new Select().from(Tweet.class).where("uid = ?", uid).executeSingle();
	}

	public static List<Tweet> getTweetsForScreenName(String screenName) {
		return new Select().from(Tweet.class).where("userScreenName = ?", screenName).execute();
	}
	
	public static long getEpoch(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
		
		long dateMillis = 0;
		try {
			dateMillis = sf.parse(rawJsonDate).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateMillis;
	}
}
