package com.brightminds.test.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;

public class DateValidatorTest {

    @Test
    public void shouldReturnFalseForNull() {
        boolean actual = DateValidator.isValid(null);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForEmptyString() {
        boolean actual = DateValidator.isValid("");
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForInvalidFormat() {
        boolean actual = DateValidator.isValid("12/12/1900");
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrueForValidFormat() {
        boolean actual = DateValidator.isValid("1900-12-12");
        assertTrue(actual);
    }

}
