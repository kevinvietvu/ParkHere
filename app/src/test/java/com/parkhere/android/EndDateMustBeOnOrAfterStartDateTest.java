package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class EndDateMustBeOnOrAfterStartDateTest {
    @Test
    public void testEndDateCanBeAfterStartDate() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(12, 3, 18, 12, 4, 18));
    }
    @Test
    public void testEndDateCannotBeBeforeStartDate() {
        assertFalse(CreateListingEndDateActivity.endsOnOrAfterStartDate(8, 7, 17, 7, 9, 17));
    }
    @Test
    public void testEndDateCanBeOnStartDate() {
        assertTrue(CreateListingEndDateActivity.endsOnOrAfterStartDate(11, 2, 17, 11, 2, 17));
    }
}
