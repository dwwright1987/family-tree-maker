package com.wright.ftm.wrappers;

import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class PropertyConfiguratorWrapper {
    public void configure(Properties properties) {
        PropertyConfigurator.configure(properties);
    }
}
