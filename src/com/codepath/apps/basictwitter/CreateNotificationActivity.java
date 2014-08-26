package com.codepath.apps.basictwitter;

import android.app.Activity;
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

import com.codepath.apps.basictwitter.models.Tweet;

public class CreateNotificationActivity extends Activity {
	private int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_notification);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(TimelineService.ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
	}
	
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		public void onReceive(android.content.Context context, Intent intent) {
			Toast.makeText(getApplicationContext(), "Received", Toast.LENGTH_SHORT).show();
			try {
				int size = intent.getIntExtra("size", 0);
				Toast.makeText(getApplicationContext(), "size: " + Integer.toString(size), Toast.LENGTH_SHORT).show();
				int sizeBefore = intent.getIntExtra("sizeBefore", 0);
				Toast.makeText(getApplicationContext(), "sizeBefore: " + Integer.toString(sizeBefore), Toast.LENGTH_SHORT).show();
				count = count + size - sizeBefore;
				//				addAll(Tweet.recentTweets());
				
			    // Notifications
			    Intent i2 = new Intent(getApplicationContext(), HomeTimelineActivity.class);
			    int requestID = (int) System.currentTimeMillis();
			    int flags = PendingIntent.FLAG_CANCEL_CURRENT; 
			    
			    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), requestID, i2, flags);
			    
			    Log.d("Debug", "sizeAfter: " + Tweet.recentTweets().size());
				
			    // constructing notification
			    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).
			    										setSmallIcon(R.drawable.ic_launcher).
			    										setContentTitle("new tweets").
			    										setContentText("you have " + Integer.toString(count) + " new tweets").
			    										setContentIntent(pIntent); //.
//			    										addAction(R.drawable.ic_launcher, "Reply", pIntent).
//			    										addAction(R.drawable.ic_launcher, "Delete", pIntent);
			    
			    builder.setAutoCancel(true);
			    
			    // get a handle to notification service
			    NotificationManager mNotificationManager =
			    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			    // inserting the notification onto the dashboard
			    mNotificationManager.notify(50, builder.build());

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		};
	};

}
