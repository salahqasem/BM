package com.brightminds.test.dao;

import java.sql.SQLException;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

    private static final String DB_DRIVER_NAME = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static final int INITIAL_SIZE = 3;
    private static final int MAX_IDLE_SIZE = 5;
    private static final int MAX_TOTAL = 10;

    private static BasicDataSource dataSource = new BasicDataSource();

    private DaoFactory() {
        init();
    }

    private void init() {
        dataSource.setDriverClassName(DB_DRIVER_NAME);
        dataSource.setUrl("jdbc:ucanaccess://" + getClass().getResource("/accountsdb.accdb").getPath());
        dataSource.setInitialSize(INITIAL_SIZE);
        dataSource.setMaxIdle(MAX_IDLE_SIZE);
        dataSource.setMaxTotal(MAX_TOTAL);
    }

    public StatementDao createStatementDao() throws SQLException {
        return new StatementDao(dataSource.getConnection());
    }
}
