package com.wright.ftm.db;

import com.wright.ftm.services.DbConfigurationService;
import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.wright.ftm.Constants.WINDOWS_ROOT_APP_STORAGE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbManagerTest {
    private DbManager classToTest;
    private boolean expectedConfigurationResult = true;
    private ClassWrapper mockClassWrapper = mock(ClassWrapper.class);
    private Connection mockConnection = mock(Connection.class);
    private DbConfigurationService mockDbConfigurationService = mock(DbConfigurationService.class);
    private DriverManagerWrapper mockDriverMangerWrapper = mock(DriverManagerWrapper.class);
    private Statement mockStatement = mock(Statement.class);

    @BeforeEach
    void setUp() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockDbConfigurationService.configure()).thenReturn(expectedConfigurationResult);
        when(mockDriverMangerWrapper.getConnection("jdbc:derby:" + WINDOWS_ROOT_APP_STORAGE_PATH + "/db/derbyDB;create=true")).thenReturn(mockConnection);

        classToTest = new DbManagerStub();
        classToTest.setClassWrapper(mockClassWrapper);
        classToTest.setDriverManagerWrapper(mockDriverMangerWrapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        classToTest.stopDb();
    }

    @Test
    void testCanGetInstance() throws Exception {
        assertNotNull(DbManager.getInstance());
    }

    @Test
    void testConnectionIsNullBeforeDbStart() throws Exception {
        assertNull(classToTest.getConnection());
    }

    @Test
    void testCreatesEmbeddedDerbyInstanceOnStart() throws Exception {
        classToTest.startDb();

        verify(mockClassWrapper).forName("org.apache.derby.jdbc.EmbeddedDriver");
    }

    @Test
    void testCreatesConnectionOnStart() throws Exception {
        classToTest.startDb();

        assertEquals(mockConnection, classToTest.getConnection());
    }

    @Test
    void testConfiguresDbOnStart() throws Exception {
        classToTest.startDb();

        verify(mockDbConfigurationService).configure();
    }

    @Test
    void testReturnsConfigurationResultOnStart() throws Exception {
        assertEquals(expectedConfigurationResult, classToTest.startDb());
    }

    @Test
    void testDoesNotConfigureDbWhenThereIsAnExceptionThrownDuringDbConnection() throws Exception {
        when(mockDriverMangerWrapper.getConnection(anyString())).thenThrow(RuntimeException.class);

        assertFalse(classToTest.startDb());

        verifyZeroInteractions(mockDbConfigurationService);
    }

    @Test
    void testStopsDerbyOnStop() throws Exception {
        classToTest.startDb();

        classToTest.stopDb();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockDriverMangerWrapper, atLeastOnce()).getConnection(stringArgumentCaptor.capture());

        List<String> argumentCaptorValues = stringArgumentCaptor.getAllValues();
        String stopConnection = argumentCaptorValues.get(argumentCaptorValues.size() - 1);
        assertEquals("jdbc:derby:;shutdown=true", stopConnection);
    }

    @Test
    void testClosesConnectionOnStop() throws Exception {
        classToTest.startDb();

        classToTest.stopDb();

        verify(mockConnection).close();
    }

    @Test
    void testConnectionIsNullAfterStoppingDb() throws Exception {
        classToTest.startDb();

        classToTest.stopDb();

        assertNull(classToTest.getConnection());
    }

    @Test
    void testDoesNothingOnStopWhenThereIsNoConnection() throws Exception {
        classToTest.stopDb();

        verifyZeroInteractions(mockDriverMangerWrapper);
        verifyZeroInteractions(mockConnection);
    }

    @Test
    void testHandlesSQLExceptionThrownOnDbStop() throws Exception {
        classToTest.startDb();

        when(mockDriverMangerWrapper.getConnection(anyString())).thenThrow(SQLException.class);

        classToTest.stopDb();

        assertNull(classToTest.getConnection());
    }

    @Test
    void testUpdateExecutesGivenStatementAsAnUpdate() throws Exception {
        String expectedUpdateStatement = "CREATE TABLE BLAH";

        classToTest.startDb();

        classToTest.update(expectedUpdateStatement);

        verify(mockStatement).executeUpdate(expectedUpdateStatement);
    }

    @Test
    void testQueryReturnsResultSetFromQuery() throws Exception {
        String expectedQueryStatement = "SELECT * FROM BLAH";
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockStatement.executeQuery(expectedQueryStatement)).thenReturn(mockResultSet);

        classToTest.startDb();

        ResultSet actualResultSet = classToTest.query(expectedQueryStatement);

        assertEquals(mockResultSet, actualResultSet);
    }

    @Test
    void testExecuteReturnsResultFromExecute() throws Exception {
        String expectedExecuteStatement = "DELETE FROM BLAH WHERE ID = 1";
        boolean result = true;

        when(mockStatement.execute(expectedExecuteStatement)).thenReturn(result);

        classToTest.startDb();

        boolean actualResult = classToTest.execute(expectedExecuteStatement);

        assertEquals(result, actualResult);
    }

    private class DbManagerStub extends DbManager {
        @Override
        DbConfigurationService getDbConfigurationService() {
            return mockDbConfigurationService;
        }
    }
}
