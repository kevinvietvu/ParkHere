package com.parkhere.android;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ManageListingsActivityTest {

    @Rule
    public ActivityTestRule<ManageListingsActivity> mProfileTestRule= new ActivityTestRule<ManageListingsActivity>(ManageListingsActivity.class);

    @Test
    public void profileActivity_btn_create() {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.create_listing),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }
    @Test
    public void profileActivity_btn_my() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction button2 = onView(
                allOf(withId(R.id.my_listings),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
    }
    @Test
    public void profileActivity_btn_reservation() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction button3 = onView(
                allOf(withId(R.id.my_reservations),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));
    }

}

