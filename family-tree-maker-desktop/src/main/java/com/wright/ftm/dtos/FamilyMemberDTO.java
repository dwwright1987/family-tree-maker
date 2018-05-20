package com.wright.ftm.dtos;

public class FamilyMemberDTO {
    private FamilyTreeDTO familyTree;
    private int familyTreeId;
    private Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setFamilyTree(FamilyTreeDTO familyTree) {
        this.familyTree = familyTree;
        familyTreeId = familyTree.getId();
    }

    public FamilyTreeDTO getFamilyTree() {
        return familyTree;
    }

    public int getFamilyTreeId() {
        return familyTreeId;
    }

    public void setFamilyTreeId(int familyTreeId) {
        this.familyTreeId = familyTreeId;
    }
}
