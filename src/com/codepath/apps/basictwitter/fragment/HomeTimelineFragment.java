package com.codepath.apps.basictwitter.fragment;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.AlarmReceiver;
import com.codepath.apps.basictwitter.HomeTimelineActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TimelineService;
import com.codepath.apps.basictwitter.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {
//	private int count = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ArrayList<Tweet> homeTweets = new ArrayList<Tweet>();
//		aTweets = new TweetArrayAdapter(getActivity(), homeTweets);
//		if (!Tweet.recentTweets().isEmpty()) {
//			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_SHORT).show();
////			aTweets.clear();
//			addAll(Tweet.recentTweets());
//		}
		scheduleAlarm();
		
	}
	
	
	// use AlarmManager to wake up the TimelineService every 5 minutes
	public void scheduleAlarm() {
	    // Construct an intent that will execute the AlarmReceiver
	    Intent intent = new Intent(this.getActivity().getApplicationContext(), AlarmReceiver.class);
	    // Create a PendingIntent to be triggered when the alarm goes off
	    final PendingIntent pIntent = PendingIntent.getBroadcast(this.getActivity(), AlarmReceiver.REQUEST_CODE,
	        intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    // Setup periodic alarm every 5 seconds
	    long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
	    int intervalMillis = 300000; // 5 minutes interval
	    AlarmManager alarm = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
	    alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
	  }
	
	@Override
	public void populateTimeline(String s) {
//		long maxId = 0;
//		if (!aTweets.isEmpty()) {
//			maxId = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
//		}
		
		addAll(Tweet.recentTweets());
		client.setSinceId(aTweets);
//		client.setSinceId(aTweets);
//		Intent i = new Intent(getActivity(), TimelineService.class);
////		i.putExtra("maxId", maxId);
//		getActivity().startService(i);
		
//		client.getHomeTimelineList(maxId, new JsonHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(JSONArray json) {
//				addAll(Tweet.fromJsonArray(json));
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
	}
	

//	@Override
//	public EndlessScrollListener populateEndlessScrollListener(String s) {
//		return new EndlessScrollListener() {
//				@Override
//				public void onLoadMore(int totalItemsCount) {
//					populateTimeline(null);
//				}
//			};
//	}
//	
	
}
