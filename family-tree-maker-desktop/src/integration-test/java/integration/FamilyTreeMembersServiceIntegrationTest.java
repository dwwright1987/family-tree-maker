package integration;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
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
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
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
        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(MALE);
        expectedFamilyTreeMemberDTO.setFirstName("Daniel");
        expectedFamilyTreeMemberDTO.setMiddleName("Woolf");
        expectedFamilyTreeMemberDTO.setLastName("Wright");

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(expectedFamilyTreeMemberDTO, familyTreeDTO);
        assertTrue(actualFamilyTreeMemberDTO.getId() > 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(expectedFamilyTreeMemberDTO.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(expectedFamilyTreeMemberDTO.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(expectedFamilyTreeMemberDTO.getMiddleName(), actualFamilyTreeMemberDTO.getMiddleName());
        assertEquals(expectedFamilyTreeMemberDTO.getLastName(), actualFamilyTreeMemberDTO.getLastName());
    }

    @Test
    void testCanCreateCurrentlyLivingFemaleFamilyMember() throws Exception {
        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(FEMALE);
        expectedFamilyTreeMemberDTO.setFirstName("Laura");
        expectedFamilyTreeMemberDTO.setLastName("Wright");

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(expectedFamilyTreeMemberDTO, familyTreeDTO);
        assertTrue(actualFamilyTreeMemberDTO.getId() > 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(expectedFamilyTreeMemberDTO.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(expectedFamilyTreeMemberDTO.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(0, actualFamilyTreeMemberDTO.getMiddleName().length());
        assertEquals(expectedFamilyTreeMemberDTO.getLastName(), actualFamilyTreeMemberDTO.getLastName());
    }
}
