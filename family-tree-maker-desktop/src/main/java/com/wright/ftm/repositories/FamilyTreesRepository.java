package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FamilyTreesRepository {
    private DbManager dbManager = DbManager.getInstance();
    private FamilyTreesMapper familyTreesMapper = new FamilyTreesMapper();

    public void createFamilyTree(String familyName) throws SQLException {
        String statement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + familyName + "')";
        dbManager.update(statement);
    }

    public List<FamilyTreeDTO> getAllFamilyTrees() throws SQLException {
        ResultSet resultSet = dbManager.query("SELECT * FROM FAMILY_TREES");
        return familyTreesMapper.map(resultSet);
    }

    void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    void setFamilyTreesMapper(FamilyTreesMapper familyTreesMapper) {
        this.familyTreesMapper = familyTreesMapper;
    }
}
