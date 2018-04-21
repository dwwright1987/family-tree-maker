package com.wright.ftm.services;

import com.wright.ftm.repositories.TablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DbConfigurationServiceTest {
    private DbConfigurationService classToTest;
    private String initialTableName = "DB_VERSION";
    private Connection mockConnection = mock(Connection.class);
    private JarFileReaderService mockJarFileReaderService = mock(JarFileReaderService.class);
    private Statement mockStatement = mock(Statement.class);
    private TablesRepository mockTablesRepository = mock(TablesRepository.class);

    @BeforeEach
    void setUp() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockJarFileReaderService.readAsString("/scripts/db/initial-schema-000001.sql")).thenReturn("initial statement 1;\r\ninitial statement 2;\r\n");
        when(mockTablesRepository.query()).thenReturn(Collections.emptyList());

        classToTest = new DbConfigurationService();
        classToTest.setConnection(mockConnection);
        classToTest.setJarFileReaderService(mockJarFileReaderService);
        classToTest.setTablesRepository(mockTablesRepository);
    }

    @Test
    void testConfigureReturnsTrueWhenInitialConfigurationIsNotAlreadyDone() throws Exception {
        assertTrue(classToTest.configure());
    }

    @Test
    void testConfigureRunsInitialScriptWhenInitialConfigurationIsNotAlreadyDone() throws Exception {
        classToTest.configure();

        verify(mockStatement, times(2)).executeUpdate(anyString());
        verify(mockStatement).executeUpdate("initial statement 1");
        verify(mockStatement).executeUpdate("initial statement 2");
    }

    @Test
    void testConfigureReturnsTrueWhenInitialConfigurationIsAlreadyDone() throws Exception {
        when(mockTablesRepository.query()).thenReturn(Collections.singletonList(initialTableName));

        assertTrue(classToTest.configure());
    }

    @Test
    void testConfigureSetsSchemaWhenInitialConfigurationIsAlreadyDone() throws Exception {
        when(mockTablesRepository.query()).thenReturn(Collections.singletonList(initialTableName));

        classToTest.configure();

        verify(mockStatement).executeUpdate("SET SCHEMA FAMILY_TREE_MAKER");
    }

    @Test
    void testConfigureReturnsFalseWhenQueryingForTableNamesThrowsSQLException() throws Exception {
        when(mockTablesRepository.query()).thenThrow(new SQLException());

        assertFalse(classToTest.configure());

        verifyZeroInteractions(mockConnection);
    }

    @Test
    void testConfigureReturnsFalseWhenReadingJarFileThrowsIOException() throws Exception {
        when(mockJarFileReaderService.readAsString(anyString())).thenThrow(new IOException());

        assertFalse(classToTest.configure());

        verifyZeroInteractions(mockConnection);
    }

    @Test
    void testConfigureReturnsFalseWhenCreatingStatementThrowsSQLException() throws Exception {
        when(mockConnection.createStatement()).thenThrow(new SQLException());

        assertFalse(classToTest.configure());
    }

    @Test
    void testConfigureReturnsFalseWhenExecutingStatementThrowsSQLException() throws Exception {
        when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException());

        assertFalse(classToTest.configure());
    }
}
