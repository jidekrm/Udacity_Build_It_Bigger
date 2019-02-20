package com.udacity.gradle.builditbigger.backend;

import com.example.javejokelib.JokeWizard;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        myData = new JokeWizard().getJokes();
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}