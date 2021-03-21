package com.brightminds.test.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.configuration.constants.DateFormats;
import com.brightminds.test.dao.DaoFactory;
import com.brightminds.test.dao.StatementDao;
import com.brightminds.test.dao.bean.Statement;

@Component
public class AccountService {

    private DaoFactory daoFactory;

    public AccountService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<Statement> getStatements(String accountId, Map<String, String> params) throws SQLException, ParseException {
        StatementDao statementDao = daoFactory.createStatementDao();
        List<Statement> statements = statementDao.getStatements(accountId, params);
        statementDao.close();
        return statements;
    }

    public List<Statement> getLast3MonthsStatements(String accountId) throws SQLException, ParseException {
        Map<String, String> params = new HashMap<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -3);
        String toDate = DateFormats.QUERY_DATE_FORMAT.getFormat().format(now);
        String fromDate = DateFormats.QUERY_DATE_FORMAT.getFormat().format(calendar.getTime());
        params.put(AccountParamKeys.FROM_DATE.getKey(), fromDate);
        params.put(AccountParamKeys.TO_DATE.getKey(), toDate);
        return getStatements(accountId, params);
    }
}
