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
    private Calendar calendar = new GregorianCalendar();
    private FamilyTreeMemberDTO danielWright;
    private FamilyTreeMemberDTO estherWright;
    private FamilyTreeMemberDTO lauraWright;
    private FamilyTreeMemberDTO stephenWright;
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
    private FamilyTreeDTO familyTreeDTO;
    private IntegrationDbManager integrationDbManager = new IntegrationDbManager();

    @BeforeEach
    void setUp() throws Exception {
        integrationDbManager.startDb();

        FamilyTreesService familyTreesService = new FamilyTreesService();
        familyTreeDTO = familyTreesService.createFamilyTree("Wright");

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        danielWright = new FamilyTreeMemberDTO();
        danielWright.setSex(MALE);
        danielWright.setFirstName("Daniel");
        danielWright.setMiddleName("Woolf");
        danielWright.setLastName(familyTreeDTO.getName());
        danielWright.setBirthDate(new Date(calendar.getTimeInMillis()));
        danielWright.setNotes("some notes about this particular family member");

        calendar.set(1954, Calendar.SEPTEMBER, 6);
        estherWright = new FamilyTreeMemberDTO();
        estherWright.setSex(FEMALE);
        estherWright.setFirstName("Esther");
        estherWright.setLastName(familyTreeDTO.getName());
        estherWright.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));

        calendar.set(1985, Calendar.MARCH, 21);
        lauraWright = new FamilyTreeMemberDTO();
        lauraWright.setSex(FEMALE);
        lauraWright.setFirstName("Laura");
        lauraWright.setLastName(familyTreeDTO.getName());
        lauraWright.setBirthDate(new Date(calendar.getTimeInMillis()));

        calendar.set(1948, Calendar.FEBRUARY, 2);
        stephenWright = new FamilyTreeMemberDTO();
        stephenWright.setSex(MALE);
        stephenWright.setFirstName("Stephen");
        stephenWright.setLastName(familyTreeDTO.getName());
        stephenWright.setBirthDate(new Date(calendar.getTimeInMillis()));
    }

    @AfterEach
    void tearDown() throws Exception {
        integrationDbManager.stopDb();
    }

    @Test
    void testCanCreateCurrentlyLivingMaleFamilyMember() throws Exception {
        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(danielWright, familyTreeDTO);
        assertTrue(actualFamilyTreeMemberDTO.getId() > 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(danielWright.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(danielWright.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(danielWright.getMiddleName(), actualFamilyTreeMemberDTO.getMiddleName());
        assertEquals(danielWright.getLastName(), actualFamilyTreeMemberDTO.getLastName());
        assertEquals(danielWright.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertNull(danielWright.getDeathDate());
        assertEquals(danielWright.getNotes(), actualFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanCreateCurrentlyLivingFemaleFamilyMember() throws Exception {
        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(lauraWright, familyTreeDTO);
        assertTrue(actualFamilyTreeMemberDTO.getId() > 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(lauraWright.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(lauraWright.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(0, actualFamilyTreeMemberDTO.getMiddleName().length());
        assertEquals(lauraWright.getLastName(), actualFamilyTreeMemberDTO.getLastName());
        assertEquals(lauraWright.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertNull(lauraWright.getDeathDate());
        assertNull(lauraWright.getNotes());
    }

    @Test
    void testCanCreateDeceasedFamilyMember() throws Exception {
        calendar.set(2077, Calendar.AUGUST, 14);
        danielWright.setDeathDate(new Date(calendar.getTimeInMillis()));

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(danielWright, familyTreeDTO);
        assertTrue(actualFamilyTreeMemberDTO.getId() > 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(danielWright.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(danielWright.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(danielWright.getMiddleName(), actualFamilyTreeMemberDTO.getMiddleName());
        assertEquals(danielWright.getLastName(), actualFamilyTreeMemberDTO.getLastName());
        assertEquals(danielWright.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertEquals(danielWright.getDeathDate().toString(), actualFamilyTreeMemberDTO.getDeathDate().toString());
        assertEquals(danielWright.getNotes(), actualFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanUpdateFamilyMember() throws Exception {
        String expectedUpdatedNotes = "some update notes";

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(danielWright, familyTreeDTO);
        danielWright.setNotes(expectedUpdatedNotes);

        assertTrue(familyTreeMembersService.updateFamilyMember(danielWright));

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(1, familyTreeMemberDTOs.size());

        actualFamilyTreeMemberDTO = familyTreeMemberDTOs.get(0);
        assertEquals(familyTreeDTO.getName(), actualFamilyTreeMemberDTO.getFamilyTree().getName());
        assertEquals(danielWright.getSex(), actualFamilyTreeMemberDTO.getSex());
        assertEquals(danielWright.getFirstName(), actualFamilyTreeMemberDTO.getFirstName());
        assertEquals(danielWright.getMiddleName(), actualFamilyTreeMemberDTO.getMiddleName());
        assertEquals(danielWright.getLastName(), actualFamilyTreeMemberDTO.getLastName());
        assertEquals(danielWright.getBirthDate().toString(), actualFamilyTreeMemberDTO.getBirthDate().toString());
        assertEquals(expectedUpdatedNotes, actualFamilyTreeMemberDTO.getNotes());
    }

    @Test
    void testCanCreateFamilyMemberWithParents() throws Exception {
        //TODO: Automatically add child when parent is being created for?
        danielWright.addParent(stephenWright);
        danielWright.addParent(estherWright);

       familyTreeMembersService.createFamilyMember(stephenWright, familyTreeDTO);
       familyTreeMembersService.createFamilyMember(estherWright, familyTreeDTO);
       familyTreeMembersService.createFamilyMember(danielWright, familyTreeDTO);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        assertEquals(3, familyTreeMemberDTOs.size());

        FamilyTreeMemberDTO actualChild = findFamilyMember(danielWright.getFirstName(), familyTreeMemberDTOs);
        assertEquals(2, actualChild.getParents().size());

        FamilyTreeMemberDTO actualParent1 = findFamilyMember(stephenWright.getFirstName(), actualChild.getParents());
        assertEquals(1, actualParent1.getChildren());
        assertEquals(danielWright.getFullName(), actualParent1.getChildren().get(0).getFullName());

        FamilyTreeMemberDTO actualParent2 = findFamilyMember(estherWright.getFirstName(), actualChild.getParents());
        assertEquals(1, actualParent2.getChildren());
        assertEquals(danielWright.getFullName(), actualParent2.getChildren().get(0).getFullName());
    }

    @Test
    void testCanAddParentsToExistingFamilyMember() throws Exception {
        familyTreeMembersService.createFamilyMember(stephenWright, familyTreeDTO);
        familyTreeMembersService.createFamilyMember(estherWright, familyTreeDTO);
        familyTreeMembersService.createFamilyMember(danielWright, familyTreeDTO);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);

        FamilyTreeMemberDTO actualChild = findFamilyMember(danielWright.getFirstName(), familyTreeMemberDTOs);
        actualChild.addParent(stephenWright);
        actualChild.addParent(estherWright);

        familyTreeMembersService.updateFamilyMember(actualChild);

        familyTreeMemberDTOs = familyTreeMembersService.getFamilyMembers(familyTreeDTO);
        actualChild = findFamilyMember(danielWright.getFirstName(), familyTreeMemberDTOs);
        assertEquals(2, actualChild.getParents().size());

        FamilyTreeMemberDTO actualParent1 = findFamilyMember(stephenWright.getFirstName(), actualChild.getParents());
        assertEquals(1, actualParent1.getChildren());
        assertEquals(danielWright.getFullName(), actualParent1.getChildren().get(0).getFullName());

        FamilyTreeMemberDTO actualParent2 = findFamilyMember(estherWright.getFirstName(), actualChild.getParents());
        assertEquals(1, actualParent2.getChildren());
        assertEquals(danielWright.getFullName(), actualParent2.getChildren().get(0).getFullName());
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
