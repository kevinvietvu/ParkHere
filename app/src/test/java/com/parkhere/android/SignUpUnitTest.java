package com.parkhere.android;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by ricky on 11/9/2017.
 */

public class SignUpUnitTest {
    @Test
    public void testCorrect() throws Exception{
        String[] email = {"prettyandsimple@example.com",
                "very.common@example.com",
                "other.email-with-dash@example.com",
                "fully-qualified-domain@example.com",
                "x@example.com",
                "example-indeed@strange-example.com",
                "example@s.solutions"};
        for(String test : email)
            assertTrue(SignupActivity.isEmailValid(test));
    }

    @Test
    public void testIncorrect() throws Exception{
        String[] email = {"Abc.example.com",
                "A@b@c@example.com",
                "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com",
                "just\"not\"right@example.com",
                "john..doe@example.com",
                "example@localhost",
                "john.doe@example..com",
                "\" \"@example.org",
                "\"very.unusual.@.unusual.com\"@example.com",
                "Duy"};
        for(String test : email)
            assertFalse(SignupActivity.isEmailValid(test));
    }

    @Test
    public void testNull() throws Exception{
        String email = "";
        assertFalse(SignupActivity.isEmailValid(email));
    }

    @Test
    public void testCorrectPassword() throws Exception {
        String[] password = {"Test1234",
                             "RuBber$39",
                             "ReallyLongPasswordThatGoesOnForever99999999999!!!!!"};
        for(String test : password)
            assertTrue(SignupActivity.isPasswordValid(test));
    }

    @Test
    public void testInorrectPassword() throws Exception {
        String[] password = {"short",
                             "testnocapital",
                             "TESTNOLOWER",
                             "1234567891234568",
                             "lowerand75",
                             "UPPERAND75",
                             "lowerUPPER"};
        for(String test : password)
            assertFalse(SignupActivity.isPasswordValid(test));
    }
    @Test
    public void testNullPassword() throws Exception {
        String password = "";
        assertFalse(SignupActivity.isPasswordValid(password));
    }
}
