package com.brightminds.test.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;

public class NumberValidatorTest {

    @Test
    public void shouldReturnFalseForNull() {
        boolean actual = NumberValidator.isValidBigDecimal(null);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForEmpty() {
        boolean actual = NumberValidator.isValidBigDecimal("");
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForInvalidNumber() {
        boolean actual = NumberValidator.isValidBigDecimal("abc");
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrueForValidNumber() {
        boolean actual = NumberValidator.isValidBigDecimal("10.123");
        assertTrue(actual);
    }
}
