package com.wright.ftm.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableNamesMapperTest {
    private TableNamesMapper classToTest;
    private ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new TableNamesMapper();
    }

    @Test
    void testReturnsEmptyListWhenThereAreNoResults() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        assertTrue(classToTest.map(mockResultSet).isEmpty());
    }

    @Test
    void testReturnsAllResults() throws Exception {
        String expectedTableName1 = "TABLE1";
        String expectedTableName2 = "TABLE2";

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("TABLE_NAME")).thenReturn(expectedTableName1, expectedTableName2);

        List<String> tableNames = classToTest.map(mockResultSet);

        assertEquals(2, tableNames.size());
        assertTrue(tableNames.contains(expectedTableName1));
        assertTrue(tableNames.contains(expectedTableName2));
    }
}
