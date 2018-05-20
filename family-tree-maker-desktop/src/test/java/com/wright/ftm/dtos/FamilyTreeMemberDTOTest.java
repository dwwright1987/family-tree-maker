package com.wright.ftm.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FamilyTreeMemberDTOTest {
    private FamilyTreeMemberDTO classToTest;
    private FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();

    @BeforeEach
    void setUp() throws Exception {
        familyTreeDTO.setId(26);

        classToTest = new FamilyTreeMemberDTO();
    }

    @Test
    void testSettingFamilyTreeSetsFamilyTreeId() throws Exception {
        classToTest.setFamilyTree(familyTreeDTO);

        assertEquals(familyTreeDTO.getId(), classToTest.getFamilyTreeId());
    }
}