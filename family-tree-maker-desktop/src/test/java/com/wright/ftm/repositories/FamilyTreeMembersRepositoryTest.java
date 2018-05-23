package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.mappers.FamilyTreeMembersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.Collections;
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
    void testCreateFamilyNameReturnsIdForInsertIntoFamilyNamesTable() throws Exception {
        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");

        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREE_MEMBERS (FAMILY_TREE_ID, SEX, FIRST_NAME, MIDDLE_NAME, LAST_NAME) VALUES (" +
            familyTreeMemberDTO.getFamilyTreeId() + ", " +
            familyTreeMemberDTO.getSex().ordinal() + ", " +
            "'" + familyTreeMemberDTO.getFirstName() + "', " +
            "'" + familyTreeMemberDTO.getMiddleName() + "', " +
            "'" + familyTreeMemberDTO.getLastName() + "'" +
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
