package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FamilyTreesRepository {
    private Connection connection = DbManager.getInstance().getConnection();
    private FamilyTreesMapper familyTreesMapper = new FamilyTreesMapper();

    public void createFamilyTree(String familyName) throws SQLException {
        String statement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + familyName + "')";
        executeUpdate(statement);
    }

    public List<FamilyTreeDTO> getAllFamilyTrees() throws SQLException {
        ResultSet resultSet = query("SELECT * FROM FAMILY_TREES");
        return familyTreesMapper.map(resultSet);
    }

    private void executeUpdate(String statement) throws SQLException {
        connection.createStatement().executeUpdate(statement);
    }

    private ResultSet query(String statement) throws SQLException {
        return connection.createStatement().executeQuery(statement);
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    void setFamilyTreesMapper(FamilyTreesMapper familyTreesMapper) {
        this.familyTreesMapper = familyTreesMapper;
    }
}
