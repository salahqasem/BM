package com.brightminds.test.configuration.constants;

import java.text.SimpleDateFormat;

public enum DateFormats {
    QUERY_DATE_FORMAT(new SimpleDateFormat("yyyy-MM-dd")),
    DB_RESULT_DATE_FORMAT(new SimpleDateFormat("dd/MM/yyyy"));

    private SimpleDateFormat format;

    DateFormats(SimpleDateFormat format) {
        this.format = format;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }
}
