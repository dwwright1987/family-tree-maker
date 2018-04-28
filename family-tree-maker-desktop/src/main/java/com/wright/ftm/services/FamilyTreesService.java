package com.wright.ftm.services;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.repositories.FamilyTreesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FamilyTreesService { //TODO: Integration tests
    private final Logger logger = LoggerFactory.getLogger(FamilyTreesService.class);
    private FamilyTreesRepository familyTreesRepository = new FamilyTreesRepository();

    public boolean createFamilyTree(String familyName) {
        try {
            familyTreesRepository.createFamilyTree(familyName);
            return true;
        } catch (SQLException e) {
            logger.error("Error creating family tree " + familyName + ": " + e.getMessage());
            return false;
        }
    }

    public List<FamilyTreeDTO> getAllFamilyTrees() {
        try {
            return familyTreesRepository.getAllFamilyTrees();
        } catch (SQLException e) {
            logger.error("Error getting family trees: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    void setFamilyTreesRepository(FamilyTreesRepository familyTreesRepository) {
        this.familyTreesRepository = familyTreesRepository;
    }
}
