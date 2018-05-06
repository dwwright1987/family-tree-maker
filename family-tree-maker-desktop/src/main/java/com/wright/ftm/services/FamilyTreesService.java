package com.wright.ftm.services;

import com.wright.ftm.Constants;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.repositories.FamilyTreesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FamilyTreesService {
    private final Logger logger = LoggerFactory.getLogger(FamilyTreesService.class);
    private FamilyTreesRepository familyTreesRepository = new FamilyTreesRepository();

    public FamilyTreeDTO createFamilyTree(String familyName) {
        FamilyTreeDTO familyTreeDTO = null;

        try {
            int id = familyTreesRepository.createFamilyTree(familyName);
            if (id != Constants.INVALID_INSERT_ID) {
                familyTreeDTO = createFamilyTreDTO(id, familyName);
            }
        } catch (SQLException e) {
            logger.error("Error creating family tree " + familyName + ": " + e.getMessage());
        }

        return familyTreeDTO;
    }

    public List<FamilyTreeDTO> getAllFamilyTrees() {
        try {
            return familyTreesRepository.getAllFamilyTrees();
        } catch (SQLException e) {
            logger.error("Error getting family trees: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean removeFamilyTree(FamilyTreeDTO familyTreeDTO) {
        try {
            return familyTreesRepository.deleteFamilyTree(familyTreeDTO.getId());
        } catch (SQLException e) {
            logger.error("Error deleting family tree: " + e.getMessage());
            return false;
        }
    }

    private FamilyTreeDTO createFamilyTreDTO(int id, String name) {
        FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
        familyTreeDTO.setId(id);
        familyTreeDTO.setName(name);

        return familyTreeDTO;
    }

    void setFamilyTreesRepository(FamilyTreesRepository familyTreesRepository) {
        this.familyTreesRepository = familyTreesRepository;
    }
}
