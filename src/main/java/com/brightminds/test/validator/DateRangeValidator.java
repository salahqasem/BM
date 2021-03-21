package com.brightminds.test.validator;

import java.util.Date;

public class DateRangeValidator {

    private DateRangeValidator() {
        
    }

    public static boolean isValid(Date from, Date to) {

        if(from == null || to == null) {
            return false;
        }

        return to.after(from);
    }

}
