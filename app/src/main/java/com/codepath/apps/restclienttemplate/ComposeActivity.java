package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 280;

    EditText etCompose;
    Button btnTweet;
    TextInputLayout tiLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

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
                    Snackbar.make(v, "Sorry, your tweet is too long", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                // make an api call to twitter inside the click listener in order to publish the tweet

            }
        });



    }


}