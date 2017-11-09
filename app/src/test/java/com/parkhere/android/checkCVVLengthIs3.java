package com.parkhere.android;

/**
 * Created by Kevin on 11/8/2017.
 */

import org.junit.Test;


import static org.junit.Assert.*;

public class checkCVVLengthIs3 {

    @Test
    public void checkCVVLengthTestBad() throws Exception {
        String bad = "0";
        assertFalse(BrowseListingPaymentActivity.checkCVVLengthIs3(bad));
    }

    @Test
    public void checkCVVLengthTestGood() throws Exception {
        String good = "123";
        assertTrue(BrowseListingPaymentActivity.checkCVVLengthIs3(good));
    }
}
