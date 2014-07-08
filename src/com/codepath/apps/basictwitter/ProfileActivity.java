package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.fragment.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	private ImageView ivProfileImage;
	private TextView tvUserName;
	private TextView tvScreenName;
	private TextView tvTweets;
	private TextView tvFollowers;
	private TextView tvFollowing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screen_name = getIntent().getStringExtra("screen_name");
		Toast.makeText(ProfileActivity.this, screen_name, Toast.LENGTH_LONG).show();
		loadUserProfile(screen_name);
	}

	private void loadUserProfile(final String screen_name) {
		// give preference to database loading for the timeline tweets of each user
		// actually it always does this because when we read a tweet, we also read a user
		// so whatever tweet profile pic we click on, that user is already there in database
		// EXCEPTION: screen_name IS NULL, or we click on the user_profile button
		if (screen_name != null && User.getUserFromScreenName(screen_name) != null) {
			Toast.makeText(ProfileActivity.this, "USE DATABASE", Toast.LENGTH_SHORT).show();
			User user = User.getUserFromScreenName(screen_name);
			populateProfileHeader(user);
			populateProfileTimeline(screen_name);
		} else {
			Toast.makeText(ProfileActivity.this, "DO REQUEST", Toast.LENGTH_SHORT).show();
			TwitterApp.getRestClient().getUserInfo(screen_name, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject userObj) {
					User user = User.fetchUser(userObj);
					getActionBar().setTitle("@" + user.getScreenName());
					populateProfileHeader(user);
					populateProfileTimeline(screen_name);
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			});
		}
	}

	protected void populateProfileHeader(User user) {
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvUserName     = (TextView) findViewById(R.id.tvUserName);
		tvScreenName   = (TextView) findViewById(R.id.tvScreenName);
		tvTweets       = (TextView) findViewById(R.id.tvTweets);
		tvFollowers    = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing    = (TextView) findViewById(R.id.tvFollowing);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(user.getName());
		tvScreenName.setText("@" + user.getScreenName());
		tvTweets.setText(Integer.toString(user.getTweetsCount()) + " tweets");
		tvFollowers.setText(Integer.toString(user.getFollowersCount()) + " followers");
		tvFollowing.setText(Integer.toString(user.getFollowingCount()) + " following");
	}
	
	
	
	protected void populateProfileTimeline(String screen_name) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screen_name);
		ft.replace(R.id.flActivityProfile, fragmentUserTimeline);
		ft.commit();
	}

}
