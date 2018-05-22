package com.wright.ftm.services;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.repositories.FamilyTreeMembersRepository;
import com.wright.ftm.repositories.FamilyTreesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyTreeMembersService {
    private final Logger logger = LoggerFactory.getLogger(FamilyTreeMembersService.class);
    private FamilyTreeMembersRepository familyTreeMembersRepository = new FamilyTreeMembersRepository();
    private FamilyTreesRepository familyTreesRepository = new FamilyTreesRepository();

    public FamilyTreeMemberDTO createFamilyMember(FamilyTreeMemberDTO familyTreeMemberDTO, FamilyTreeDTO familyTreeDTO) {
        try {
            logger.info("Creating " + familyTreeDTO.getName() + " family member...");
            familyTreeMemberDTO.setFamilyTree(familyTreeDTO);
            familyTreeMemberDTO.setId(familyTreeMembersRepository.createFamilyMember(familyTreeMemberDTO));
            logger.info("Family member created");
            return familyTreeMemberDTO;
        } catch (SQLException e) {
            logger.error("Error creating family member: " + e.getMessage());
            return null;
        }
    }

    public List<FamilyTreeMemberDTO> getFamilyMembers(FamilyTreeDTO familyTreeDTO) {
        try {
            logger.info("Getting " + familyTreeDTO.getName() + " family members...");
            List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersRepository.getFamilyMembers(familyTreeDTO.getId());
            logger.info("Found " + familyTreeMemberDTOs.size() + " family members");

            for (FamilyTreeMemberDTO familyTreeMemberDTO : familyTreeMemberDTOs) {
                familyTreeMemberDTO.setFamilyTree(familyTreesRepository.getFamilyTree(familyTreeMemberDTO.getFamilyTreeId()));
            }

            return familyTreeMemberDTOs;
        } catch (SQLException e) {
            logger.error("Error getting family members: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    void setFamilyTreeMembersRepository(FamilyTreeMembersRepository familyTreeMembersRepository) {
        this.familyTreeMembersRepository = familyTreeMembersRepository;
    }

    void setFamilyTreesRepository(FamilyTreesRepository familyTreesRepository) {
        this.familyTreesRepository = familyTreesRepository;
    }
}
