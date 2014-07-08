package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TweetViewActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ArrayList<Tweet> homeTweets = new ArrayList<Tweet>();
//		aTweets = new TweetArrayAdapter(getActivity(), homeTweets);
		if (!Tweet.recentTweets().isEmpty()) {
			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_LONG).show();
//			aTweets.clear();
			addAll(Tweet.recentTweets());
		}
		
	}
	
	
	@Override
	public void populateTimeline(String s) {
		long maxId = 0;
		if (!aTweets.isEmpty()) {
			maxId = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
		}
		
		client.getHomeTimelineList(maxId, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJsonArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
//	public void addAll(List<Tweet> tweets) {
//		aTweets.addAll(tweets);
//	}

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
