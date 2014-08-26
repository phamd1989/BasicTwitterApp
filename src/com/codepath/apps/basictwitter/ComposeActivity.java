package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	
//	private ImageView ivProfileImage;
//	private TextView tvUserName;
//	private TextView tvScreenName;
	private EditText etTweet;
//	private Button btnTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.menu_custom_view, null);
		
		// set up the action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		final ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		final TextView tvUserName     = (TextView) findViewById(R.id.tvUserName);
		final TextView tvScreenName   = (TextView) findViewById(R.id.tvScreenName);
		
		tvUserName.setText(getIntent().getStringExtra("user_name"));
		tvScreenName.setText(getIntent().getStringExtra("screen_name"));
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(getIntent().getStringExtra("profile_img_url"), ivProfileImage);
		
		final Button btnTweet = (Button) findViewById(R.id.btnMenuTweet);
		final TextView tvCount = (TextView) findViewById(R.id.tvCount);
		btnTweet.setBackgroundResource(R.drawable.shape_button_normal);
		
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
				if (s.toString().length() != 0) {
					btnTweet.setBackgroundResource(R.drawable.shape_button_pressed);
					tvCount.setText(Integer.toString(140 - s.toString().length()));
				} else {
					btnTweet.setBackgroundResource(R.drawable.shape_button_normal);
				}
			}
		});
		
		btnTweet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("tweet", etTweet.getText().toString());
				setResult(RESULT_OK, data);
				finish();
			}
		});
		
		return true;
	}
	
	
	private void setupViews() {
//		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//		tvUserName     = (TextView) findViewById(R.id.tvUserName);
//		tvScreenName   = (TextView) findViewById(R.id.tvScreenName);
		etTweet        = (EditText) findViewById(R.id.etTweet);
		
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
