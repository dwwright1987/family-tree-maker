package com.wright.ftm.mappers;

import com.wright.ftm.dtos.FamilyTreeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreesMapperTest {
    private FamilyTreesMapper classToTest;
    private ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreesMapper();
    }

    @Test
    void testMapReturnsEmptyListWhenThereIsAnEmptyResultSet() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        List<FamilyTreeDTO> familyTreeDTOs = classToTest.map(mockResultSet);

        assertTrue(familyTreeDTOs.isEmpty());
    }

    @Test
    void testMapReturnsListOfFamilyTreeDTOs() throws Exception {
        int expectedId1 = 1;
        int expectedId2 = 2;
        String expectedName1 = "Wright";
        String expectedName2 = "Nusum";

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(expectedId1, expectedId2);
        when(mockResultSet.getString("NAME")).thenReturn(expectedName1, expectedName2);

        List<FamilyTreeDTO> familyTreeDTOs = classToTest.map(mockResultSet);

        assertEquals(2, familyTreeDTOs.size());

        FamilyTreeDTO familyTreeDTO1 = familyTreeDTOs.get(0);
        assertEquals(expectedId1, familyTreeDTO1.getId());
        assertEquals(expectedName1, familyTreeDTO1.getName());

        FamilyTreeDTO familyTreeDTO2 = familyTreeDTOs.get(1);
        assertEquals(expectedId2, familyTreeDTO2.getId());
        assertEquals(expectedName2, familyTreeDTO2.getName());
    }

    @Test
    void testMapDoesNotThrowSQLException() throws Exception {
        when(mockResultSet.next()).thenThrow(new SQLException());

        try {
            List<FamilyTreeDTO> familyTreeDTOs = classToTest.map(mockResultSet);

            assertTrue(familyTreeDTOs.isEmpty());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
}
