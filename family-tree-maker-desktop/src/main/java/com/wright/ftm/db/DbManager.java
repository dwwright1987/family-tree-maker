package com.wright.ftm.db;

import com.wright.ftm.services.DbConfigurationService;
import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    private final Logger logger = LoggerFactory.getLogger(DbManager.class);
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
            logger.info("Connecting to database...");
            classWrapper.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = driverManagerWrapper.getConnection("jdbc:derby:c:/ProgramData/wright/family-tree-maker/db/derbyDB;create=true");
            logger.info("Database connection established");

            return getDbConfigurationService().configure();
        } catch (Exception e) {
            logger.error("Error while connecting to database: " + e.getMessage());
            return false;
        }
    }

    public void stopDb() {
        if (connection != null) {
            try {
                logger.info("Closing database connection...");
                driverManagerWrapper.getConnection("jdbc:derby:;shutdown=true");
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error while closing database connection: " + e.getMessage());
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
