package com.parkhere.android;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static junit.framework.Assert.*;

/**
 * Created by Stanley on 11/9/2017.
 */

public class StartTimeMustBeOnOrAfterCurrentDateAndTimeTest {
    @Test
    public void testStartTimeCanBeAfterCurrentDateAndTime() {
        assertTrue(CreateListingStartTimeActivity.startsOnOrAfterCurrentDateAndTime(11, 20, 18, 19, 20));
    }
    @Test
    public void testStartTimeCannotBeBeforeCurrentDateAndTime() {
        assertFalse(CreateListingStartTimeActivity.startsOnOrAfterCurrentDateAndTime(4, 14, 17, 3, 20));
    }
    @Test
    public void testTimeCanStartOnCurrentDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH:mm");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        int month = Integer.parseInt(currentDate.substring(0, 2));
        int day = Integer.parseInt(currentDate.substring(3, 5));
        int year = Integer.parseInt(currentDate.substring(6, 8));
        int hour = Integer.parseInt(currentDate.substring(9, 11));
        int minute = Integer.parseInt(currentDate.substring(12, 14));

        assertTrue(CreateListingStartTimeActivity.startsOnOrAfterCurrentDateAndTime(month, day, year, hour, minute));
    }
}
