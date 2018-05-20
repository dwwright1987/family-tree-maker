package com.wright.ftm.dtos;

public class FamilyTreeMemberDTO {
    private FamilyTreeDTO familyTree;
    private int familyTreeId;
    private int id;
    private Sex sex;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
