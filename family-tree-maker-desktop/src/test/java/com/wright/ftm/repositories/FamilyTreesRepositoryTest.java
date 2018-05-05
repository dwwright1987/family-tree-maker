package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FamilyTreesRepositoryTest {
    private FamilyTreesRepository classToTest;
    private final DbManager mockDbManager = mock(DbManager.class);
    private FamilyTreesMapper mockFamilyTreesMapper = mock(FamilyTreesMapper.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreesRepository();
        classToTest.setDbManager(mockDbManager);
        classToTest.setFamilyTreesMapper(mockFamilyTreesMapper);
    }

    @Test
    void testCreateFamilyNameRunsInsertStatementForFamilyNamesTable() throws Exception {
        String expectedFamilyName = "Wright";

        classToTest.createFamilyTree(expectedFamilyName);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockDbManager).update(stringArgumentCaptor.capture());

        String expectedStatement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + expectedFamilyName + "')";
        assertEquals(expectedStatement, stringArgumentCaptor.getValue());
    }

    @Test
    void testGetAllFamilyTreesReturnFamilyTreesMappedFromQuery() throws Exception {
        List<FamilyTreeDTO> expectedFamilyTreeDTOS = new ArrayList<>();
        expectedFamilyTreeDTOS.add(new FamilyTreeDTO());

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockDbManager.query("SELECT * FROM FAMILY_TREES")).thenReturn(mockResultSet);
        when(mockFamilyTreesMapper.map(mockResultSet)).thenReturn(expectedFamilyTreeDTOS);

        List<FamilyTreeDTO> actualFamilyTreesDTOs = classToTest.getAllFamilyTrees();

        assertEquals(expectedFamilyTreeDTOS, actualFamilyTreesDTOs);
    }

    @Test
    void testDeleteReturnsResultOfExecutingDeleteForId() throws Exception {
        int expectedIdToDelete = 1;
        String expectedDeleteStatement = "DELETE FROM FAMILY_TREES WHERE ID = " + expectedIdToDelete;
        boolean result = true;

        when(mockDbManager.execute(expectedDeleteStatement)).thenReturn(result);

        boolean actualResult = classToTest.deleteFamilyTree(expectedIdToDelete);

        assertEquals(result, actualResult);
    }
}
