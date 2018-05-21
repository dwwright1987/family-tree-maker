package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FamilyTreeMembersRepositoryTest {
    private FamilyTreeMembersRepository classToTest;
    private DbManager mockDbManager = mock(DbManager.class);

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreeMembersRepository();
        classToTest.setDbManager(mockDbManager);
    }

    @Test
    void testCreateFamilyNameReturnsIdForInsertIntoFamilyNamesTable() throws Exception {
        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);

        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREE_MEMBERS (FAMILY_TREE_ID, SEX) VALUES (" +
                familyTreeMemberDTO.getFamilyTreeId() + ", " +
                familyTreeMemberDTO.getSex().ordinal() +
                ")";

        when(mockDbManager.insert(expectedStatement)).thenReturn(expectedId);

        assertEquals(expectedId, classToTest.createFamilyMember(familyTreeMemberDTO));
    }
}
