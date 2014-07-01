package com.codepath.apps.basictwitter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;


public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View view;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			view = convertView;
		}
		
		// find the views within the template
		ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
		ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		TextView tvUserName   = (TextView) view.findViewById(R.id.tvUserName);
		TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
		TextView tvBody		  = (TextView) view.findViewById(R.id.tvBody);
		TextView tvTimestamp  = (TextView) view.findViewById(R.id.tvTimestamp);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ivProfileImage.setTag(tvScreenName.getText().toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		// populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvScreenName.setText("@" + tweet.getUid());//getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
		
		final User user = tweet.getUser();
		ivProfileImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //client.setUser_timeline_max_id( screen_name, getMax_id() );
                
                Intent i = new Intent(getContext(), ProfileActivity.class);
//                i.putExtra("user_id", user.getUserId() );
                i.putExtra("screen_name", user.getScreenName() );
                v.getContext().startActivity(i);
            }

        });
		return view;
	}
	
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return relativeDate;
	}
}
