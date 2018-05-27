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
        expectedFamilyTreeMemberDTO.setLastName("Wright");
        expectedFamilyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

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
    }

    @Test
    void testCanCreateCurrentlyLivingFemaleFamilyMember() throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.set(1985, Calendar.MARCH, 21);

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(FEMALE);
        expectedFamilyTreeMemberDTO.setFirstName("Laura");
        expectedFamilyTreeMemberDTO.setLastName("Wright");
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
    }

    @Test
    void testCanCreateDeceasedFamilyMember() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setSex(MALE);
        expectedFamilyTreeMemberDTO.setFirstName("Daniel");
        expectedFamilyTreeMemberDTO.setMiddleName("Woolf");
        expectedFamilyTreeMemberDTO.setLastName("Wright");

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
    }
}
