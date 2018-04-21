package com.wright.ftm.services;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.repositories.TablesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbConfigurationService {
    private final Logger logger = LoggerFactory.getLogger(DbConfigurationService.class);
    private Connection connection = DbManager.getInstance().getConnection();
    private JarFileReaderService jarFileReaderService = new JarFileReaderService();
    private TablesRepository tablesRepository = new TablesRepository(connection);

    public boolean configure() {
        try {
            logger.info("Configuring database...");
            List<String> tableNames = tablesRepository.query();
            if (tableNames.contains("DB_VERSION")) {
                executeStatement("SET SCHEMA FAMILY_TREE_MAKER");
            } else {
                runInitialConfiguration();
            }
            logger.info("Database configuration complete");

            return true;
        } catch (SQLException | IOException e) {
            logger.error("Error while configuring database: " + e.getMessage());
            return false;
        }
    }

    private void runInitialConfiguration() throws IOException, SQLException {
        logger.info("Running initial configuration...");
        executeScript("/scripts/db/initial-schema-000001.sql");
        logger.info("Initial configuration complete");
    }

    private void executeScript(String scriptJarPath) throws IOException, SQLException {
        logger.info("Executing configuration script: " + scriptJarPath + "...");

        String scriptContents = jarFileReaderService.readAsString(scriptJarPath);
        String[] statements = scriptContents.trim().split(";");
        for (String statement : statements) {
            executeStatement(statement.trim());
        }

        logger.info("Script execution complete");
    }

    private void executeStatement(String statementString) throws SQLException {
        connection.createStatement().executeUpdate(statementString);
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    void setJarFileReaderService(JarFileReaderService jarFileReaderService) {
        this.jarFileReaderService = jarFileReaderService;
    }

    void setTablesRepository(TablesRepository tablesRepository) {
        this.tablesRepository = tablesRepository;
    }
}
