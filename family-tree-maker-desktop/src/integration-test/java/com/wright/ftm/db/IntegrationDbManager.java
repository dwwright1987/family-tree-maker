package com.wright.ftm.db;

public class IntegrationDbManager extends DbManager {
    @Override
    String getDerbyUrl() {
        return "jdbc:derby:memory:family-tree-maker;create=true";
    }
}
