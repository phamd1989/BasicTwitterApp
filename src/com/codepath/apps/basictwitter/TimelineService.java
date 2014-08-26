package com.codepath.apps.basictwitter;

import org.json.JSONArray;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineService extends IntentService {
	
	public static final String ACTION = "com.codepath.apps.basictwitter.TimelineService";
	private int count = 0;
	
	public TimelineService() {
		super("twitter-timeline-service");
	}
	
	public TimelineService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	// this is where we make network request to fetch home timeline
	protected void onHandleIntent(Intent intent) {
//		long maxId = intent.getLongExtra("maxId", 0);
//		Log.d("Debug", Long.toString(maxId));
		final int sizeBefore = intent.getIntExtra("sizeBefore", 0);
		final Intent i = new Intent(ACTION);
		
		TwitterClient client = TwitterApp.getRestClient();
		if (client.sinceId != 0) {
			client.refreshHomeTimeline(new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONArray json) {
//					addAll(Tweet.fromJsonArray(json));
					Log.d("Debug", json.toString());
					int size = Tweet.fromJsonArray(json).size();
					count = count + size;
////					Bundle bundle = new Bundle();
//					bundle.putSerializable("tweets", Tweet.fromJsonArray(json).size());
//					Log.d("Debug", "json data: " + Tweet.fromJsonArray(json).toString());
					i.putExtra("size", size);
					i.putExtra("sizeBefore", sizeBefore);
					Log.d("Debug", "sizeBefore: " + sizeBefore);
					Log.d("Debug", "size: " + size);
					
					// build notifications
					Intent i2 = new Intent(getApplicationContext(), HomeTimelineActivity.class);
				    int requestID = (int) System.currentTimeMillis();
				    int flags = PendingIntent.FLAG_CANCEL_CURRENT; 
				    
				    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), requestID, i2, flags);
				    
//				    Log.d("Debug", "sizeAfter: " + Tweet.recentTweets().size());
					
				    if (count > 0) {
				    	// constructing notification
					    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).
					    										setSmallIcon(R.drawable.ic_twitter).
					    										setContentTitle("new tweets").
					    										setContentText("you have " + Integer.toString(count) + " new tweets").
					    										setContentIntent(pIntent); //.
//					    										addAction(R.drawable.ic_launcher, "Reply", pIntent).
//					    										addAction(R.drawable.ic_launcher, "Delete", pIntent);
					    
					    builder.setAutoCancel(true);
					    
					    // get a handle to notification service
					    NotificationManager mNotificationManager =
					    	    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
					    // inserting the notification onto the dashboard
					    mNotificationManager.notify(50, builder.build());
				    }

				    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
//					i.putextra
				}
			
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}	
			});
		} else {
			client.getHomeTimelineList(0, new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONArray json) {
					Log.d("Debug", json.toString());
					int size = Tweet.fromJsonArray(json).size();
					count = count + size;
					i.putExtra("size", size);
					i.putExtra("sizeBefore", sizeBefore);
					Log.d("Debug", "sizeBefore: " + sizeBefore);
					Log.d("Debug", "size: " + size);
					
					// build notifications
					Intent i2 = new Intent(getApplicationContext(), HomeTimelineActivity.class);
				    int requestID = (int) System.currentTimeMillis();
				    int flags = PendingIntent.FLAG_CANCEL_CURRENT; 
				    
				    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), requestID, i2, flags);
				    
//				    Log.d("Debug", "sizeAfter: " + Tweet.recentTweets().size());
					
				    if (count > 0) {
				    	// constructing notification
					    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).
					    										setSmallIcon(R.drawable.ic_launcher).
					    										setContentTitle("new tweets").
					    										setContentText("you have " + Integer.toString(count) + " new tweets").
					    										setContentIntent(pIntent); //.
//					    										addAction(R.drawable.ic_launcher, "Reply", pIntent).
//					    										addAction(R.drawable.ic_launcher, "Delete", pIntent);
					    
					    builder.setAutoCancel(true);
					    
					    // get a handle to notification service
					    NotificationManager mNotificationManager =
					    	    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
					    // inserting the notification onto the dashboard
					    mNotificationManager.notify(50, builder.build());
				    }

					
					LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
				}
			
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}	
			});
		}
		

		
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	    
	    	}

}
