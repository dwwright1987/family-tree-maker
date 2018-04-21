package com.wright.ftm.services;

import com.wright.ftm.wrappers.Log4jManagerWrapper;
import com.wright.ftm.wrappers.PropertyConfiguratorWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.wright.ftm.Constants.WINDOWS_ROOT_APP_STORAGE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoggerConfigurationServiceTest {
    private LoggerConfigurationService classToTest;
    private InputStream mockInputStream = mock(InputStream.class);
    private JarFileReaderService mockJarFileReaderService = mock(JarFileReaderService.class);
    private Log4jManagerWrapper mockLog4jManagerWrapper = mock(Log4jManagerWrapper.class);
    private PropertyConfiguratorWrapper mockPropertyConfiguratorWrapper = mock(PropertyConfiguratorWrapper.class);

    @BeforeEach
    void setUp() throws Exception {
        when(mockJarFileReaderService.readAsInputStream(anyString())).thenReturn(mockInputStream);

        classToTest = new LoggerConfigurationService();
        classToTest.setJarFileReaderService(mockJarFileReaderService);
        classToTest.setLog4jManagerWrapper(mockLog4jManagerWrapper);
        classToTest.setPropertyConfiguratorWrapper(mockPropertyConfiguratorWrapper);
    }

    @Test
    void testConfigureLoadsExistingConfiguration() throws Exception {
        classToTest.configure();

        InOrder inOrder = inOrder(mockInputStream, mockJarFileReaderService);
        inOrder.verify(mockJarFileReaderService).readAsInputStream("/log4j.properties");
        inOrder.verify(mockInputStream).close();
    }

    @Test
    void testConfigureUpdatesLog4jProperties() throws Exception {
        classToTest.configure();

        InOrder inOrder = inOrder(mockLog4jManagerWrapper, mockPropertyConfiguratorWrapper);
        inOrder.verify(mockLog4jManagerWrapper).resetConfiguration();
        inOrder.verify(mockPropertyConfiguratorWrapper).configure(any(Properties.class));
    }

    @Test
    void testConfigureSetsLogFilePath() throws Exception {
        classToTest.configure();

        ArgumentCaptor<Properties> propertiesArgumentCaptor = ArgumentCaptor.forClass(Properties.class);
        verify(mockPropertyConfiguratorWrapper).configure(propertiesArgumentCaptor.capture());

        String expectedLogFilePath = WINDOWS_ROOT_APP_STORAGE_PATH + "/logs/app.log";
        assertEquals(expectedLogFilePath, propertiesArgumentCaptor.getValue().getProperty("log4j.appender.file.File"));
    }

    @Test
    void testConfigureDoesNotThrowExceptionOnStreamCloseError() throws Exception {
        doThrow(new IOException()).when(mockInputStream).close();

        try {
            classToTest.configure();
        } catch (Exception e) {
            fail("Should not throw Exception");
        }
    }
}
