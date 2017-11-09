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
    public void testStartDateCanBeAfterCurrentDate() {
        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(12, 3, 18));
    }
    @Test
    public void testStartDateCannotBeBeforeCurrentDate() {
        assertFalse(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(2, 6, 16));
    }
    @Test
    public void testStartDateCanBeOnCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        int month = Integer.parseInt(currentDate.substring(0, 2));
        int day = Integer.parseInt(currentDate.substring(3, 5));
        int year = Integer.parseInt(currentDate.substring(6, 8));

        assertTrue(CreateListingStartDateActivity.startsOnOrAfterCurrentDate(month, day, year));
    }
}
