package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragment.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragment.MentionTimelineFragment;
import com.codepath.apps.basictwitter.listerner.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineActivity extends FragmentActivity {
	
	private static final int REQUEST_CODE = 50;
	private TwitterClient client;
	
	private String myUserName;
	private String myScreenName;
	private String myProfileImgUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_timeline);
		client = TwitterApp.getRestClient();
		setupTabs();
	}
	

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
//			.setCustomView(R.layout.tab_home)
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		
		Tab tab2 = actionBar
			.newTab()
//			.setCustomView(R.layout.tab_mentions)
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionTimelineFragment>(R.id.flContainer, this, "mentions",
			    		MentionTimelineFragment.class));
		
		actionBar.addTab(tab2);
		actionBar.selectTab(tab1);
		
//		Tab tab = actionBar.getSelectedTab();
//		if (tab.equals(tab1)) {
//			tab.setCustomView(R.layout.tab_home_selected);
//		} else if (tab.equals(tab2)) {
//			tab.setCustomView(R.layout.tab_mentions_selected);
//		}
		
		
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
		
//		private void fetchTimelineAsync() {
//			client.refreshHomeTimeline(new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(JSONArray json) {
//					List<Tweet> newTweets = Tweet.fromJsonArray(json);
//					for (int i = newTweets.size() - 1; i>=0; i--) {
//						aTweets.insert(newTweets.get(i), 0);
//					}
//					client.setSinceId(aTweets);
//					lvTweets.onRefreshComplete();
//				}
//				
//				@Override
//				public void onFailure(Throwable e, String s) {
//					Log.d("debug", e.toString());
//					Log.d("debug", s.toString());
//				}
//			});
//			
//		}

//	private void populateUserMeInfo() {
//		client.getUserMeInfo(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject jsonObj) {
//				try {
//					myUserName      = jsonObj.getString("name");
//					myScreenName    = jsonObj.getString("screen_name");
//					myProfileImgUrl = jsonObj.getString("profile_image_url");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}

	
	public void onCompose(MenuItem mi) {
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(HomeTimelineActivity.this, ComposeActivity.class);
		
		User me = User.getUserFromScreenName("DungPhamd1989");
		if (me != null ) {
			this.myUserName      = me.getName();
			this.myScreenName    = me.getScreenName();
			this.myProfileImgUrl = me.getProfileImageUrl();
		}
		
		i.putExtra("user_name", this.myUserName);
		i.putExtra("screen_name", this.myScreenName);
		i.putExtra("profile_img_url", this.myProfileImgUrl);
		startActivityForResult(i, REQUEST_CODE);
		overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
	}
	
	public void onProfile(MenuItem mi) {
		Intent i = new Intent(HomeTimelineActivity.this, ProfileActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
//	public void viewProfile(View v) {
//		String s = (String) v.getTag();
//		TextView tv = (TextView) v.findViewById(R.id.tvUserName);
//		//String s = tv.getText().toString();
//		Toast.makeText(this, tv.getText().toString(), Toast.LENGTH_LONG).show();
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String tweet = data.getExtras().getString("tweet");
			client.setTweetBody(tweet);
			postTweetUpdate();
		}
	}

	private void postTweetUpdate() {
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObj) {
				//aTweets.insert(new Tweet(jsonObj), 0);
				Toast.makeText(HomeTimelineActivity.this, "tweet posted", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
		
	}
}
