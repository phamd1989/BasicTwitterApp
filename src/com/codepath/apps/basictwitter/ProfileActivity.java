package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
		loadProfileInfo();
	}

//	private void loadUserProfile() {
//		TwitterApp.getRestClient().getUserMeInfo(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject userObj) {
//				User user = User.fetchUser(userObj);
//				getActionBar().setTitle("@" + user.getScreenName());
//				populateProfileHeader(user);
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}

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
	
	
	
	protected void loadProfileInfo() {
        String screen_name = getIntent().getStringExtra("screen_name");
        
        
        if ( screen_name == null ) {
            TwitterApp.getRestClient().getUserMeInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject json) {
                    User u = User.fetchUser(json);
                    getActionBar().setTitle("@" + u.getScreenName());
                    
                    populateProfileHeader(u);
                    populateProfileTimeline(u);
                }
                
                @Override
                public void onFailure(Throwable e, String s) {
                    Log.d("ERROR", e.toString() );
                    Log.d("ERROR", s);
                }
                
                @Override
                protected void handleFailureMessage(Throwable e, String s) {
                    Log.d("ERROR", e.toString() );
                    Log.d("ERROR", s);
                }
                
            });
        }
        else {
            TwitterApp.getRestClient().getUserInfo(screen_name, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject json) {
                    User u = User.fetchUser(json);
                    getActionBar().setTitle("@" + u.getScreenName());
                    populateProfileHeader(u);
                    populateProfileTimeline(u);
                }
                
                @Override
                public void onFailure(Throwable e, String s) {
                    Log.d("ERROR", e.toString() );
                    Log.d("ERROR", s);
                }
                
                @Override
                protected void handleFailureMessage(Throwable e, String s) {
                    Log.d("ERROR", e.toString() );
                    Log.d("ERROR", s);
                }
            });
        }

    }

	protected void populateProfileTimeline(User user) {
		String screen_name = user.getScreenName();
        
        String debugStr = String.format( 
                "loadProfileInfo: screen_name: %s ", screen_name );
        Log.d("DEBUG", debugStr );
        
        UserTimelineFragment utf = 
                (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
        utf.populateTimeline(screen_name);
		
	}

}
