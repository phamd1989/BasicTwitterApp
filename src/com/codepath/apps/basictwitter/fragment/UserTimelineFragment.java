package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> myTweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), myTweets);
	}
	
	@Override
	public void populateTimeline(String screen_name) {
		client.getUserTimeline(screen_name, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.clear();
				addAll(Tweet.fromJsonArray(json));
				client.setMaxId(aTweets);
				if (client.sinceId == 0) {
					client.setSinceId(aTweets);
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	@Override
	public EndlessScrollListener populateEndlessScrollListener(final String screen_name) {
		return new EndlessScrollListener() {
				@Override
				public void onLoadMore(int totalItemsCount) {
					populateTimeline(screen_name);
				}
			};
	}
}
