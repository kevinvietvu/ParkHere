package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class EndDateMustBeOnOrAfterStartDateTest {
    @Test
    public void testEndDateCanBeAfterStartDay() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 11, 6, 17));
    }
    @Test
    public void testEndDateCanBeAfterStartMonth() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 12, 5, 17));
    }
    @Test
    public void testEndDateCanBeAfterStartYear() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 11, 5, 18));
    }
    @Test
    public void testEndDateCannotBeBeforeStartDay() {
        assertFalse(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 11, 4, 17));
    }
    @Test
    public void testEndDateCannotBeBeforeStartMonth() {
        assertFalse(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 10, 5, 17));
    }
    @Test
    public void testEndDateCannotBeBeforeStartYear() {
        assertFalse(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 11, 5, 16));
    }
    @Test
    public void testEndDateCanBeOnStartDate() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 5, 17, 11, 5, 17));
    }
}
