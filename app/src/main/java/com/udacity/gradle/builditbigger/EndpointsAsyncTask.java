package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.myandroidjokelibrary.AndroidJokeMainActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private Context context;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return "";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(String result) {

        if (result != null) {

            // In  the context of calling another activity in a multiple package app, Which of this code blocks is more appropriate to use?
            //THIS
            Intent myIntent = new Intent(context.getApplicationContext(), AndroidJokeMainActivity.class);
            myIntent.putExtra(AndroidJokeMainActivity.ANDROID_JOKE_KEY, result);
            context.getApplicationContext().startActivity(myIntent);

            /*
            *** OR THIS
            * If this, is it better to set use setComponent, or  setClassName?
            Intent myIntent = new Intent();
            myIntent.setComponent(new ComponentName(context.getApplicationContext(), "com.example.myandroidjokelibrary.AndroidJokeMainActivity"));
//          myIntent.setClassName(this.context, "com.example.myandroidjokelibrary.AndroidJokeMainActivity");
            myIntent.putExtra( "ANDROID_JOKE_KEY", result);
            context.getApplicationContext().startActivity(myIntent);
            */

        } else {
            Log.i("GAGA", "Failed");
        }

    }
}

