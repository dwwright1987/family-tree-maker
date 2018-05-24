package com.wright.ftm.mappers;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreeMembersMapperTest {
    private FamilyTreeMembersMapper classToTest;
    private ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreeMembersMapper();
    }

    @Test
    void testMapReturnsEmptyListWhenThereIsAnEmptyResultSet() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = classToTest.map(mockResultSet);

        assertTrue(familyTreeMemberDTOs.isEmpty());
    }

    @Test
    void testMapReturnsListOfFamilyTreeMemberDTOs() throws Exception {
        Calendar calendar1 = new GregorianCalendar();
        calendar1.set(1987, Calendar.SEPTEMBER, 1);
        Calendar calendar2 = new GregorianCalendar();
        calendar2.set(1985, Calendar.MARCH, 21);

        int expectedId1 = 1;
        int expectedId2 = 2;
        int expectedFamilyTreeId1 = 3;
        int expectedFamilyTreeId2 = 4;
        String expectedFirstName1 = "Daniel";
        String expectedFirstName2 = "Laura";
        String expectedMiddleName1 = "Woolf";
        String expectedLastName1 = "Wright";
        String expectedLastName2 = "wright";
        Date expectedBirthDate1 = new Date(calendar1.getTimeInMillis());
        Date expectedBirthDate2 = new Date(calendar2.getTimeInMillis());

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(expectedId1, expectedId2);
        when(mockResultSet.getInt("FAMILY_TREE_ID")).thenReturn(expectedFamilyTreeId1, expectedFamilyTreeId2);
        when(mockResultSet.getInt("SEX")).thenReturn(1, 0);
        when(mockResultSet.getString("FIRST_NAME")).thenReturn(expectedFirstName1, expectedFirstName2);
        when(mockResultSet.getString("MIDDLE_NAME")).thenReturn(expectedMiddleName1, null);
        when(mockResultSet.getString("LAST_NAME")).thenReturn(expectedLastName1, expectedLastName2);
        when(mockResultSet.getDate("BIRTH_DATE")).thenReturn(expectedBirthDate1, expectedBirthDate2);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = classToTest.map(mockResultSet);

        assertEquals(2, familyTreeMemberDTOs.size());

        FamilyTreeMemberDTO familyTreeMemberDTO1 = familyTreeMemberDTOs.get(0);
        assertEquals(expectedId1, familyTreeMemberDTO1.getId());
        assertEquals(expectedFamilyTreeId1, familyTreeMemberDTO1.getFamilyTreeId());
        assertEquals(MALE, familyTreeMemberDTO1.getSex());
        assertEquals(expectedFirstName1, familyTreeMemberDTO1.getFirstName());
        assertEquals(expectedMiddleName1, familyTreeMemberDTO1.getMiddleName());
        assertEquals(expectedLastName1, familyTreeMemberDTO1.getLastName());
        assertEquals(expectedBirthDate1, familyTreeMemberDTO1.getBirthDate());

        FamilyTreeMemberDTO familyTreeMemberDTO2 = familyTreeMemberDTOs.get(1);
        assertEquals(expectedId2, familyTreeMemberDTO2.getId());
        assertEquals(expectedFamilyTreeId2, familyTreeMemberDTO2.getFamilyTreeId());
        assertEquals(FEMALE, familyTreeMemberDTO2.getSex());
        assertEquals(expectedFirstName2, familyTreeMemberDTO2.getFirstName());
        assertEquals(0, familyTreeMemberDTO2.getMiddleName().length());
        assertEquals(expectedLastName2, familyTreeMemberDTO2.getLastName());
        assertEquals(expectedBirthDate2, familyTreeMemberDTO2.getBirthDate());
    }

    @Test
    void testMapDoesNotThrowSQLException() throws Exception {
        when(mockResultSet.next()).thenThrow(new SQLException());

        try {
            assertTrue(classToTest.map(mockResultSet).isEmpty());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
}
