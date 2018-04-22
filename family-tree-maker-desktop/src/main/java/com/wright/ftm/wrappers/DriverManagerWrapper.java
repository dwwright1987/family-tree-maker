package com.wright.ftm.wrappers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerWrapper {
    public Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }
}
