package com.wright.ftm.services;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.repositories.FamilyTreesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FamilyTreesServiceTest {
    private static final String WRIGHT_FAMILY_NAME = "Wright";
    private FamilyTreesService classToTest;
    private FamilyTreesRepository mockFamilyTreesRepository = mock(FamilyTreesRepository.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreesService();
        classToTest.setFamilyTreesRepository(mockFamilyTreesRepository);
    }

    @Test
    void testCreateFamilyNameReturnsTrueWhenThereIsNoException() throws Exception {
        assertTrue(classToTest.createFamilyTree(WRIGHT_FAMILY_NAME));
    }

    @Test
    void testCreateFamilyNameReturnsFalseWhenThereIsAnException() throws Exception {
        doThrow(new SQLException()).when(mockFamilyTreesRepository).createFamilyTree(WRIGHT_FAMILY_NAME);

        assertFalse(classToTest.createFamilyTree(WRIGHT_FAMILY_NAME));
    }

    @Test
    void testGetAllFamilyTreesReturnsAllFamilyTrees() throws Exception {
        List<FamilyTreeDTO> expectedFamilyTreeDTOS = new ArrayList<>();
        expectedFamilyTreeDTOS.add(new FamilyTreeDTO());

        when(mockFamilyTreesRepository.getAllFamilyTrees()).thenReturn(expectedFamilyTreeDTOS);

        List<FamilyTreeDTO> actualFamilyTreeDTOs = classToTest.getAllFamilyTrees();

        assertEquals(expectedFamilyTreeDTOS, actualFamilyTreeDTOs);
    }

    @Test
    void testGetAllFamilyTreesReturnsEmptyListWhenThereIsAnException() throws Exception {
        when(mockFamilyTreesRepository.getAllFamilyTrees()).thenThrow(new SQLException());

        assertTrue(classToTest.getAllFamilyTrees().isEmpty());
    }
}
