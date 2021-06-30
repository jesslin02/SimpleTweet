package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    String name;
    /* aka twitter handle */
    String screenName;
    String pfpUrl;

    // empty constructor needed by Parceler library
    public User() {

    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = "@" + jsonObject.getString("screen_name");
        user.pfpUrl = jsonObject.getString("profile_image_url_https");
        return user;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }
}
