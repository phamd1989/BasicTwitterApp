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
import com.codepath.apps.basictwitter.DisasterNewsClient;
import com.codepath.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;

public abstract class TweetsListFragment extends Fragment {
	protected DisasterNewsClient client;
	protected TweetArrayAdapter aTweets;
	protected SwipeListView lvTweets;
	
	
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
		lvTweets = (SwipeListView) v.findViewById(R.id.lvTweets);
		
		
		lvTweets.setAdapter(aTweets);
		
		populateTimeline(null);
//		lvTweets.setOnScrollListener(populateEndlessScrollListener(null));
		
//		Log.d("Debug", "Going inside onItemClickListener");
//		Log.d("Debug", Boolean.toString(lvTweets.isClickable()));
//		Log.d("Debug", lvTweets.toString());
		
//		lvTweets.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
//
//				Log.d("Debug", "Inside onItemClickListener");
//				Intent i = new Intent(getActivity(), TweetViewActivity.class);
//				long uid = aTweets.getItem(position).getUid();
//				i.putExtra("tweet_uid", uid);
//				startActivity(i);
//
//			}
//		});
		
		lvTweets.setSwipeListViewListener(new SwipeListViewListener() {
			
			@Override
			public void onStartOpen(int position, int action, boolean right) {
				// TODO Auto-generated method stub
				Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
			}
			
			@Override
			public void onStartClose(int position, boolean right) {
				// TODO Auto-generated method stub
				Log.d("swipe", String.format("onStartClose %d", position));
			}
			
			@Override
			public void onOpened(int position, boolean toRight) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMove(int position, float x) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onListChanged() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLastListItem() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFirstListItem() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClosed(int position, boolean fromRight) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickFrontView(int position) {
				Log.d("swipe", String.format("onClickFrontView %d", position));
				lvTweets.openAnimate(position); //when you touch front view it will open
			}
			
			@Override
			public void onClickBackView(int position) {
				Log.d("swipe", String.format("onClickBackView %d", position));
				lvTweets.closeAnimate(position); //when you touch back view it will close
			}
			
			@Override
			public void onChoiceStarted() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChoiceEnded() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChoiceChanged(int position, boolean selected) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int onChangeSwipeMode(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		
//		lvTweets.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
//		lvTweets.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
//		lvTweets.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
		lvTweets.setAnimationTime(50); // Animation time
//		lvTweets.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
		
		return v;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
				
	}
	
	public void addAll(List<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public abstract void populateTimeline(String screen_name);
//	public abstract EndlessScrollListener populateEndlessScrollListener(String screen_name);
	
}
