package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class ListingIsAtLeastOneHour {
    @Test
    public void testListingCanBeGreaterThanOneHour() {
        assertTrue(CreateListingEndTimeActivity.isLongerThan1Hour(4, 20, 5, 25));
    }
    @Test
    public void testListingCannotBeLessThanOneHour() {
        assertTrue(CreateListingEndTimeActivity.isLongerThan1Hour(4, 20, 5, 00));
    }
    @Test
    public void testListingCanBeExactlyOneHour() {
        assertTrue(CreateListingEndTimeActivity.isLongerThan1Hour(4, 20, 5, 20));
    }
}
