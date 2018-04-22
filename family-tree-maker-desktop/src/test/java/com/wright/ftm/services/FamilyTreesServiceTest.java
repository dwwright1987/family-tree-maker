package com.wright.ftm.services;

import com.wright.ftm.repositories.FamilyTreesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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
}
