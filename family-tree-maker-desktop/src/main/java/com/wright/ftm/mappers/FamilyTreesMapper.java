package com.wright.ftm.mappers;

import com.wright.ftm.dtos.FamilyTreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FamilyTreesMapper {
    private final Logger logger = LoggerFactory.getLogger(FamilyTreesMapper.class);

    public List<FamilyTreeDTO> map(ResultSet resultSet) {
        ArrayList<FamilyTreeDTO> familyTreeDTOs = new ArrayList<>();

        try {
            while (resultSet.next()) {
                FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
                familyTreeDTO.setId(resultSet.getInt("ID"));
                familyTreeDTO.setName(resultSet.getString("NAME"));

                familyTreeDTOs.add(familyTreeDTO);
            }
        } catch (SQLException e) {
            logger.error("Error mapping family trees: " + e.getMessage());
            return Collections.emptyList();
        }

        return familyTreeDTOs;
    }
}
