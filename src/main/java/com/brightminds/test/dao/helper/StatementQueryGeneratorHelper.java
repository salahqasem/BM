package com.brightminds.test.dao.helper;

public class StatementQueryGeneratorHelper {

    private static final String INNER_QUERY = "(select Statement.account_id, Account.account_number, CDEC(val(Statement.amount)) AS dec_amount, " +
        "Format(CDate(Replace(datefield, \".\", \"-\")), \"dd/mm/yyyy\") AS formatted_date from statement, account" +
        " WHERE Statement.account_id = Account.id AND Statement.account_id = ? ) AS Q1 ";


    public static String createStatementQueryString() {
        StringBuilder query = new StringBuilder();

        query.append("select * from ");
        query.append(INNER_QUERY);
        query.append("where CDate(formatted_date) >= ? AND CDate(formatted_date) <= ? ");
        query.append("AND ");
        query.append("CDec(dec_amount) >= ? AND  CDec(dec_amount) <= ?");

        return query.toString();
    }


}
