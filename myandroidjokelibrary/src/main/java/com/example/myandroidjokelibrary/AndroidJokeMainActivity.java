package com.example.myandroidjokelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidJokeMainActivity extends AppCompatActivity {
    public static final String ANDROID_JOKE_KEY = "ANDROID_JOKE_KEY";
    TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_joke_main);

        jokeTextView = findViewById(R.id.jokeTextView);
        if (getIntent().hasExtra(ANDROID_JOKE_KEY)){
            String jokeIn = getIntent().getExtras().getString(ANDROID_JOKE_KEY);
            jokeTextView.setText(jokeIn);
        }




    }
}
