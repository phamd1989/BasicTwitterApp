package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

public class MentionTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ArrayList<Tweet> mentionsTweets = new ArrayList<Tweet>();
//		aTweets = new TweetArrayAdapter(getActivity(), mentionsTweets);
	}
	
	@Override
	public void populateTimeline(String s) {
		long maxId = 0;
		if (!aTweets.isEmpty()) {
			maxId = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
		}
		
		client.getMentionTimelineList(maxId, new JsonHttpResponseHandler() {
			
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
	
	@Override
	public EndlessScrollListener populateEndlessScrollListener(String screen_name) {
		return new EndlessScrollListener() {
				@Override
				public void onLoadMore(int totalItemsCount) {
					populateTimeline(null);
				}
			};
	}
}

