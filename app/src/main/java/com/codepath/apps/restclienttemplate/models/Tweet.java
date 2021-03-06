package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    static final String TAG = "Tweet";

    static final int SECOND_MILLIS = 1000;
    static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    static long lowestId = Integer.MAX_VALUE;

    String body;
    String createdAt;
    String embedUrl;
    User user;
    String id_str;

    // empty constructor needed by Parceler library
    public Tweet() {

    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.id_str = jsonObject.getString("id_str");
//        if (tweet.getIdInt() < lowestId) {
//            lowestId = tweet.getIdInt();
//        }
        if (jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString("full_text");
        } else {
            tweet.body = jsonObject.getString("text");
        }
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.embedUrl = embedFromJson(jsonObject.getJSONObject("entities"));
        Log.i("Tweet", "creating tweet from json object");
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    private static String embedFromJson(JSONObject jsonObject) {
        try {
            JSONObject media = jsonObject.getJSONArray("media").getJSONObject(0);
            Log.i("Tweet", "media object success " + media);
            return media.getString("media_url_https");
        } catch (JSONException e) {
            // Log.e("Tweet", "media array failure " + e);
            return null;
        }

    }

    /**
     * getRelativeTimeAgo using created at date for tweet
     * source: https://gist.github.com/nesquena/f786232f5ef72f6e10a7
     * @return
     */
    public String getRelativeTimeAgo() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(createdAt).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 60 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + "m";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + "h";
            } else {
                return diff / DAY_MILLIS + "d";
            }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public String getId() {
        return id_str;
    }

    public long getIdLong() {
        return Long.parseLong(id_str);
    }

    public static long getLowestId() {
        return lowestId;
    }
}
