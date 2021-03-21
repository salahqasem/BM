package com.brightminds.test.configuration.constants;

public enum AccountParamKeys {
    FROM_DATE("from_date"), TO_DATE("to_date"),
    FROM_AMOUNT("from_amount"), TO_AMOUNT("to_amount");

    private String key;

    AccountParamKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

