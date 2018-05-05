package com.wright.ftm.services;

import com.wright.ftm.db.IntegrationDbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyTreesServiceIntegrationTest {
    @BeforeEach
    void setUp() throws Exception {
        IntegrationDbManager.getInstance().startDb();
    }

    @AfterEach
    void tearDown() throws Exception {
        IntegrationDbManager.getInstance().stopDb();
    }

    @Test
    void test1() throws Exception {
        assertEquals(1, 1);
    }
}
