package integration;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.services.FamilyTreesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyTreesServiceIntegrationTest {
    private DbManager dbManager = DbManager.getInstance();
    private FamilyTreesService familyTreesService = new FamilyTreesService();

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
    void testWritesAndReadsFamilyTrees() throws Exception {
        String expectedFamilyName = "Wright";

        familyTreesService.createFamilyTree(expectedFamilyName);

        List<FamilyTreeDTO> allFamilyTrees = familyTreesService.getAllFamilyTrees();

        assertEquals(1, allFamilyTrees.size());
        assertEquals(expectedFamilyName, allFamilyTrees.get(0).getName());
    }
}
