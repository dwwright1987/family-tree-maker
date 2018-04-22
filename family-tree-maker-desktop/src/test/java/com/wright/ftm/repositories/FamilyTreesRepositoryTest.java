package com.wright.ftm.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FamilyTreesRepositoryTest {
    private FamilyTreesRepository classToTest;
    private Statement mockStatement = mock(Statement.class);

    @BeforeEach
    void setUp() throws Exception {
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);

        classToTest = new FamilyTreesRepository();
        classToTest.setConnection(mockConnection);
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
}
