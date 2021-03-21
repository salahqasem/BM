package com.brightminds.test.validator;

import java.math.BigDecimal;


import org.apache.commons.lang3.StringUtils;

public class NumberValidator {

    private NumberValidator() {

    }

    public static boolean isValidBigDecimal(String value) {

        if (StringUtils.isEmpty(value)) {
            return false;
        }

        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
