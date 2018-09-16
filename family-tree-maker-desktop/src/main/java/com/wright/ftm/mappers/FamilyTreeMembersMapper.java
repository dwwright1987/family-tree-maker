package com.wright.ftm.mappers;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.dtos.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyTreeMembersMapper {
    private final Logger logger = LoggerFactory.getLogger(FamilyTreeMembersMapper.class);

    public List<FamilyTreeMemberDTO> map(ResultSet resultSet) {
        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = new ArrayList<>();

        try {
            while (resultSet.next()) {
                FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
                familyTreeMemberDTO.setId(resultSet.getInt("ID"));
                familyTreeMemberDTO.setFamilyTreeId(resultSet.getInt("FAMILY_TREE_ID"));
                familyTreeMemberDTO.setSex(Sex.values()[resultSet.getInt("SEX")]);
                familyTreeMemberDTO.setFirstName(resultSet.getString("FIRST_NAME"));
                familyTreeMemberDTO.setMiddleName(resultSet.getString("MIDDLE_NAME"));
                familyTreeMemberDTO.setLastName(resultSet.getString("LAST_NAME"));
                familyTreeMemberDTO.setBirthDate(resultSet.getDate("BIRTH_DATE"));
                familyTreeMemberDTO.setDeathDate(resultSet.getDate("DEATH_DATE"));
                familyTreeMemberDTO.setNotes(resultSet.getString("NOTES"));
                familyTreeMemberDTO.setParentIds(resultSet.getString("PARENT_IDS"));

                familyTreeMemberDTOs.add(familyTreeMemberDTO);
            }
        } catch (SQLException e) {
            logger.error("Error mapping family tree members: " + e.getMessage());
        }

        return familyTreeMemberDTOs;
    }
}
