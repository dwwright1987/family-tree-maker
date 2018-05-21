package com.wright.ftm.mappers;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        int expectedId1 = 1;
        int expectedId2 = 2;
        int expectedFamilyTreeId1 = 3;
        int expectedFamilyTreeId2 = 4;

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(expectedId1, expectedId2);
        when(mockResultSet.getInt("FAMILY_TREE_ID")).thenReturn(expectedFamilyTreeId1, expectedFamilyTreeId2);
        when(mockResultSet.getInt("SEX")).thenReturn(1, 0);

        List<FamilyTreeMemberDTO> familyTreeMemberDTOs = classToTest.map(mockResultSet);

        assertEquals(2, familyTreeMemberDTOs.size());

        FamilyTreeMemberDTO familyTreeMemberDTO1 = familyTreeMemberDTOs.get(0);
        assertEquals(expectedId1, familyTreeMemberDTO1.getId());
        assertEquals(expectedFamilyTreeId1, familyTreeMemberDTO1.getFamilyTreeId());
        assertEquals(MALE, familyTreeMemberDTO1.getSex());

        FamilyTreeMemberDTO familyTreeMemberDTO2 = familyTreeMemberDTOs.get(1);
        assertEquals(expectedId2, familyTreeMemberDTO2.getId());
        assertEquals(expectedFamilyTreeId2, familyTreeMemberDTO2.getFamilyTreeId());
        assertEquals(FEMALE, familyTreeMemberDTO2.getSex());
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
