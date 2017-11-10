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
public class Signup {
    @Rule
    public ActivityTestRule<ProfileActivity> SignupTestRule= new ActivityTestRule<ProfileActivity>(ProfileActivity.class);

    @Test
    public void delay() throws Exception{

        Thread.sleep(50000);

    }
}