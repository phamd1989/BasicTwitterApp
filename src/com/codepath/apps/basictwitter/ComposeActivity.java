package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	
	private ImageView ivProfileImage;
	private TextView tvUserName;
	private TextView tvScreenName;
	private EditText etTweet;
	private Button btnTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
		
		tvUserName.setText(getIntent().getStringExtra("user_name"));
		tvScreenName.setText(getIntent().getStringExtra("screen_name"));
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(getIntent().getStringExtra("profile_img_url"), ivProfileImage);
		
		etTweet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().length() != 0) {
					btnTweet.setBackgroundResource(R.drawable.selector_tweet_button);
//					btnTweet.setBackgroundColor(Color.BLUE);
				} else {
					btnTweet.setBackgroundResource(R.drawable.shape_button_normal);
//					btnTweet.setBackgroundColor(Color.YELLOW);
				}
			}
		});
	}

	private void setupViews() {
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvUserName     = (TextView) findViewById(R.id.tvUserName);
		tvScreenName   = (TextView) findViewById(R.id.tvScreenName);
		etTweet        = (EditText) findViewById(R.id.etTweet);
		btnTweet  	   = (Button) findViewById(R.id.btnTweet);
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
	}
	
	public void onTweet(View v) {
		Intent data = new Intent();
		data.putExtra("tweet", etTweet.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}
}
