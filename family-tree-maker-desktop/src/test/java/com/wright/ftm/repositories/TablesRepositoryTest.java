package com.wright.ftm.repositories;

import com.wright.ftm.mappers.TableNamesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TablesRepositoryTest {
    private TablesRepository classToTest;
    private List<String> expectedTableNames = Collections.singletonList("TABLE_NAME");
    private Connection mockConnection = mock(Connection.class);
    private DatabaseMetaData mockDatabaseMetaData = mock(DatabaseMetaData.class);

    @BeforeEach
    void setUp() throws Exception {
        ResultSet mockResultSet = mock(ResultSet.class);
        TableNamesMapper mockTableNamesMapper = mock(TableNamesMapper.class);

        when(mockConnection.getMetaData()).thenReturn(mockDatabaseMetaData);
        when(mockDatabaseMetaData.getTables(eq(null), eq(null), eq(null), any(String[].class))).thenReturn(mockResultSet);
        when(mockTableNamesMapper.map(mockResultSet)).thenReturn(expectedTableNames);

        classToTest = new TablesRepository();
        classToTest.setConnection(mockConnection);
        classToTest.setTableNamesMapper(mockTableNamesMapper);
    }

    @Test
    void testReturnsTablesFromQuery() throws Exception {
        assertEquals(expectedTableNames, classToTest.query());
    }

    @Test
    void testQueriesForTables() throws Exception {
        classToTest.query();

        ArgumentCaptor<String[]> typesArgumentCaptor = ArgumentCaptor.forClass(String[].class);

        verify(mockDatabaseMetaData).getTables(eq(null), eq(null), eq(null), typesArgumentCaptor.capture());

        String[] actualTypes = typesArgumentCaptor.getValue();

        assertEquals(1, actualTypes.length);
        assertEquals("TABLE", actualTypes[0]);
    }
}
