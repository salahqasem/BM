package com.brightminds.test.validator;

import java.util.regex.Pattern;


import org.apache.commons.lang3.StringUtils;

public class DateValidator {

    private static Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private DateValidator() {
    }

    public static boolean isValid(String date) {

        if (StringUtils.isEmpty(date)) {
            return false;
        }

        return DATE_PATTERN.matcher(date).matches();
    }
}
