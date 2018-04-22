package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;

import java.sql.Connection;
import java.sql.SQLException;

public class FamilyTreesRepository {
    private Connection connection = DbManager.getInstance().getConnection();

    public void createFamilyTree(String familyName) throws SQLException {
        String statement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + familyName + "')";
        executeStatement(statement);
    }

    private void executeStatement(String statementString) throws SQLException {
        connection.createStatement().executeUpdate(statementString);
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
