package com.parkhere.android;

/**
 * Created by Kevin on 11/8/2017.
 */

import org.junit.Test;


import static org.junit.Assert.*;

public class checkCVVLengthIs3Test {

    @Test
    public void checkCVVLengthTestBadEmpty() throws Exception {
        String bad = "";
        assertFalse(BrowseListingPaymentActivity.checkCVVLengthIs3(bad));
    }

    @Test
    public void checkCVVLengthTestBadLessThan3() throws Exception {
        String bad = "0";
        assertFalse(BrowseListingPaymentActivity.checkCVVLengthIs3(bad));
    }

    @Test
    public void checkCVVLengthTestBadGreaterThan3() throws Exception {
        String bad = "1234";
        assertFalse(BrowseListingPaymentActivity.checkCVVLengthIs3(bad));
    }

    @Test
    public void checkCVVLengthTestGoodExactly3() throws Exception {
        String good = "123";
        assertTrue(BrowseListingPaymentActivity.checkCVVLengthIs3(good));
    }
}
