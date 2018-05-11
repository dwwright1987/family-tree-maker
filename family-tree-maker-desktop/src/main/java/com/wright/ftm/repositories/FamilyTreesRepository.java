package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FamilyTreesRepository {
    private static final String FAMILY_TREES_TABLE_NAME = "FAMILY_TREES";
    private DbManager dbManager = DbManager.getInstance();
    private FamilyTreesMapper familyTreesMapper = new FamilyTreesMapper();

    public int createFamilyTree(String familyName) throws SQLException {
        String statement = "INSERT INTO " + FAMILY_TREES_TABLE_NAME + " (NAME) VALUES ('" + familyName + "')";
        return dbManager.insert(statement);
    }

    public boolean deleteFamilyTree(int id) throws SQLException {
        String statement = "DELETE FROM " + FAMILY_TREES_TABLE_NAME + " WHERE ID = " + id;
        return !dbManager.execute(statement);
    }

    public List<FamilyTreeDTO> getAllFamilyTrees() throws SQLException {
        String statement = "SELECT * FROM " + FAMILY_TREES_TABLE_NAME;
        ResultSet resultSet = dbManager.query(statement);
        return familyTreesMapper.map(resultSet);
    }

    void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    void setFamilyTreesMapper(FamilyTreesMapper familyTreesMapper) {
        this.familyTreesMapper = familyTreesMapper;
    }
}
