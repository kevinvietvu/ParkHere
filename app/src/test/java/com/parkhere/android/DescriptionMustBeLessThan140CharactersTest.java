package com.parkhere.android;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by Stanley on 11/9/2017.
 */

public class DescriptionMustBeLessThan140CharactersTest {
    @Test
    public void testDescriptionCanBeLessThanOrEqualTo140Characters() {
        String string = "aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrssssstttttuuuuuvvvvvwwwwwxxxxxyyyyyzzzzz";
        assertTrue(CreateListingDetailsActivity.descriptionMustBeLessThan140Characters(string));
    }
    @Test
    public void testDescriptionCannotBeMoreThan140Characters() {
        String string = "aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrssssstttttuuuuuvvvvvwwwwwxxxxxyyyyyzzzzz1234567890123";
        assertFalse(CreateListingDetailsActivity.descriptionMustBeLessThan140Characters(string));
    }
}
