package integration;

import com.wright.ftm.db.DbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyTreesServiceIntegrationTest {
    private DbManager dbManager = DbManager.getInstance();

    @BeforeEach
    void setUp() throws Exception {
        dbManager.setDerbyUrl(Constants.DERBY_IN_MEMORY_URL);
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
