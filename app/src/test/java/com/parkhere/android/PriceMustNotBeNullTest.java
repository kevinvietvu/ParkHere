package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Stanley on 11/9/2017.
 */

public class PriceMustNotBeNullTest {
    @Test
    public void testPriceDoubleShouldBeValid() {
        assertTrue(CreateListingDetailsActivity.priceIsNotNull(0.5));
    }
    @Test
    public void testPriceIntShouldBeValid() {
        assertTrue(CreateListingDetailsActivity.priceIsNotNull(5));
    }
    @Test
    public void testPriceCannotBeNull() {
        assertFalse(CreateListingDetailsActivity.priceIsNotNull(null));
    }
}
