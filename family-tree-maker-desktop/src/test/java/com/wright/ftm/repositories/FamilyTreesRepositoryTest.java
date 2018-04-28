package com.wright.ftm.repositories;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.mappers.FamilyTreesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FamilyTreesRepositoryTest {
    private FamilyTreesRepository classToTest;
    private FamilyTreesMapper mockFamilyTreesMapper = mock(FamilyTreesMapper.class);
    private Statement mockStatement = mock(Statement.class);

    @BeforeEach
    void setUp() throws Exception {
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);

        classToTest = new FamilyTreesRepository();
        classToTest.setConnection(mockConnection);
        classToTest.setFamilyTreesMapper(mockFamilyTreesMapper);
    }

    @Test
    void testCreateFamilyNameRunsInsertStatementForFamilyNamesTable() throws Exception {
        String expectedFamilyName = "Wright";

        classToTest.createFamilyTree(expectedFamilyName);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockStatement).executeUpdate(stringArgumentCaptor.capture());

        String expectedStatement = "INSERT INTO FAMILY_TREES (NAME) VALUES ('" + expectedFamilyName + "')";
        assertEquals(expectedStatement, stringArgumentCaptor.getValue());
    }

    @Test
    void testGetAllFamilyTreesReturnFamilyTreesMappedFromQuery() throws Exception {
        List<FamilyTreeDTO> expectedFamilyTreeDTOS = new ArrayList<>();
        expectedFamilyTreeDTOS.add(new FamilyTreeDTO());

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockFamilyTreesMapper.map(mockResultSet)).thenReturn(expectedFamilyTreeDTOS);
        when(mockStatement.executeQuery("SELECT * FROM FAMILY_TREES")).thenReturn(mockResultSet);

        List<FamilyTreeDTO> actualFamilyTreesDTOs = classToTest.getAllFamilyTrees();

        assertEquals(expectedFamilyTreeDTOS, actualFamilyTreesDTOs);
    }
}
