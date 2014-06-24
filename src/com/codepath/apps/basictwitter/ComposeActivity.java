package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	}

	private void setupViews() {
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvUserName     = (TextView) findViewById(R.id.tvUserName);
		tvScreenName   = (TextView) findViewById(R.id.tvScreenName);
		etTweet        = (EditText) findViewById(R.id.etTweet);
		btnTweet  	   = (Button) findViewById(R.id.btnTweet);
		
	}
	
	public void onTweet(View v) {
		Intent data = new Intent();
		data.putExtra("tweet", etTweet.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}
}
