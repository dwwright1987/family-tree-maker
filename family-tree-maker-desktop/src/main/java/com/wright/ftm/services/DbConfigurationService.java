package com.wright.ftm.services;

import java.sql.Connection;

public class DbConfigurationService {
    private Connection connection;

    public DbConfigurationService(Connection connection) {
        this.connection = connection;
    }

    public void configure() {

    }
}
