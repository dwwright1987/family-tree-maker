package integration;

import com.wright.ftm.dtos.FamilyMemberDTO;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.services.FamilyMembersTreeService;
import com.wright.ftm.services.FamilyTreesService;
import integration.db.IntegrationDbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FamilyTreeMembersServiceIntegrationTest {
    private FamilyMembersTreeService familyMembersTreeService = new FamilyMembersTreeService();
    private FamilyTreeDTO familyTreeDTO;
    private IntegrationDbManager integrationDbManager = new IntegrationDbManager();

    @BeforeEach
    void setUp() throws Exception {
        integrationDbManager.startDb();

        FamilyTreesService familyTreesService = new FamilyTreesService();
        familyTreeDTO = familyTreesService.createFamilyTree("Wright");
    }

    @AfterEach
    void tearDown() throws Exception {
        integrationDbManager.stopDb();
    }

    @Test
    void testCanCreateCurrentlyLivingMaleFamilyMember() throws Exception {
        FamilyMemberDTO expectedFamilyMemberDTO = new FamilyMemberDTO();
        expectedFamilyMemberDTO.setFamilyTree(familyTreeDTO);
        expectedFamilyMemberDTO.setSex(MALE);

        int familyMemberId = familyMembersTreeService.createFamilyMember(expectedFamilyMemberDTO);
        assertTrue(familyMemberId > 0);

        List<FamilyMemberDTO> familyMemberDTOs = familyMembersTreeService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyMemberDTOs.size());

        FamilyMemberDTO actualFamilyMemberDTO = familyMemberDTOs.get(0);
        assertEquals(expectedFamilyMemberDTO.getSex(), actualFamilyMemberDTO.getSex());
    }

    @Test
    void testCanCreateCurrentlyLivingFemaleFamilyMember() throws Exception {
        FamilyMemberDTO expectedFamilyMemberDTO = new FamilyMemberDTO();
        expectedFamilyMemberDTO.setFamilyTree(familyTreeDTO);
        expectedFamilyMemberDTO.setSex(FEMALE);

        int familyMemberId = familyMembersTreeService.createFamilyMember(expectedFamilyMemberDTO);
        assertTrue(familyMemberId > 0);

        List<FamilyMemberDTO> familyMemberDTOs = familyMembersTreeService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyMemberDTOs.size());

        FamilyMemberDTO actualFamilyMemberDTO = familyMemberDTOs.get(0);
        assertEquals(expectedFamilyMemberDTO.getSex(), actualFamilyMemberDTO.getSex());
    }
}
