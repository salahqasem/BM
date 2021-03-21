package com.brightminds.test.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;


import org.junit.jupiter.api.Test;

public class NumberRageValidatorTest {

    @Test
    public void shouldReturnFalseForNull() {
        boolean actual = NumberRangeValidator.isValid(null, null);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForInvalidRange() {
        boolean actual = NumberRangeValidator.isValid(new BigDecimal("10"), new BigDecimal("1"));
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrueForValidRange() {
        boolean actual = NumberRangeValidator.isValid(new BigDecimal("10"), new BigDecimal("20"));
        assertTrue(actual);
    }

}
