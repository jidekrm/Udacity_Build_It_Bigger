package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {
    private static final String INTERSTITIAL_AD_TEXT = "ca-app-pub-3940256099942544/1033173712";
    public static InterstitialAd mInterstitialAd;
    public static ProgressBar progressBar;
    public TextView textView;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_indicator);
        button = findViewById(R.id.button_view);
        textView = findViewById(R.id.instructions_text_view);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIAL_AD_TEXT);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                tellJokeNow();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                tellJokeNow();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJokeNow() {
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this.getApplicationContext());
        endpointsAsyncTask.execute();
        visibility(View.INVISIBLE, View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        visibility(View.VISIBLE, View.INVISIBLE);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void visibility(int visible, int invisible) {
        textView.setVisibility(visible);
        button.setVisibility(visible);
        progressBar.setVisibility(invisible);
    }
}
