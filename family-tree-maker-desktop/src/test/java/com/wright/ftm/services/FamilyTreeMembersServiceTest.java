package com.wright.ftm.services;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.repositories.FamilyTreeMembersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FamilyTreeMembersServiceTest {
    private FamilyTreeMembersService classToTest;
    private FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
    private FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
    private FamilyTreeMembersRepository mockFamilyTreeMembersRepository = mock(FamilyTreeMembersRepository.class);

    @BeforeEach
    void setUp() throws Exception {
        familyTreeDTO.setId(10);

        classToTest = new FamilyTreeMembersService();
        classToTest.setFamilyTreeMembersRepository(mockFamilyTreeMembersRepository);
    }

    @Test
    void testCreateFamilyMemberCreateMemberForFamily() throws Exception {
        classToTest.createFamilyMember(familyTreeMemberDTO, familyTreeDTO);

        ArgumentCaptor<FamilyTreeMemberDTO> familyTreeMemberDTOArgumentCaptor = ArgumentCaptor.forClass(FamilyTreeMemberDTO.class);

        verify(mockFamilyTreeMembersRepository).createFamilyMember(familyTreeMemberDTOArgumentCaptor.capture());

        assertEquals(familyTreeDTO, familyTreeMemberDTO.getFamilyTree());
    }

    @Test
    void testCreateFamilyMemberReturnIdOfCreatedMember() throws Exception {
        int expectedId = 12;

        when(mockFamilyTreeMembersRepository.createFamilyMember(familyTreeMemberDTO)).thenReturn(expectedId);

        FamilyTreeMemberDTO actualFamilyMemberDTO = classToTest.createFamilyMember(familyTreeMemberDTO, familyTreeDTO);

        assertEquals(expectedId, actualFamilyMemberDTO.getId());
    }

    @Test
    void testCreateFamilyMemberReturnsNullWhenThereIsAnError() throws Exception {
        when(mockFamilyTreeMembersRepository.createFamilyMember(familyTreeMemberDTO)).thenThrow(new SQLException());

        assertNull(classToTest.createFamilyMember(familyTreeMemberDTO, familyTreeDTO));
    }

    @Test
    void testGetFamilyMembersReturnsFamilyMemberForFamilyTree() throws Exception {
        List<FamilyTreeMemberDTO> expectedFamilyTreeMemberDTOs = Collections.singletonList(familyTreeMemberDTO);

        when(mockFamilyTreeMembersRepository.getFamilyMembers(familyTreeDTO.getId())).thenReturn(expectedFamilyTreeMemberDTOs);

        assertEquals(expectedFamilyTreeMemberDTOs, classToTest.getFamilyMembers(familyTreeDTO));
    }

    @Test
    void testGetFamilyMembersReturnEmptyListWhenThereIsAnError() throws Exception {
        when(mockFamilyTreeMembersRepository.getFamilyMembers(familyTreeDTO.getId())).thenThrow(new SQLException());

        assertTrue(classToTest.getFamilyMembers(familyTreeDTO).isEmpty());
    }
}
