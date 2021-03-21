package com.brightminds.test.dao.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Statement {

    private String accountId;
    private String accountNumber;
    private Date date;
    private BigDecimal amount;

    public Statement(String accountId, String accountNumber, Date date, BigDecimal amount) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.date = date;
        this.amount = amount;
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
