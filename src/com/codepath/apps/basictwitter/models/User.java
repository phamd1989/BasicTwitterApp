package com.codepath.apps.basictwitter.models;

import org.json.JSONObject;

public class User {
	private String name;
	private long id;
	private String screenName;
	private String profileImageUrl;
	
	public static User fromJson(JSONObject obj) {
		User user = new User();
		try {
			 user.name = obj.getString("name");
			 user.id = obj.getLong("id");
			 user.screenName = obj.getString("screen_name");
			 user.profileImageUrl = obj.getString("profile_image_url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
