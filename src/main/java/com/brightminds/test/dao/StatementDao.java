package com.brightminds.test.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.configuration.constants.DateFormats;
import com.brightminds.test.dao.bean.Statement;
import com.brightminds.test.dao.helper.StatementQueryGeneratorHelper;

public class StatementDao {

    private static final String DEFAULT_FROM_DATE = "1970-01-01";

    private final Connection connection;

    StatementDao(Connection connection) {
        this.connection = connection;
    }

    public List<Statement> getStatements(String accountId, Map<String, String> params) throws SQLException, ParseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = StatementQueryGeneratorHelper.createStatementQueryString();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountId);
            preparedStatement.setString(2, params.getOrDefault(AccountParamKeys.FROM_DATE.getKey(), DEFAULT_FROM_DATE));
            preparedStatement.setString(3, params.getOrDefault(AccountParamKeys.TO_DATE.getKey(), DateFormats.QUERY_DATE_FORMAT.getFormat().format(new Date())));
            preparedStatement.setString(4, params.getOrDefault(AccountParamKeys.FROM_AMOUNT.getKey(), Double.toString(Double.MIN_VALUE)));
            preparedStatement.setString(5, params.getOrDefault(AccountParamKeys.TO_AMOUNT.getKey(), Double.toString(Double.MAX_VALUE)));

            resultSet = preparedStatement.executeQuery();

            return toList(resultSet);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }

    }

    private List<Statement> toList(ResultSet resultSet) throws SQLException, ParseException {
        List<Statement> statements = new ArrayList<>();
        while (resultSet.next()) {
            Statement statement = new Statement(resultSet.getString("account_ID"),
                resultSet.getString("account_number"),
                DateFormats.DB_RESULT_DATE_FORMAT.getFormat().parse(resultSet.getString("formatted_date")),
                new BigDecimal(resultSet.getString("dec_amount")));
            statements.add(statement);
        }

        return statements;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
