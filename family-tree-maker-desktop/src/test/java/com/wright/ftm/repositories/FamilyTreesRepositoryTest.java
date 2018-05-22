package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreesRepositoryTest {
    private FamilyTreesRepository classToTest;
    private DbManager mockDbManager = mock(DbManager.class);
    private FamilyTreesMapper mockFamilyTreesMapper = mock(FamilyTreesMapper.class);
    private ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreesRepository();
        classToTest.setDbManager(mockDbManager);
        classToTest.setFamilyTreesMapper(mockFamilyTreesMapper);
    }

    @Test
    void testCreateFamilyNameReturnsIdForInsertIntoFamilyNamesTable() throws Exception {
        String expectedFamilyName = "Wright";
        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + expectedFamilyName + "')";

        when(mockDbManager.insert(expectedStatement)).thenReturn(expectedId);

        assertEquals(expectedId, classToTest.createFamilyTree(expectedFamilyName));
    }

    @Test
    void testGetAllFamilyTreesReturnsFamilyTreesMappedFromQuery() throws Exception {
        List<FamilyTreeDTO> expectedFamilyTreeDTOs = Collections.singletonList(new FamilyTreeDTO());

        when(mockDbManager.query("SELECT * FROM FAMILY_TREES")).thenReturn(mockResultSet);
        when(mockFamilyTreesMapper.map(mockResultSet)).thenReturn(expectedFamilyTreeDTOs);

        assertEquals(expectedFamilyTreeDTOs, classToTest.getAllFamilyTrees());
    }

    @Test
    void testGetFamilyTreeReturnsFamilyTreeForId() throws Exception {
        FamilyTreeDTO expectedFamilyTreeDTO = new FamilyTreeDTO();
        expectedFamilyTreeDTO.setId(11);

        String expectedStatement = "SELECT * FROM FAMILY_TREES WHERE ID = " + expectedFamilyTreeDTO.getId();

        when(mockDbManager.query(expectedStatement)).thenReturn(mockResultSet);
        when(mockFamilyTreesMapper.map(mockResultSet)).thenReturn(Collections.singletonList(expectedFamilyTreeDTO));

        assertEquals(expectedFamilyTreeDTO, classToTest.getFamilyTree(expectedFamilyTreeDTO.getId()));
    }

    @Test
    void testGetFamilyTreeReturnsNullWhenThereIsNoFamilyTreeForId() throws Exception {
        when(mockDbManager.query(anyString())).thenReturn(mockResultSet);
        when(mockFamilyTreesMapper.map(mockResultSet)).thenReturn(Collections.emptyList());

        assertNull(classToTest.getFamilyTree(0));
    }

    @Test
    void testDeleteReturnsTrueWhenResultIsFalse() throws Exception {
        int expectedIdToDelete = 1;
        String expectedDeleteStatement = "DELETE FROM FAMILY_TREES WHERE ID = " + expectedIdToDelete;

        when(mockDbManager.execute(expectedDeleteStatement)).thenReturn(false);

        assertTrue(classToTest.deleteFamilyTree(expectedIdToDelete));
    }
}
