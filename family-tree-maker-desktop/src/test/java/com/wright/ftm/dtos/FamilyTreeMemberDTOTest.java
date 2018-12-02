package com.wright.ftm.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
    void testParentsIsEmptyByDefault() throws Exception {
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

    @Test
    void testChildrenIsEmptyByDefault() throws Exception {
        assertTrue(classToTest.getChildren().isEmpty());
    }

    @Test
    void testGetChildIdsReturnsCommaSeparatedStringOfChildIds() throws Exception {
        FamilyTreeMemberDTO child1 = new FamilyTreeMemberDTO();
        child1.setId(1);

        FamilyTreeMemberDTO child2 = new FamilyTreeMemberDTO();
        child2.setId(2);

        classToTest.addChild(child1);
        classToTest.addChild(child2);

        List<String> expectedChildIds = classToTest.getChildren().stream().map(child -> Integer.toString(child.getId())).collect(Collectors.toList());

        assertEquals(String.join(",", expectedChildIds), classToTest.getChildIds());
    }

    @Test
    void testAddingParentAddsFamilyMemberAsChild() throws Exception {
        FamilyTreeMemberDTO child = new FamilyTreeMemberDTO();
        child.setId(1);
        FamilyTreeMemberDTO parent = new FamilyTreeMemberDTO();
        parent.setId(2);

        child.addParent(parent);

        List<FamilyTreeMemberDTO> children = parent.getChildren();
        assertEquals(1, children.size());
        assertEquals(child.getId(), children.get(0).getId());
    }

    @Test
    void testAddingChildAddsFamilyMemberAsParent() throws Exception {
        FamilyTreeMemberDTO child = new FamilyTreeMemberDTO();
        child.setId(1);
        FamilyTreeMemberDTO parent = new FamilyTreeMemberDTO();
        parent.setId(2);

        parent.addChild(child);

        List<FamilyTreeMemberDTO> parents = child.getParents();
        assertEquals(1, parents.size());
        assertEquals(parent.getId(), parents.get(0).getId());
    }

    @Test
    void testParentsAndChildrenDoNotGetDuplicated() throws Exception {
        FamilyTreeMemberDTO child = new FamilyTreeMemberDTO();
        child.setId(1);
        FamilyTreeMemberDTO parent = new FamilyTreeMemberDTO();
        parent.setId(2);

        child.addParent(parent);
        child.addParent(parent);

        List<FamilyTreeMemberDTO> parents = child.getParents();
        assertEquals(1, parents.size());
        assertEquals(parent.getId(), parents.get(0).getId());

        List<FamilyTreeMemberDTO> children = parent.getChildren();
        assertEquals(1, children.size());
        assertEquals(child.getId(), children.get(0).getId());
    }

    @Test
    void testBadChildrenAndParentsAreNotAdded() throws Exception {
        FamilyTreeMemberDTO child = new FamilyTreeMemberDTO();
        child.setId(1);
        FamilyTreeMemberDTO parent = new FamilyTreeMemberDTO();
        parent.setId(2);

        child.addParent(null);
        assertTrue(child.getParents().isEmpty());

        parent.addChild(null);
        assertTrue(parent.getChildren().isEmpty());
    }
}
