package com.brightminds.test.service;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.configuration.constants.DateFormats;
import com.brightminds.test.dao.DaoFactory;
import com.brightminds.test.dao.StatementDao;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService actual;

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private StatementDao statementDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestGetStatements() throws SQLException, ParseException {

        Map<String, String> params = new HashMap<>();
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "10");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "1");

        when(statementDao.getStatements("123", params)).thenReturn(Collections.emptyList());
        when(daoFactory.createStatementDao()).thenReturn(statementDao);

        actual.getStatements("123", params);

        verify(daoFactory, times(1)).createStatementDao();
        verify(statementDao, times(1)).getStatements("123", params);
        verify(statementDao, times(1)).close();
    }

    @Test
    public void TestGetLast3MonthsStatements() throws SQLException, ParseException {

        Map<String, String> params = new HashMap<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -3);
        String toDate = DateFormats.QUERY_DATE_FORMAT.getFormat().format(now);
        String fromDate = DateFormats.QUERY_DATE_FORMAT.getFormat().format(calendar.getTime());
        params.put(AccountParamKeys.FROM_DATE.getKey(), fromDate);
        params.put(AccountParamKeys.TO_DATE.getKey(), toDate);

        when(statementDao.getStatements("123", params)).thenReturn(Collections.emptyList());
        when(daoFactory.createStatementDao()).thenReturn(statementDao);

        AccountService actual = new AccountService(daoFactory);
        actual.getLast3MonthsStatements("123");

        verify(daoFactory, times(1)).createStatementDao();
        verify(statementDao, times(1)).getStatements("123", params);
        verify(statementDao, times(1)).close();
    }

    @Test
    public void TestGetStatementsException() throws SQLException, ParseException {

        Map<String, String> params = new HashMap<>();
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), "10");
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), "1");

        when(statementDao.getStatements("123", params)).thenThrow(SQLException.class);
        when(daoFactory.createStatementDao()).thenReturn(statementDao);

        AccountService actual = new AccountService(daoFactory);

        Assertions.assertThrows(SQLException.class, () -> actual.getStatements("123", params));
        verify(daoFactory, times(1)).createStatementDao();
        verify(statementDao, times(1)).getStatements("123", params);
        verify(statementDao, times(0)).close();
    }
}
