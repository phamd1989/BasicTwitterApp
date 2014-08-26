package com.codepath.apps.basictwitter;

import com.codepath.apps.basictwitter.models.Tweet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	public static final int REQUEST_CODE = 12345;
	public static final String ACTION = "com.codepath.apps.basictwitter.TimelineService";
	
	public AlarmReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, TimelineService.class);
		int sizeBefore = Tweet.recentTweets().size();
		i.putExtra("sizeBefore", sizeBefore);
		context.startService(i);
	}
	
}
