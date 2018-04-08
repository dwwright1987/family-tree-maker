package com.wright.ftm.wrappers;

import sun.reflect.CallerSensitive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerWrapper {
    @CallerSensitive
    public Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }
}
