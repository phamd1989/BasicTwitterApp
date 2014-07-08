package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TweetViewActivity;
import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public abstract class TweetsListFragment extends Fragment {
	protected TwitterClient client;
	protected TweetArrayAdapter aTweets;
	protected PullToRefreshListView lvTweets;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// non-view initialization
		client = TwitterApp.getRestClient();
		aTweets = new TweetArrayAdapter(getActivity(), new ArrayList<Tweet>());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// assign our view preferences, get all views
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		return v;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		lvTweets.setOnScrollListener(populateEndlessScrollListener(null));
		
		Log.d("Debug", "Going inside onItemClickListener");
		Log.d("Debug", Boolean.toString(lvTweets.isClickable()));
		Log.d("Debug", lvTweets.toString());
		
		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

				Log.d("Debug", "Inside onItemClickListener");
				Intent i = new Intent(getActivity(), TweetViewActivity.class);
				long uid = aTweets.getItem(position).getUid();
				i.putExtra("tweet_uid", uid);
				startActivity(i);

			}
		});
		
	}
	
	public void addAll(List<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public abstract void populateTimeline(String screen_name);
	public abstract EndlessScrollListener populateEndlessScrollListener(String screen_name);
	
}
