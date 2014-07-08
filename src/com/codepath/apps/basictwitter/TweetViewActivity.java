package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TweetViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_view);
		
		long uid = getIntent().getLongExtra("tweet_uid", 0);
		Toast.makeText(this, Long.toString(uid), Toast.LENGTH_SHORT).show();
	}
}
