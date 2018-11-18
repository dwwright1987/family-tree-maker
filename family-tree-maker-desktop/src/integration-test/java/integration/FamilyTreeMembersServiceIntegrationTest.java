package integration;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
import com.wright.ftm.services.FamilyTreesService;
import integration.db.IntegrationDbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.*;

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
        Calendar calendar = new GregorianCalendar();
        calendar.set(1987, Calendar.SEPTEMBER, 1);

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(MALE);
        expectedFamilyTreeMemberDTO.setFirstName("Daniel");
        expectedFamilyTreeMemberDTO.setMiddleName("Woolf");
        expectedFamilyTreeMemberDTO.setLastName(familyTreeDTO.getName());
        expectedFamilyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));
        expectedFamilyTreeMemberDTO.setNotes("some notes about this particular family member");

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
        assertEquals(expectedFamilyTreeMemberDTO.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertNull(expectedFamilyTreeMemberDTO.getDeathDate());
        assertEquals(expectedFamilyTreeMemberDTO.getNotes(), actualFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanCreateCurrentlyLivingFemaleFamilyMember() throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.set(1985, Calendar.MARCH, 21);

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(FEMALE);
        expectedFamilyTreeMemberDTO.setFirstName("Laura");
        expectedFamilyTreeMemberDTO.setLastName(familyTreeDTO.getName());
        expectedFamilyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

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
        assertEquals(expectedFamilyTreeMemberDTO.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertNull(expectedFamilyTreeMemberDTO.getDeathDate());
        assertNull(expectedFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanCreateDeceasedFamilyMember() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(MALE);
        expectedFamilyTreeMemberDTO.setFirstName("Daniel");
        expectedFamilyTreeMemberDTO.setMiddleName("Woolf");
        expectedFamilyTreeMemberDTO.setLastName(familyTreeDTO.getName());

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        expectedFamilyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        calendar.set(2077, Calendar.AUGUST, 14);
        expectedFamilyTreeMemberDTO.setDeathDate(new Date(calendar.getTimeInMillis()));

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
        assertEquals(expectedFamilyTreeMemberDTO.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertEquals(expectedFamilyTreeMemberDTO.getDeathDate().toString(), actualFamilyTreeMemberDTO.getDeathDate().toString());
        assertNull(expectedFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanUpdateFamilyMember() throws Exception {
        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(MALE);
        expectedFamilyTreeMemberDTO.setFirstName("Daniel");
        expectedFamilyTreeMemberDTO.setLastName(familyTreeDTO.getName());
        expectedFamilyTreeMemberDTO.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(expectedFamilyTreeMemberDTO, familyTreeDTO);
        actualFamilyTreeMemberDTO.setMiddleName("Woolf");

        assertTrue(familyTreeMembersService.updateFamilyMember(actualFamilyTreeMemberDTO));

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(expectedFamilyTreeMemberDTO.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(expectedFamilyTreeMemberDTO.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(expectedFamilyTreeMemberDTO.getMiddleName(), actualFamilyTreeMemberDTO.getMiddleName());
        assertEquals(expectedFamilyTreeMemberDTO.getLastName(), actualFamilyTreeMemberDTO.getLastName());
        assertEquals(expectedFamilyTreeMemberDTO.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
    }

    @Test
    void testCanCreateFamilyMemberWithParents() throws Exception {
        //TODO: Automatically add child when parent is being created for?
        FamilyTreeMemberDTO expectedParent1 = new FamilyTreeMemberDTO();
        expectedParent1.setSex(MALE);
        expectedParent1.setFirstName("Stephen");
        expectedParent1.setLastName(familyTreeDTO.getName());
        expectedParent1.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        FamilyTreeMemberDTO expectedParent2 = new FamilyTreeMemberDTO();
        expectedParent2.setSex(FEMALE);
        expectedParent2.setFirstName("Esther");
        expectedParent2.setLastName(familyTreeDTO.getName());
        expectedParent2.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        FamilyTreeMemberDTO expectedChild = new FamilyTreeMemberDTO();
        expectedChild.setSex(MALE);
        expectedChild.setFirstName("Daniel");
        expectedChild.setLastName(familyTreeDTO.getName());
        expectedChild.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));
        expectedChild.addParent(expectedParent1);
        expectedChild.addParent(expectedParent2);

       familyTreeMembersService.createFamilyMember(expectedParent1, familyTreeDTO);
       familyTreeMembersService.createFamilyMember(expectedParent2, familyTreeDTO);
       familyTreeMembersService.createFamilyMember(expectedChild, familyTreeDTO);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(3, familyTreeMemberDTOs.size());

        FamilyTreeMemberDTO actualChild = findFamilyMember(expectedChild.getFirstName(), familyTreeMemberDTOs);
        assertEquals(2, actualChild.getParents().size());
        assertEquals(expectedParent1.getFirstName(), actualChild.getParents().get(0).getFirstName());
        assertEquals(expectedParent2.getFirstName(), actualChild.getParents().get(1).getFirstName());
    }

    @Test
    void testCanAddParentsToExistingFamilyMember() throws Exception {
        FamilyTreeMemberDTO expectedParent1 = new FamilyTreeMemberDTO();
        expectedParent1.setSex(MALE);
        expectedParent1.setFirstName("Stephen");
        expectedParent1.setLastName(familyTreeDTO.getName());
        expectedParent1.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        FamilyTreeMemberDTO expectedParent2 = new FamilyTreeMemberDTO();
        expectedParent2.setSex(FEMALE);
        expectedParent2.setFirstName("Esther");
        expectedParent2.setLastName(familyTreeDTO.getName());
        expectedParent2.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        FamilyTreeMemberDTO expectedChild = new FamilyTreeMemberDTO();
        expectedChild.setSex(MALE);
        expectedChild.setFirstName("Daniel");
        expectedChild.setLastName(familyTreeDTO.getName());
        expectedChild.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        familyTreeMembersService.createFamilyMember(expectedParent1, familyTreeDTO);
        familyTreeMembersService.createFamilyMember(expectedParent2, familyTreeDTO);
        familyTreeMembersService.createFamilyMember(expectedChild, familyTreeDTO);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);

        FamilyTreeMemberDTO actualChild = findFamilyMember(expectedChild.getFirstName(), familyTreeMemberDTOs);
        actualChild.addParent(expectedParent1);
        actualChild.addParent(expectedParent2);

        familyTreeMembersService.updateFamilyMember(actualChild);

        familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        actualChild = findFamilyMember(expectedChild.getFirstName(), familyTreeMemberDTOs);
        assertEquals(2, actualChild.getParents().size());
        assertEquals(expectedParent1.getFirstName(), actualChild.getParents().get(0).getFirstName());
        assertEquals(expectedParent2.getFirstName(), actualChild.getParents().get(1).getFirstName());
    }

    private FamilyTreeMemberDTO findFamilyMember(String firstName, List<FamilyTreeMemberDTO> familyTreeMemberDTOs) {
        for (FamilyTreeMemberDTO familyTreeMemberDTO : familyTreeMemberDTOs) {
            if (firstName.equals(familyTreeMemberDTO.getFirstName())) {
                return familyTreeMemberDTO;
            }
        }

        return null;
    }
}
