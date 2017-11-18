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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginTestRule= new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void LoginActivityTest_link() throws Exception{
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btn_link_signup)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.btn_link_login)).perform(click());
        Thread.sleep(1500);
    }
    @Test
    public void LoginActivityTest_email_null() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid email."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Enter a valid email.")));
    }

    @Test
    public void LoginActivityTest_email_neg() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.login_input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("test@black"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid email."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Enter a valid email.")));
    }

    @Test
    public void LoginActivityTest_email_pos_pass_null() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.login_input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("test@black.box"));


        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView1 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid password with min 6 characters."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_password),
                                        1),
                                0),
                        isDisplayed()));
        textView1.check(matches(withText("Enter a valid password with min 6 characters.")));
    }

    @Test
    public void LoginActivityTest_email_pos_pass_neg() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.login_input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("test@black.box"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.login_input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_password),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Bl@ck"));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView1 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid password with min 6 characters."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_password),
                                        1),
                                0),
                        isDisplayed()));
        textView1.check(matches(withText("Enter a valid password with min 6 characters.")));
    }

    @Test
    public void LoginActivityTest_email_pos_pass_pos() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.login_input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("test@black.box"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.login_input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_input_layout_password),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Blackb0x"));


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
