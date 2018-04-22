package com.wright.ftm.services;

import com.wright.ftm.repositories.FamilyTreesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class FamilyTreesService {
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

    void setFamilyTreesRepository(FamilyTreesRepository familyTreesRepository) {
        this.familyTreesRepository = familyTreesRepository;
    }
}
