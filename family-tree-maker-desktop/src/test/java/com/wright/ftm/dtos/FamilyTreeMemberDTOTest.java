package com.wright.ftm.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testGetFullNameWhenThereIsAMiddleName() throws Exception {
        classToTest.setFirstName("Daniel");
        classToTest.setMiddleName("Woolf");
        classToTest.setLastName("Wright");

        String expectedFullName = classToTest.getFirstName() + " " + classToTest.getMiddleName() + " " + classToTest.getLastName();

        assertEquals(expectedFullName, classToTest.getFullName());
    }

    @Test
    void testGetFullNameWhenThereIsNoMiddleName() throws Exception {
        classToTest.setFirstName("Daniel");
        classToTest.setLastName("Wright");

        String expectedFullName = classToTest.getFirstName() + " " + classToTest.getLastName();

        assertEquals(expectedFullName, classToTest.getFullName());
    }

    @Test
    void testParentsIsEmptyBeDefault() throws Exception {
        assertTrue(classToTest.getParents().isEmpty());
    }

    @Test
    void testGetParentIdsReturnsCommaSeparatedStringOfParentIds() throws Exception {
        FamilyTreeMemberDTO parent1 = new FamilyTreeMemberDTO();
        parent1.setId(1);

        FamilyTreeMemberDTO parent2 = new FamilyTreeMemberDTO();
        parent2.setId(2);

        classToTest.addParent(parent1);
        classToTest.addParent(parent2);

        List<String> expectedParentIds = classToTest.getParents().stream().map(parent -> Integer.toString(parent.getId())).collect(Collectors.toList());

        assertEquals(String.join(",", expectedParentIds), classToTest.getParentIds());
    }
}
