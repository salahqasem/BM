package com.brightminds.test.controller.util;

import java.math.BigDecimal;
import java.util.Date;


import org.junit.jupiter.api.Test;

import com.brightminds.test.controller.util.bean.HashedStatement;
import com.brightminds.test.dao.bean.Statement;

import static org.junit.Assert.assertEquals;

public class HashedStatementTest {

    @Test
    public void testHashedStatement() {
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        String expected = "1234XXXXXX123";
        HashedStatement actual = new HashedStatement(statement);

        assertEquals(expected, actual.getAccountNumber());
        assertEquals(statement.getAccountId(), actual.getAccountId());
        assertEquals(statement.getAmount(), actual.getAmount());
        assertEquals(statement.getDate(), actual.getDate());
    }

}
