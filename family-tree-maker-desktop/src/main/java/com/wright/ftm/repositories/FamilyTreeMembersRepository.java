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
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("INSERT INTO ").append(FAMILY_TREE_MEMBERS_TABLE_NAME).append(" (FAMILY_TREE_ID, SEX, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DATE");
        if (familyTreeMemberDTO.getDeathDate() != null) {
            statementBuilder.append(", DEATH_DATE");
        }
        statementBuilder.append(") VALUES (");
        statementBuilder.append(familyTreeMemberDTO.getFamilyTreeId());
        statementBuilder.append(", ").append(familyTreeMemberDTO.getSex().ordinal());
        statementBuilder.append(", '").append(familyTreeMemberDTO.getFirstName()).append("'");
        statementBuilder.append(", '").append(familyTreeMemberDTO.getMiddleName()).append("'");
        statementBuilder.append(", '").append(familyTreeMemberDTO.getLastName()).append("'");
        statementBuilder.append(", '").append(familyTreeMemberDTO.getBirthDate()).append("'");
        if (familyTreeMemberDTO.getDeathDate() != null) {
            statementBuilder.append(", '").append(familyTreeMemberDTO.getDeathDate()).append("'");
        }
        statementBuilder.append(")");

        return dbManager.insert(statementBuilder.toString());
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
