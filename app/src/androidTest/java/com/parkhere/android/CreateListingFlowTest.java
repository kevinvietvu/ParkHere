package com.parkhere.android;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateListingFlowTest {

    @Rule
    public ActivityTestRule<CreateListingMapsActivity> mActivityTestRule = new ActivityTestRule<CreateListingMapsActivity>(CreateListingMapsActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        CreateListingMapsActivity.placeToSend = "119 Cappy Ct San Jose";
    }

    @Test
    public void createListingFlowTest_maps_detail() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.address_submit_btn), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        button.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.enter_spot_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("This is a mock description"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.enter_spot_description), withText("This is a mock description"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.next_step), withText("Next Step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.next_step), withText("Next Step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.next_step), withText("Next Step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.next_step), withText("Next Step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton6.perform(click());


        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.next_step), withText("Next Step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction textView = onView(
                    allOf(withId(R.id.listing_price),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    0),
                            isDisplayed()));
            textView.check(matches(isDisplayed()));

            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.spot_description),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    1),
                            isDisplayed()));
            textView2.check(matches(isDisplayed()));

            ViewInteraction textView3 = onView(
                    allOf(withId(R.id.spot_type),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    2),
                            isDisplayed()));
            textView3.check(matches(isDisplayed()));

            ViewInteraction textView4 = onView(
                    allOf(withId(R.id.listing_start_date),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    3),
                            isDisplayed()));
            textView4.check(matches(isDisplayed()));

            ViewInteraction textView5 = onView(
                    allOf(withId(R.id.listing_start_time),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    4),
                            isDisplayed()));
            textView5.check(matches(isDisplayed()));

            ViewInteraction textView6 = onView(
                    allOf(withId(R.id.listing_end_date),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    5),
                            isDisplayed()));
            textView6.check(matches(isDisplayed()));

            ViewInteraction textView7 = onView(
                    allOf(withId(R.id.listing_end_time),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    6),
                            isDisplayed()));
            textView7.check(matches(isDisplayed()));

            ViewInteraction textView8 = onView(
                    allOf(withId(R.id.Listing_address),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    7),
                            isDisplayed()));
            textView8.check(matches(isDisplayed()));

            ViewInteraction appCompatButton8 = onView(
                    allOf(withId(R.id.confirm_button), withText("Confirm"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    8),
                            isDisplayed()));
            appCompatButton8.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

            try {
                Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ViewInteraction textView9 = onView(
                        allOf(withId(R.id.listing_confirmed), withText("Listing has been created!"),
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        0),
                                isDisplayed()));
                textView9.check(matches(withText("Listing has been created!")));

                ViewInteraction appCompatButton9 = onView(
                        allOf(withId(R.id.okay_button), withText("Okay"),
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        1),
                                isDisplayed()));
                appCompatButton9.perform(click());

            }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
