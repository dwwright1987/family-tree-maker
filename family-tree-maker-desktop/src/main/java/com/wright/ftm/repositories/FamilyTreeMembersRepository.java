package com.wright.ftm.repositories;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;

import java.sql.SQLException;
import java.util.List;

public class FamilyTreeMembersRepository {
    public int createFamilyMember(FamilyTreeMemberDTO familyTreeMemberDTO) throws SQLException {
        return 0;
    }

    public List<FamilyTreeMemberDTO> getFamilyMembers(int familyTreeId) throws SQLException {
        return null;
    }
}
