package com.parkhere.android;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static junit.framework.Assert.*;

/**
 * Created by Stanley on 11/9/2017.
 */

public class StartDateMustBeOnOrAfterCurrentDateTest {
    @Test
    public void testStartDateCanBeAfterCurrentDay() {
        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(11, 31, 17));
    }
    @Test
    public void testStartDateCanBeAfterCurrentMonth() {
        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(12, 22, 17));
    }
    @Test
    public void testStartDateCanBeAfterCurrentYear() {
        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(11, 20, 18));
    }
    @Test
    public void testStartDateCannotBeBeforeCurrentDay() {
        assertFalse(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(11, 8, 17));
    }
    @Test
    public void testStartDateCannotBeBeforeCurrentMonth() {
        assertFalse(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(10, 31, 17));
    }
    @Test
    public void testStartDateCannotBeBeforeCurrentYear() {
        assertFalse(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(11, 29, 16));
    }
    @Test
    public void testStartDateCanBeOnCurrentDate() {
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
        int year = Integer.parseInt(new SimpleDateFormat("yy").format(Calendar.getInstance().getTime()));

        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(month, day, year));
    }
}
