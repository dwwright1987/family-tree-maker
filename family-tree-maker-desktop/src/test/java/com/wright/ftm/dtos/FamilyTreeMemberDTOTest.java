package com.wright.ftm.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void testHandlesSettingNullFamilyTree() throws Exception {
        classToTest.setFamilyTree(null);
        assertNull(classToTest.getFamilyTree());
        assertEquals(0, classToTest.getFamilyTreeId());
    }

    @Test
    void testMiddleNameIsAnEmptyStringWhenNotSet() throws Exception {
        assertEquals(0, classToTest.getMiddleName().length());
    }

    @Test
    void testMiddleNameIsSetToGiveNameWhenNotNull() throws Exception {
        String expectedMiddleName = "Woolf";

        classToTest.setMiddleName(expectedMiddleName);

        assertEquals(expectedMiddleName, classToTest.getMiddleName());
    }

    @Test
    void testMiddleNamesIsEmptyWhenSettingItToNull() throws Exception {
        classToTest.setMiddleName(null);

        assertEquals(0, classToTest.getMiddleName().length());
    }
}
