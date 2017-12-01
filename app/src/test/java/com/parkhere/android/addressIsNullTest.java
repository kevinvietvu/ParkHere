package com.parkhere.android;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class addressIsNullTest {


    @Test
    public void addressIsNullTestBadValidAddress() throws Exception {
        String bad = "1 Washington Square, San Jose";
        assertFalse(AddSpotMapsActivity.addressIsNull(bad));
    }

    @Test
    public void addressIsNullTestGoodEmptyAddress() throws Exception {
        String good = "";
        assertTrue(AddSpotMapsActivity.addressIsNull(good));
    }

    @Test
    public void addressIsNullTestGoodNullAddress() throws Exception {
        String good = null;
        assertTrue(AddSpotMapsActivity.addressIsNull(good));
    }


}