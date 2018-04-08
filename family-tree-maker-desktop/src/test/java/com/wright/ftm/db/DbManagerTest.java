package com.wright.ftm.db;

import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class DbManagerTest {
    private DbManager classToTest;
    private ClassWrapper mockClassWrapper = mock(ClassWrapper.class);
    private Connection mockConnection = mock(Connection.class);
    private DriverManagerWrapper mockDriverMangerWrapper = mock(DriverManagerWrapper.class);

    @BeforeEach
    void setUp() throws Exception {
        when(mockDriverMangerWrapper.getConnection("jdbc:derby:c:/ProgramData/wright/family-tree-maker/db/derbyDB;create=true")).thenReturn(mockConnection);

        classToTest = DbManager.getInstance();
        classToTest.setClassWrapper(mockClassWrapper);
        classToTest.setDriverManagerWrapper(mockDriverMangerWrapper);
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
    void testDoesNothingOnStopWhenThereIsNoConnection() throws Exception {
        classToTest.stopDb();

        verifyZeroInteractions(mockDriverMangerWrapper);
        verifyZeroInteractions(mockConnection);
    }
}