package com.brightminds.test.validator;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class DateRangeValidatorTest {

    @Test
    public void shouldReturnTrueForValidRage() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        boolean actual = DateRangeValidator.isValid(calendar.getTime(), new Date());
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalseForNull() {
        boolean actual = DateRangeValidator.isValid(null, null);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseForInvalidRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        boolean actual = DateRangeValidator.isValid(calendar.getTime(), new Date());
        assertFalse(actual);
    }
}
