package com.parkhere.android;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.ViewFinder.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/**
 * Created by nelson on 11/9/17.
 */

@RunWith(JUnit4.class)
public class TESTER {
    @Rule
    public ActivityTestRule<LoginActivity> LoginTestRule= new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void LoginActivity_btn_link_signup_blackbox() throws Exception{
        onView(withId(R.id.btn_link_signup)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.btn_link_login)).perform(click());
        Thread.sleep(1500);
    }


}