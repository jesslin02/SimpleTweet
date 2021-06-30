package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 280;
    public static final String TAG = "ComposeActivity";

    EditText etCompose;
    Button btnTweet;
    TextInputLayout tiLayout;
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getTwitterClient(this);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        tiLayout = findViewById(R.id.tiLayout);

        tiLayout.setCounterMaxLength(MAX_TWEET_LENGTH);

        // set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Sorry, your tweet cannot be empty", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Snackbar.make(findViewById(android.R.id.content), "Sorry, your tweet is too long", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                // make an api call to twitter in order to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet t = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet says: " + t.getBody());
                            // prepare data intent
                            Intent data = new Intent();
                            // pass relevant data back as a result
                            data.putExtra("tweet", Parcels.wrap(t));
                            // activity finished ok, return the data
                            // set result code and bundle data for response
                            setResult(RESULT_OK, data);
                            // close the activity and pass data to parent activity
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);

                    }
                });

            }
        });



    }


}