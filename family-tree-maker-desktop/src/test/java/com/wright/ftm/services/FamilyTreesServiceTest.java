package com.wright.ftm.services;

import com.wright.ftm.Constants;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.repositories.FamilyTreesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreesServiceTest {
    private static final String WRIGHT_FAMILY_NAME = "Wright";
    private FamilyTreesService classToTest;
    private FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
    private FamilyTreesRepository mockFamilyTreesRepository = mock(FamilyTreesRepository.class);

    @BeforeEach
    void setUp() throws Exception {
        familyTreeDTO.setId(11);

        classToTest = new FamilyTreesService();
        classToTest.setFamilyTreesRepository(mockFamilyTreesRepository);
    }

    @Test
    void testCreateFamilyNameReturnsFamilyTreeDTOWhenThereIsNoException() throws Exception {
        int expectedId = 5;

        when(mockFamilyTreesRepository.createFamilyTree(WRIGHT_FAMILY_NAME)).thenReturn(expectedId);

        FamilyTreeDTO familyTreeDTO = classToTest.createFamilyTree(WRIGHT_FAMILY_NAME);

        assertEquals(expectedId, familyTreeDTO.getId());
        assertEquals(WRIGHT_FAMILY_NAME, familyTreeDTO.getName());
    }

    @Test
    void testCreateFamilyNameReturnsNullWhenAnInvalidIdIsReturned() throws Exception {
        when(mockFamilyTreesRepository.createFamilyTree(WRIGHT_FAMILY_NAME)).thenReturn(Constants.INVALID_INSERT_ID);

        assertNull(classToTest.createFamilyTree(WRIGHT_FAMILY_NAME));
    }

    @Test
    void testCreateFamilyNameReturnsNullWhenThereIsAnException() throws Exception {
        when(mockFamilyTreesRepository.createFamilyTree(WRIGHT_FAMILY_NAME)).thenThrow(new SQLException());

        assertNull(classToTest.createFamilyTree(WRIGHT_FAMILY_NAME));
    }

    @Test
    void testGetAllFamilyTreesReturnsAllFamilyTrees() throws Exception {
        List<FamilyTreeDTO> expectedFamilyTreeDTOS = new ArrayList<>();
        expectedFamilyTreeDTOS.add(familyTreeDTO);

        when(mockFamilyTreesRepository.getAllFamilyTrees()).thenReturn(expectedFamilyTreeDTOS);

        List<FamilyTreeDTO> actualFamilyTreeDTOs = classToTest.getAllFamilyTrees();

        assertEquals(expectedFamilyTreeDTOS, actualFamilyTreeDTOs);
    }

    @Test
    void testGetAllFamilyTreesReturnsEmptyListWhenThereIsAnException() throws Exception {
        when(mockFamilyTreesRepository.getAllFamilyTrees()).thenThrow(new SQLException());

        assertTrue(classToTest.getAllFamilyTrees().isEmpty());
    }

    @Test
    void testGetFamilyTreeReturnsFamilyTreeForGivenId() throws Exception {
        when(mockFamilyTreesRepository.getFamilyTree(familyTreeDTO.getId())).thenReturn(familyTreeDTO);

        assertEquals(familyTreeDTO, classToTest.getFamilyTree(familyTreeDTO.getId()));
    }

    @Test
    void testGetFamilyTreeReturnsNullWhenThereIsAnExceptions() throws Exception {
        when(mockFamilyTreesRepository.getFamilyTree(familyTreeDTO.getId())).thenThrow(new SQLException());

        assertNull(classToTest.getFamilyTree(familyTreeDTO.getId()));
    }

    @Test
    void testRemoveFamilyTreeReturnsResultOfDelete() throws Exception {
        when(mockFamilyTreesRepository.deleteFamilyTree(familyTreeDTO.getId())).thenReturn(true);

        assertTrue(classToTest.removeFamilyTree(familyTreeDTO));
    }

    @Test
    void testRemoveFamilyTreeReturnsFalseWhenThereIsAnException() throws Exception {
        when(mockFamilyTreesRepository.deleteFamilyTree(familyTreeDTO.getId())).thenThrow(new SQLException());

        assertFalse(classToTest.removeFamilyTree(familyTreeDTO));
    }
}
