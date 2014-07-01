package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> homeTweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), homeTweets);
		if (!Tweet.recentTweets().isEmpty()) {
			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_LONG).show();
//			aTweets.clear();
			addAll(Tweet.recentTweets());
		}
	}
	
	@Override
	public void populateTimeline(String s) {
		client.getHomeTimelineList(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
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
	
	public void addAll(List<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	@Override
	public EndlessScrollListener populateEndlessScrollListener(String s) {
		return new EndlessScrollListener() {
				@Override
				public void onLoadMore(int totalItemsCount) {
					populateTimeline(null);
				}
			};
	}
	
}
