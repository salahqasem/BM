package com.brightminds.test.controller.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.configuration.constants.DateFormats;
import com.brightminds.test.controller.util.bean.ValidatorResult;
import com.brightminds.test.validator.DateRangeValidator;
import com.brightminds.test.validator.DateValidator;
import com.brightminds.test.validator.NumberRangeValidator;
import com.brightminds.test.validator.NumberValidator;

public class AccountParamsValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountParamsValidator.class);

    private final static String INVALID_DATE_FORMAT = "%s is not a valid date, please use '" + DateFormats.QUERY_DATE_FORMAT.getFormat().toPattern()
        + "' as format.";

    private final static String INVALID_DATE_RANGE = "%s Must be after %s";
    private final static String INVALID_NUMBER = "%s is not a valid number.";
    private final static String INVALID_NUMBER_RANGE = " Must be greater or equal to ";

    public static ValidatorResult validate(Map<String, String> params) {

        if (params == null || params.isEmpty()) {
            return new ValidatorResult(new ArrayList(0));
        }

        List<String> errors = new ArrayList();
        List<String> dateErrors = new ArrayList();
        List<String> amountErrors = new ArrayList();

        if (params.containsKey(AccountParamKeys.FROM_DATE.getKey())) {
            if (!DateValidator.isValid(params.get(AccountParamKeys.FROM_DATE.getKey()))) {
                dateErrors.add(String.format(INVALID_DATE_FORMAT, AccountParamKeys.FROM_DATE.getKey()));
            }
        }

        if (params.containsKey(AccountParamKeys.TO_DATE.getKey())) {
            if (!DateValidator.isValid(params.get(AccountParamKeys.TO_DATE.getKey()))) {
                dateErrors.add(String.format(INVALID_DATE_FORMAT, AccountParamKeys.TO_DATE.getKey()));
            }
        }


        if (dateErrors.isEmpty() && params.containsKey(AccountParamKeys.TO_DATE.getKey())
            && params.containsKey(AccountParamKeys.FROM_DATE.getKey())) {

            try {
                Date from = DateFormats.QUERY_DATE_FORMAT.getFormat().parse(params.get(AccountParamKeys.FROM_DATE.getKey()));
                Date to = DateFormats.QUERY_DATE_FORMAT.getFormat().parse(params.get(AccountParamKeys.TO_DATE.getKey()));
                if (!DateRangeValidator.isValid(from, to)) {
                    dateErrors.add(String.format(INVALID_DATE_RANGE, AccountParamKeys.TO_DATE.getKey(), AccountParamKeys.FROM_DATE.getKey()));
                }
            } catch (ParseException ex) {
                //this error should not happen because the dates validated above.
                LOGGER.error(ex.getMessage(), ex);
            }

        }

        if (params.containsKey(AccountParamKeys.FROM_AMOUNT.getKey())) {
            if (!NumberValidator.isValidBigDecimal(params.get(AccountParamKeys.FROM_AMOUNT.getKey()))) {
                amountErrors.add(String.format(INVALID_NUMBER, AccountParamKeys.FROM_AMOUNT.getKey()));
            }
        }

        if (params.containsKey(AccountParamKeys.TO_AMOUNT.getKey())) {
            if (!NumberValidator.isValidBigDecimal(params.get(AccountParamKeys.TO_AMOUNT.getKey()))) {
                amountErrors.add(String.format(INVALID_NUMBER, AccountParamKeys.TO_AMOUNT.getKey()));
            }
        }

        if (amountErrors.isEmpty() && params.containsKey(AccountParamKeys.TO_AMOUNT.getKey())
            && params.containsKey(AccountParamKeys.FROM_AMOUNT.getKey())) {

            if (!NumberRangeValidator.isValid(new BigDecimal(params.get(AccountParamKeys.FROM_AMOUNT.getKey())),
                new BigDecimal(params.get(AccountParamKeys.TO_AMOUNT.getKey())))) {
                amountErrors.add(String.format(INVALID_NUMBER_RANGE, AccountParamKeys.TO_AMOUNT.getKey(), AccountParamKeys.FROM_AMOUNT.getKey()));
            }
        }

        errors.addAll(dateErrors);
        errors.addAll(amountErrors);
        return new ValidatorResult(errors);
    }
}
