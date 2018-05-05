package integration;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.services.FamilyTreesService;
import integration.db.IntegrationDbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FamilyTreesServiceIntegrationTest {
    private static final String EXPECTED_FAMILY_NAME = "Wright";
    private DbManager dbManager = DbManager.getInstance();
    private FamilyTreesService familyTreesService = new FamilyTreesService();
    private IntegrationDbManager integrationDbManager = new IntegrationDbManager();

    @BeforeEach
    void setUp() throws Exception {
        integrationDbManager.startDb();
    }

    @AfterEach
    void tearDown() throws Exception {
        integrationDbManager.stopDb();
    }

    @Test
    void testWritesAndReadsFamilyTrees() throws Exception {
        familyTreesService.createFamilyTree(EXPECTED_FAMILY_NAME);

        List<FamilyTreeDTO> allFamilyTrees = familyTreesService.getAllFamilyTrees();

        assertEquals(1, allFamilyTrees.size());
        assertEquals(EXPECTED_FAMILY_NAME, allFamilyTrees.get(0).getName());
    }

    @Test
    void testDeletesFamilyTrees() throws Exception {
        familyTreesService.createFamilyTree(EXPECTED_FAMILY_NAME);

        FamilyTreeDTO familyTreeToDelete = familyTreesService.getAllFamilyTrees().get(0);

        familyTreesService.removeFamilyTree(familyTreeToDelete);

        assertTrue(familyTreesService.getAllFamilyTrees().isEmpty());
    }
}
