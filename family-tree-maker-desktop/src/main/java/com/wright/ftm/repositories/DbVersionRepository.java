package com.wright.ftm.repositories;

import java.sql.Connection;

public class DbVersionRepository {
    private Connection connection;

    public DbVersionRepository(Connection connection) {
        this.connection = connection;
    }
}
