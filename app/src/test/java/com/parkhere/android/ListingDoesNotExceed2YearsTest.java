package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class ListingDoesNotExceed2YearsTest {
    @Test
    public void testCannotExceed2Years() {
        assertFalse(CreateListingEndDateActivity.isNotLongerThan2Years(17, 20));
    }
    @Test
    public void testCanBeLessThan2Years() {
        assertTrue(CreateListingEndDateActivity.isNotLongerThan2Years(17, 18));
    }
    @Test
    public void testCanBeExactly2Years() {
        assertTrue(CreateListingEndDateActivity.isNotLongerThan2Years(17, 19));
    }
}
