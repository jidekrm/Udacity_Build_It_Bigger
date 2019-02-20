package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EndPointAsyncTest extends AndroidTestCase {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testMe() throws Exception {
        EndpointsAsyncTask endPoint = new EndpointsAsyncTask(InstrumentationRegistry.getTargetContext());
        endPoint.execute();
        String tada = endPoint.get(30, TimeUnit.SECONDS);
        assertNotNull(tada);
        assertFalse(TextUtils.isEmpty(tada));
    }
}
