package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.*;


/**
 * Created by Stanley on 11/8/2017.
 */
public class PriceMustBeBetween1To999Test {
    @Test
    public void testPriceCanBeGreaterThan1OrLessThan999() {
        assertTrue(CreateListingDetailsActivity.priceMustBeBetween1And999(454.0));
    }
    @Test
    public void testPriceCannotBeGreaterThan999() {
        assertFalse(CreateListingDetailsActivity.priceMustBeBetween1And999(1023.5));
    }
    @Test
    public void testPriceCannotBeLessThan1() {
        assertFalse(CreateListingDetailsActivity.priceMustBeBetween1And999(0.4));
    }
    @Test
    public void testPriceCannotBeNegative() {
        assertFalse(CreateListingDetailsActivity.priceMustBeBetween1And999(-115.2));
    }
}
