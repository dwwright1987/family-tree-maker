package com.wright.ftm.services;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.repositories.TablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DbConfigurationServiceTest {
    private DbConfigurationService classToTest;
    private String initialTableName = "DB_VERSION";
    private DbManager mockDbManager = mock(DbManager.class);
    private JarFileReaderService mockJarFileReaderService = mock(JarFileReaderService.class);
    private TablesRepository mockTablesRepository = mock(TablesRepository.class);

    @BeforeEach
    void setUp() throws Exception {
        when(mockJarFileReaderService.readAsString("/scripts/db/initial-schema-000001.sql")).thenReturn("initial statement 1;\r\ninitial statement 2;\r\n");
        when(mockTablesRepository.query()).thenReturn(Collections.emptyList());

        classToTest = new DbConfigurationService();
        classToTest.setDbManager(mockDbManager);
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

        verify(mockDbManager, times(2)).update(anyString());
        verify(mockDbManager).update("initial statement 1");
        verify(mockDbManager).update("initial statement 2");
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

        verify(mockDbManager).update("SET SCHEMA FAMILY_TREE_MAKER");
    }

    @Test
    void testConfigureReturnsFalseWhenQueryingForTableNamesThrowsSQLException() throws Exception {
        when(mockTablesRepository.query()).thenThrow(new SQLException());

        assertFalse(classToTest.configure());

        verifyZeroInteractions(mockDbManager);
    }

    @Test
    void testConfigureReturnsFalseWhenReadingJarFileThrowsIOException() throws Exception {
        when(mockJarFileReaderService.readAsString(anyString())).thenThrow(new IOException());

        assertFalse(classToTest.configure());

        verifyZeroInteractions(mockDbManager);
    }

    @Test
    void testConfigureReturnsFalseWhenUpdatingThrowsSQLException() throws Exception {
        doThrow(new SQLException()).when(mockDbManager).update(anyString());

        assertFalse(classToTest.configure());
    }
}
