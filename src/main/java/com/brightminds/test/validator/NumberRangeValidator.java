package com.brightminds.test.validator;

import java.math.BigDecimal;

public class NumberRangeValidator {

    private NumberRangeValidator() {
    }

    public static boolean isValid(BigDecimal from, BigDecimal to) {

        if(from == null || to == null) {
            return false;
        }

        return to.compareTo(from) > -1;
    }
}
