package com.brightminds.test.controller.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.Test;

import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.configuration.constants.DateFormats;
import com.brightminds.test.controller.util.bean.ValidatorResult;

public class AccountParamsValidatorTest {

    private final static String INVALID_DATE_FORMAT = "%s is not a valid date, please use '" + DateFormats.QUERY_DATE_FORMAT.getFormat().toPattern()
        + "' as format.";

    private final static String INVALID_DATE_RANGE = "%s Must be after %s";
    private final static String INVALID_NUMBER = "%s is not a valid number.";
    private final static String INVALID_NUMBER_RANGE = " Must be greater or equal to ";

    @Test
    public void shouldReturnTrueForEmptyParams() {
        ValidatorResult actual = AccountParamsValidator.validate(Collections.emptyMap());
        assertTrue(actual.isValid());
        assertTrue(actual.getErrors().isEmpty());
    }

    @Test
    public void shouldReturnTrueForValidParams() {

        Map<String, String> params = new HashMap();
        params.put(AccountParamKeys.TO_DATE.getKey(), "2021-05-10");
        params.put(AccountParamKeys.FROM_DATE.getKey(), "2021-01-10");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "1");
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "10");

        ValidatorResult actual = AccountParamsValidator.validate(params);
        assertTrue(actual.isValid());
        assertTrue(actual.getErrors().isEmpty());
    }

    @Test
    public void shouldReturnFalseForInvalidDateFormats() {

        Map<String, String> params = new HashMap();
        params.put(AccountParamKeys.TO_DATE.getKey(), "123");
        params.put(AccountParamKeys.FROM_DATE.getKey(), "123");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "1");
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "10");

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add(String.format(INVALID_DATE_FORMAT, AccountParamKeys.TO_DATE.getKey()));
        expectedErrors.add(String.format(INVALID_DATE_FORMAT, AccountParamKeys.FROM_DATE.getKey()));

        ValidatorResult actual = AccountParamsValidator.validate(params);
        assertFalse(actual.isValid());
        assertEquals(expectedErrors.size(), actual.getErrors().size());
        assertTrue(actual.getErrors().containsAll(expectedErrors));
    }

    @Test
    public void shouldReturnFalseForInvalidAmounts() {

        Map<String, String> params = new HashMap();
        params.put(AccountParamKeys.TO_DATE.getKey(), "2021-05-10");
        params.put(AccountParamKeys.FROM_DATE.getKey(), "2021-01-10");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "abc");
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "def");

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add(String.format(INVALID_NUMBER, AccountParamKeys.TO_AMOUNT.getKey()));
        expectedErrors.add(String.format(INVALID_NUMBER, AccountParamKeys.FROM_AMOUNT.getKey()));

        ValidatorResult actual = AccountParamsValidator.validate(params);
        assertFalse(actual.isValid());
        assertEquals(expectedErrors.size(), actual.getErrors().size());
        assertTrue(actual.getErrors().containsAll(expectedErrors));
    }

    @Test
    public void shouldReturnFalseForInvalidRanges() {

        Map<String, String> params = new HashMap();
        params.put(AccountParamKeys.TO_DATE.getKey(), "2021-01-10");
        params.put(AccountParamKeys.FROM_DATE.getKey(), "2021-05-10");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "10");
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "1");

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add(String.format(INVALID_DATE_RANGE, AccountParamKeys.TO_DATE.getKey(), AccountParamKeys.FROM_DATE.getKey()));
        expectedErrors.add(String.format(INVALID_NUMBER_RANGE, AccountParamKeys.TO_AMOUNT.getKey(), AccountParamKeys.FROM_AMOUNT.getKey()));

        ValidatorResult actual = AccountParamsValidator.validate(params);
        assertFalse(actual.isValid());
        assertEquals(expectedErrors.size(), actual.getErrors().size());
        assertTrue(actual.getErrors().containsAll(expectedErrors));
    }
}
