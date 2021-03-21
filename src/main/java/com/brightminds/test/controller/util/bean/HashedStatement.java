package com.brightminds.test.controller.util.bean;

import java.math.BigDecimal;
import java.util.Date;


import com.brightminds.test.dao.bean.Statement;

public class HashedStatement {


    private static final String hashed = "XXXXXX";

    private String accountId;
    private String accountNumber;
    private Date date;
    private BigDecimal amount;

    public HashedStatement(Statement statement) {
        this.accountId = statement.getAccountId();
        this.accountNumber = statement.getAccountNumber().substring(0, 4) + hashed + statement.getAccountNumber().substring(10);
        this.date = statement.getDate();
        this.amount = statement.getAmount();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
