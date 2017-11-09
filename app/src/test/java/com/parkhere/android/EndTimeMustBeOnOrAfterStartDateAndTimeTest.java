package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class EndTimeMustBeOnOrAfterStartDateAndTimeTest {
    @Test
    public void testTimeCanEndAfterStartDateAndTime() {
        assertTrue(CreateListingEndTimeActivity.endsAfterStartDateAndTime(12, 14, 17, 3, 30,
                12, 14, 17, 3, 45));
    }
    @Test
    public void testTimeEndStartBeforeStartDateAndTime() {
        assertFalse(CreateListingEndTimeActivity.endsAfterStartDateAndTime(4, 14, 17, 3, 20,
                3, 15, 17, 7, 40));
    }
    @Test
    public void testTimeCanEndOnStartDateAndTime() {
        assertTrue(CreateListingEndTimeActivity.endsAfterStartDateAndTime(11, 1, 17, 6, 20,
                11, 1, 17, 6, 20));
    }

}
