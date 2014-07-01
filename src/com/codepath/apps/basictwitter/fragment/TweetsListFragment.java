package com.codepath.apps.basictwitter.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public abstract class TweetsListFragment extends Fragment {
	protected TwitterClient client;
//	protected ArrayList<Tweet> homeTweets;
//	protected ArrayList<Tweet> mentionTweets;
	protected TweetArrayAdapter aTweets;
//	protected TweetArrayAdapter aMentionTweets;
	protected PullToRefreshListView lvTweets;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// non-view initialization
		client = TwitterApp.getRestClient();
//		homeTweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), null);
//		aHomeTweets = new TweetArrayAdapter(getActivity(), homeTweets);
//		mentionTweets = new ArrayList<Tweet>();
//		aMentionTweets = new TweetArrayAdapter(getActivity(), mentionTweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the laytout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// assign our view preferences, get all views
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
//		lvTweets.setAdapter(aMentionTweets);
		
//		if (!Tweet.recentTweets().isEmpty()) {
//			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_LONG).show();
//			addAll(Tweet.recentTweets());
//		} else {
//			populateTimeline();
//		}
		
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//			@Override
//			public void onLoadMore(int totalItemsCount) {
//				populateTimeline("");
//			}
//		});
		
		lvTweets.setOnScrollListener(populateEndlessScrollListener(null));
		
		
//		lvTweets.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				TextView tv = (TextView) view.findViewById(R.id.ivProfileImage);
//				String screename = aTweets.getItem(position).getUser().getScreenName();
//				
//			}
//		});
		// return the view
		return v;
	}
	
	public void addAll(List<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public abstract void populateTimeline(String screen_name);
	public abstract EndlessScrollListener populateEndlessScrollListener(String screen_name);
}
