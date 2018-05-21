package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.mappers.FamilyTreeMembersMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FamilyTreeMembersRepository {
    private static final String FAMILY_TREE_MEMBERS_TABLE_NAME = "FAMILY_TREE_MEMBERS";
    private DbManager dbManager = DbManager.getInstance();
    private FamilyTreeMembersMapper familyTreeMembersMapper = new FamilyTreeMembersMapper();

    public int createFamilyMember(FamilyTreeMemberDTO familyTreeMemberDTO) throws SQLException {
        String statement = "INSERT INTO " + FAMILY_TREE_MEMBERS_TABLE_NAME + " (FAMILY_TREE_ID, SEX) VALUES (" +
            familyTreeMemberDTO.getFamilyTreeId() + ", " +
            familyTreeMemberDTO.getSex().ordinal() +
        ")";

        return dbManager.insert(statement);
    }

    public List<FamilyTreeMemberDTO> getFamilyMembers(int familyTreeId) throws SQLException {
        String statement = "SELECT * FROM " + FAMILY_TREE_MEMBERS_TABLE_NAME + " WHERE FAMILY_TREE_ID = " + familyTreeId;
        ResultSet resultSet = dbManager.query(statement);
        return familyTreeMembersMapper.map(resultSet);
    }

    void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    void setFamilyTreeMembersMapper(FamilyTreeMembersMapper familyTreeMembersMapper) {
        this.familyTreeMembersMapper = familyTreeMembersMapper;
    }
}
