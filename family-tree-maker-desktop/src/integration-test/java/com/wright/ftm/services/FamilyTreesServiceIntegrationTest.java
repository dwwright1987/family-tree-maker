package com.wright.ftm.services;

import com.wright.ftm.db.IntegrationDbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyTreesServiceIntegrationTest {
    private IntegrationDbManager dbManager;

    @BeforeEach
    void setUp() throws Exception {
        dbManager = new IntegrationDbManager();
        dbManager.startDb();
    }

    @AfterEach
    void tearDown() throws Exception {
        dbManager.stopDb();
    }

    @Test
    void test1() throws Exception {
        assertEquals(1, 1);
    }
}
