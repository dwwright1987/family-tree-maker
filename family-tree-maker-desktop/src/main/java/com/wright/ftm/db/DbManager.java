package com.wright.ftm.db;

import com.wright.ftm.Constants;
import com.wright.ftm.services.DbConfigurationService;
import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.wright.ftm.Constants.WINDOWS_ROOT_APP_STORAGE_PATH;

public class DbManager {
    private final Logger logger = LoggerFactory.getLogger(DbManager.class);
    private static DbManager instance;
    private ClassWrapper classWrapper = new ClassWrapper();
    private Connection connection;
    private String derbyUrl = "jdbc:derby:" + WINDOWS_ROOT_APP_STORAGE_PATH + "/db/derbyDB;create=true";

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
            classWrapper.forName(Constants.DERBY_EMBEDDED_DRIVER_CLASS);
            connection = driverManagerWrapper.getConnection(derbyUrl);
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

    public boolean execute(String statement) throws SQLException {
        return connection.createStatement().execute(statement);
    }

    public int insert(String statement) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            return Constants.INVALID_INSERT_ID;
        }
    }

    public void update(String statement) throws SQLException {
        connection.createStatement().executeUpdate(statement);
    }

    public ResultSet query(String statement) throws SQLException {
        return connection.createStatement().executeQuery(statement);
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

    public void setDerbyUrl(String derbyUrl) {
        this.derbyUrl = derbyUrl;
    }

    void setDriverManagerWrapper(DriverManagerWrapper driverManagerWrapper) {
        this.driverManagerWrapper = driverManagerWrapper;
    }
}
