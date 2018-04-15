package com.wright.ftm.db;

import com.wright.ftm.services.DbConfigurationService;
import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    private static DbManager instance;
    private ClassWrapper classWrapper = new ClassWrapper();
    private Connection connection;
    private DriverManagerWrapper driverManagerWrapper = new DriverManagerWrapper();

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }

        return instance;
    }

    public boolean startDb() {
        try {
            classWrapper.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = driverManagerWrapper.getConnection("jdbc:derby:c:/ProgramData/wright/family-tree-maker/db/derbyDB;create=true");

            return getDbConfigurationService().configure();
        } catch (Exception e) {
            return false;
        }
    }

    public void stopDb() {
        if (connection != null) {
            try {
                driverManagerWrapper.getConnection("jdbc:derby:;shutdown=true");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            connection = null;
        }
    }

    DbManager() {}

    public Connection getConnection() {
        return connection;
    }

    DbConfigurationService getDbConfigurationService() {
        return new DbConfigurationService();
    }

    void setClassWrapper(ClassWrapper classWrapper) {
        this.classWrapper = classWrapper;
    }

    void setDriverManagerWrapper(DriverManagerWrapper driverManagerWrapper) {
        this.driverManagerWrapper = driverManagerWrapper;
    }
}
