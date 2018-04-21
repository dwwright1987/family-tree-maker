package com.wright.ftm.services;

import com.wright.ftm.wrappers.Log4jManagerWrapper;
import com.wright.ftm.wrappers.PropertyConfiguratorWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.wright.ftm.Constants.WINDOWS_ROOT_APP_STORAGE_PATH;

public class LoggerConfigurationService {
    private JarFileReaderService jarFileReaderService = new JarFileReaderService();
    private Log4jManagerWrapper log4jManagerWrapper = new Log4jManagerWrapper();
    private PropertyConfiguratorWrapper propertyConfiguratorWrapper = new PropertyConfiguratorWrapper();

    public void configure() {
        try {
            Properties log4jProperties = loadLog4jProperties();

            log4jProperties.setProperty("log4j.appender.file.File", WINDOWS_ROOT_APP_STORAGE_PATH + "/logs/app.log");

            updateLog4jProperties(log4jProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties loadLog4jProperties() throws IOException {
        Properties log4jProperties = new Properties();

        InputStream existingLog4jPropertiesStream = jarFileReaderService.readAsInputStream("/log4j.properties");
        log4jProperties.load(existingLog4jPropertiesStream);
        existingLog4jPropertiesStream.close();

        return log4jProperties;
    }

    private void updateLog4jProperties(Properties log4jProperties) {
        log4jManagerWrapper.resetConfiguration();
        propertyConfiguratorWrapper.configure(log4jProperties);
    }

    void setJarFileReaderService(JarFileReaderService jarFileReaderService) {
        this.jarFileReaderService = jarFileReaderService;
    }

    void setLog4jManagerWrapper(Log4jManagerWrapper log4jManagerWrapper) {
        this.log4jManagerWrapper = log4jManagerWrapper;
    }

    void setPropertyConfiguratorWrapper(PropertyConfiguratorWrapper propertyConfiguratorWrapper) {
        this.propertyConfiguratorWrapper = propertyConfiguratorWrapper;
    }
}
