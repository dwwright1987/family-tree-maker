package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.mappers.FamilyTreeMembersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.*;

import static com.wright.ftm.dtos.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

        FamilyTreeMemberDTO parent1 = new FamilyTreeMemberDTO();
        parent1.setId(1);

        FamilyTreeMemberDTO parent2 = new FamilyTreeMemberDTO();
        parent2.setId(2);

        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");
        familyTreeMemberDTO.setNotes("some notes");
        familyTreeMemberDTO.addParent(parent1);
        familyTreeMemberDTO.addParent(parent2);

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        familyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        calendar.set(2077, Calendar.AUGUST, 14);
        familyTreeMemberDTO.setDeathDate(new Date(calendar.getTimeInMillis()));

        int expectedId = 30;
        String expectedStatement = "INSERT INTO FAMILY_TREE_MEMBERS (FAMILY_TREE_ID, SEX, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DATE, DEATH_DATE, NOTES, PARENT_IDS) VALUES (" +
            familyTreeMemberDTO.getFamilyTreeId() + ", " +
            familyTreeMemberDTO.getSex().ordinal() + ", " +
            "'" + familyTreeMemberDTO.getFirstName() + "', " +
            "'" + familyTreeMemberDTO.getMiddleName() + "', " +
            "'" + familyTreeMemberDTO.getLastName() + "', " +
            "'" + familyTreeMemberDTO.getBirthDate() + "', " +
            "'" + familyTreeMemberDTO.getDeathDate() + "', " +
            "'" + familyTreeMemberDTO.getNotes() + "', " +
            "'" + familyTreeMemberDTO.getParentIds() + "'" +
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
    void testGetFamilyTreeMembersReturnFamilyTreeMembersMappedFromQuery() throws Exception {
        int expectedFamilyTreeId = 2;
        List<FamilyTreeMemberDTO> expectedFamilyTreeMemberDTOs = Collections.singletonList(new FamilyTreeMemberDTO());
        String expectedStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE FAMILY_TREE_ID = " + expectedFamilyTreeId;

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockDbManager.query(expectedStatement)).thenReturn(mockResultSet);
        when(mockFamilyTreeMembersMapper.map(mockResultSet)).thenReturn(expectedFamilyTreeMemberDTOs);

        assertEquals(expectedFamilyTreeMemberDTOs, classToTest.getFamilyMembers(expectedFamilyTreeId));
    }

    @Test
    void testPopulatesParentsWhenGettingFamilyTreeMembers() throws Exception {
        FamilyTreeMemberDTO expectedParent1 = new FamilyTreeMemberDTO();
        expectedParent1.setId(1);
        FamilyTreeMemberDTO expectedParent2 = new FamilyTreeMemberDTO();
        expectedParent2.setId(2);

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setId(3);
        expectedFamilyTreeMemberDTO.setParentIds(String.join(",", Arrays.asList(Integer.toString(expectedParent1.getId()), Integer.toString(expectedParent2.getId()))));

        int expectedFamilyTreeId = 2;
        String expectedFamilyTreeStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE FAMILY_TREE_ID = " + expectedFamilyTreeId;
        String expectedFamilyMemberBaseStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE ID = ";

        ResultSet mockFamilyTreeResultSet = mock(ResultSet.class);
        ResultSet mockParent1ResultSet = mock(ResultSet.class);
        ResultSet mockParent2ResultSet = mock(ResultSet.class);

        when(mockDbManager.query(expectedFamilyTreeStatement)).thenReturn(mockFamilyTreeResultSet);
        when(mockDbManager.query(expectedFamilyMemberBaseStatement + Integer.toString(expectedParent1.getId()))).thenReturn(mockParent1ResultSet);
        when(mockDbManager.query(expectedFamilyMemberBaseStatement + Integer.toString(expectedParent2.getId()))).thenReturn(mockParent2ResultSet);
        when(mockFamilyTreeMembersMapper.map(mockFamilyTreeResultSet)).thenReturn(Collections.singletonList(expectedFamilyTreeMemberDTO));
        when(mockFamilyTreeMembersMapper.map(mockParent1ResultSet)).thenReturn(Collections.singletonList(expectedParent1));
        when(mockFamilyTreeMembersMapper.map(mockParent2ResultSet)).thenReturn(Collections.singletonList(expectedParent2));

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = classToTest.getFamilyMembers(expectedFamilyTreeId).get(0);

        assertEquals(2, actualFamilyTreeMemberDTO.getParents().size());
        assertEquals(expectedParent1.getId(), actualFamilyTreeMemberDTO.getParents().get(0).getId());
        assertEquals(0, actualFamilyTreeMemberDTO.getParents().get(0).getParents().size());
        assertEquals(expectedParent2.getId(), actualFamilyTreeMemberDTO.getParents().get(1).getId());
        assertEquals(0, actualFamilyTreeMemberDTO.getParents().get(1).getParents().size());
    }

    @Test
    void testHandlesNotMatchingParent() throws Exception {
        FamilyTreeMemberDTO expectedParent1 = new FamilyTreeMemberDTO();
        expectedParent1.setId(1);

        FamilyTreeMemberDTO expectedFamilyTreeMemberDTO = new FamilyTreeMemberDTO();
        expectedFamilyTreeMemberDTO.setId(3);
        expectedFamilyTreeMemberDTO.setParentIds(String.join(",", Collections.singletonList(Integer.toString(expectedParent1.getId()))));

        int expectedFamilyTreeId = 2;
        String expectedFamilyTreeStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE FAMILY_TREE_ID = " + expectedFamilyTreeId;
        String expectedFamilyMemberBaseStatement = "SELECT * FROM FAMILY_TREE_MEMBERS WHERE ID = ";

        ResultSet mockFamilyTreeResultSet = mock(ResultSet.class);
        ResultSet mockParent1ResultSet = mock(ResultSet.class);

        when(mockDbManager.query(expectedFamilyTreeStatement)).thenReturn(mockFamilyTreeResultSet);
        when(mockDbManager.query(expectedFamilyMemberBaseStatement + Integer.toString(expectedParent1.getId()))).thenReturn(mockParent1ResultSet);
        when(mockFamilyTreeMembersMapper.map(mockFamilyTreeResultSet)).thenReturn(Collections.singletonList(expectedFamilyTreeMemberDTO));
        when(mockFamilyTreeMembersMapper.map(mockParent1ResultSet)).thenReturn(Collections.emptyList());

        FamilyTreeMemberDTO actualFamilyTreeMemberDTO = classToTest.getFamilyMembers(expectedFamilyTreeId).get(0);

        assertEquals(0, actualFamilyTreeMemberDTO.getParents().size());
    }

    @Test
    void testUpdateFamilyMemberUpdatesFamilyMember() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO parent = new FamilyTreeMemberDTO();
        parent.setId(1);

        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setId(30);
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");
        familyTreeMemberDTO.setNotes("some notes");
        familyTreeMemberDTO.addParent(parent);

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        familyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        calendar.set(2077, Calendar.AUGUST, 14);
        familyTreeMemberDTO.setDeathDate(new Date(calendar.getTimeInMillis()));

        String expectedStatement = "UPDATE FAMILY_TREE_MEMBERS SET " +
            "FAMILY_TREE_ID=" + familyTreeMemberDTO.getFamilyTreeId() + ", " +
            "SEX=" + familyTreeMemberDTO.getSex().ordinal() + ", " +
            "FIRST_NAME='" + familyTreeMemberDTO.getFirstName() + "', " +
            "MIDDLE_NAME='" + familyTreeMemberDTO.getMiddleName() + "', " +
            "LAST_NAME='" + familyTreeMemberDTO.getLastName() + "', " +
            "BIRTH_DATE='" + familyTreeMemberDTO.getBirthDate() + "', " +
            "DEATH_DATE='" + familyTreeMemberDTO.getDeathDate() + "', " +
            "NOTES='" + familyTreeMemberDTO.getNotes() + "', " +
            "PARENT_IDS='" + familyTreeMemberDTO.getParentIds() + "' " +
            "WHERE ID=" + familyTreeMemberDTO.getId();

        classToTest.updateFamilyMember(familyTreeMemberDTO);

        verify(mockDbManager).update(expectedStatement);
    }

    @Test
    void testUpdateFamilyMemberHandlesOptionalColumns() throws Exception {
        Calendar calendar = new GregorianCalendar();

        FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
        familyTreeMemberDTO.setId(30);
        familyTreeMemberDTO.setFamilyTreeId(29);
        familyTreeMemberDTO.setSex(MALE);
        familyTreeMemberDTO.setFirstName("Daniel");
        familyTreeMemberDTO.setMiddleName("Woolf");
        familyTreeMemberDTO.setLastName("Wright");

        calendar.set(1987, Calendar.SEPTEMBER, 1);
        familyTreeMemberDTO.setBirthDate(new Date(calendar.getTimeInMillis()));

        String expectedStatement = "UPDATE FAMILY_TREE_MEMBERS SET " +
                "FAMILY_TREE_ID=" + familyTreeMemberDTO.getFamilyTreeId() + ", " +
                "SEX=" + familyTreeMemberDTO.getSex().ordinal() + ", " +
                "FIRST_NAME='" + familyTreeMemberDTO.getFirstName() + "', " +
                "MIDDLE_NAME='" + familyTreeMemberDTO.getMiddleName() + "', " +
                "LAST_NAME='" + familyTreeMemberDTO.getLastName() + "', " +
                "BIRTH_DATE='" + familyTreeMemberDTO.getBirthDate() + "', " +
                "DEATH_DATE=NULL, " +
                "NOTES=NULL, " +
                "PARENT_IDS='' " +
                "WHERE ID=" + familyTreeMemberDTO.getId();

        classToTest.updateFamilyMember(familyTreeMemberDTO);

        verify(mockDbManager).update(expectedStatement);
    }
}
