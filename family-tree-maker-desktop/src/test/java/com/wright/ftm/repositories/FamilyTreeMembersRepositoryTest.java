package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.mappers.FamilyTreeMembersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreeMembersRepositoryTest {
    private FamilyTreeMembersRepository classToTest;
    private DbManager mockDbManager = mock(DbManager.class);
    private FamilyTreeMembersMapper mockFamilyTreeMembersMapper = mock(FamilyTreeMembersMapper.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreeMembersRepository();
        classToTest.setDbManager(mockDbManager);
        classToTest.setFamilyTreeMembersMapper(mockFamilyTreeMembersMapper);
    }

    @Test
    void testCreateFamilyMemberReturnsIdForInsertIntoFamilyNamesTable() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");
        familyTreeMemberDTO.setNotes("some notes");

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        familyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        calendar.set(2077, Calendar.AUGUST, 14);
        familyTreeMemberDTO.setDeathDate(new Date(calendar.getTimeInMillis()));

        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREE_MEMBERS (FAMILY_TREE_ID, SEX, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DATE, DEATH_DATE, NOTES) VALUES (" +
            familyTreeMemberDTO.getFamilyTreeId() + ", " +
            familyTreeMemberDTO.getSex().ordinal() + ", " +
            "'" + familyTreeMemberDTO.getFirstName() + "', " +
            "'" + familyTreeMemberDTO.getMiddleName() + "', " +
            "'" + familyTreeMemberDTO.getLastName() + "', " +
            "'" + familyTreeMemberDTO.getBirthDate() + "', " +
            "'" + familyTreeMemberDTO.getDeathDate() + "', " +
            "'" + familyTreeMemberDTO.getNotes() + "'" +
        ")";

        when(mockDbManager.insert(expectedStatement)).thenReturn(expectedId);

        assertEquals(expectedId, classToTest.createFamilyMember(familyTreeMemberDTO));
    }

    @Test
    void testCreateFamilyMemberHandlesOptionalsColumns() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        familyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREE_MEMBERS (FAMILY_TREE_ID, SEX, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DATE) VALUES (" +
                familyTreeMemberDTO.getFamilyTreeId() + ", " +
                familyTreeMemberDTO.getSex().ordinal() + ", " +
                "'" + familyTreeMemberDTO.getFirstName() + "', " +
                "'" + familyTreeMemberDTO.getMiddleName() + "', " +
                "'" + familyTreeMemberDTO.getLastName() + "', " +
                "'" + familyTreeMemberDTO.getBirthDate() + "'" +
                ")";

        when(mockDbManager.insert(expectedStatement)).thenReturn(expectedId);

        assertEquals(expectedId, classToTest.createFamilyMember(familyTreeMemberDTO));
    }

    @Test
    void testGetAllFamilyTreesReturnFamilyTreesMappedFromQuery() throws Exception {
        int expectedFamilyTreeId = 2;
        List<FamilyTreeMemberDTO> expectedFamilyTreeMemberDTOs = Collections.singletonList(new FamilyTreeMemberDTO());
        String expectedStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE FAMILY_TREE_ID = " + expectedFamilyTreeId;

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockDbManager.query(expectedStatement)).thenReturn(mockResultSet);
        when(mockFamilyTreeMembersMapper.map(mockResultSet)).thenReturn(expectedFamilyTreeMemberDTOs);

        assertEquals(expectedFamilyTreeMemberDTOs, classToTest.getFamilyMembers(expectedFamilyTreeId));
    }
}
