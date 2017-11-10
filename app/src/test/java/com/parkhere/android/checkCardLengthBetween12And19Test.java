package com.parkhere.android;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class checkCardLengthBetween12And19Test {

    @Test
    public void checkCardLengthTestBadLessThan12() throws Exception {
        String bad = "12345678910";
        assertFalse(BrowseListingPaymentActivity.checkCardLengthBetween12And19(bad));
    }

    @Test
    public void checkCardLengthTestBadGreaterThan19() throws Exception {
        String good = "51238596812834968681293581";
        assertFalse(BrowseListingPaymentActivity.checkCardLengthBetween12And19(good));
    }

    @Test
    public void checkCardLengthTestGoodLength16() throws Exception {
        String good = "5938139206842982";
        assertTrue(BrowseListingPaymentActivity.checkCardLengthBetween12And19(good));
    }

}