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
        if (familyTreeMemberDTO.getNotes() != null) {
            statementBuilder.append(", NOTES");
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
        if (familyTreeMemberDTO.getNotes() != null) {
            statementBuilder.append(", '").append(familyTreeMemberDTO.getNotes()).append("'");
        }
        statementBuilder.append(")");

        return dbManager.insert(statementBuilder.toString());
    }

    public List<FamilyTreeMemberDTO> getFamilyMembers(int familyTreeId) throws SQLException {
        String statement = "SELECT * FROM " + FAMILY_TREE_MEMBERS_TABLE_NAME + " WHERE FAMILY_TREE_ID = " + familyTreeId;
        ResultSet resultSet = dbManager.query(statement);
        return familyTreeMembersMapper.map(resultSet);
    }

    public void updateFamilyMember(FamilyTreeMemberDTO familyTreeMemberDTO) throws SQLException {
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("UPDATE ").append(FAMILY_TREE_MEMBERS_TABLE_NAME).append(" SET ");
        statementBuilder.append("FAMILY_TREE_ID=").append(familyTreeMemberDTO.getFamilyTreeId()).append(", ");
        statementBuilder.append("SEX=").append(familyTreeMemberDTO.getSex().ordinal()).append(", ");
        statementBuilder.append("FIRST_NAME='").append(familyTreeMemberDTO.getFirstName()).append("', ");
        statementBuilder.append("MIDDLE_NAME='").append(familyTreeMemberDTO.getMiddleName()).append("', ");
        statementBuilder.append("LAST_NAME='").append(familyTreeMemberDTO.getLastName()).append("', ");
        statementBuilder.append("BIRTH_DATE='").append(familyTreeMemberDTO.getBirthDate()).append("', ");
        statementBuilder.append("DEATH_DATE=").append(getUpdateValue(familyTreeMemberDTO.getDeathDate())).append(", ");
        statementBuilder.append("NOTES=").append(getUpdateValue(familyTreeMemberDTO.getNotes())).append(" ");
//        statementBuilder.append("DEATH_DATE='").append(familyTreeMemberDTO.getDeathDate()).append("', ");
//        statementBuilder.append("NOTES='").append(familyTreeMemberDTO.getNotes()).append("' ");
        statementBuilder.append("WHERE ID=").append(familyTreeMemberDTO.getId());

        dbManager.update(statementBuilder.toString());
    }

    private Object getUpdateValue(Object value) {
        return value == null ? "NULL" : "'" + value + "'";
    }

    void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    void setFamilyTreeMembersMapper(FamilyTreeMembersMapper familyTreeMembersMapper) {
        this.familyTreeMembersMapper = familyTreeMembersMapper;
    }
}
