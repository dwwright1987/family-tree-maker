package com.wright.ftm.services;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.repositories.TablesRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbConfigurationService {
    private Connection connection = DbManager.getInstance().getConnection();
    private JarFileReaderService jarFileReaderService = new JarFileReaderService();
    private TablesRepository tablesRepository = new TablesRepository(connection);

    public boolean configure() {
        try {
            List<String> tableNames = tablesRepository.query();
            if (tableNames.contains("DB_VERSION")) {
                executeStatement("SET SCHEMA FAMILY_TREE_MAKER");
            } else {
                runInitialConfiguration();
            }

            return true;
        } catch (SQLException | IOException e) {
            return false;
        }
    }

    private void runInitialConfiguration() throws IOException, SQLException {
        executeScript("/scripts/db/initial-schema-000001.sql");
    }

    private void executeScript(String scriptJarPath) throws IOException, SQLException {
        String scriptContents = jarFileReaderService.readAsString(scriptJarPath);
        String[] statements = scriptContents.trim().split(";");
        for (String statement : statements) {
            executeStatement(statement.trim());
        }
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
