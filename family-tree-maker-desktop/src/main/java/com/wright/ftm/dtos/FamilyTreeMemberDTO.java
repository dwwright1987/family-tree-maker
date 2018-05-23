package com.wright.ftm.dtos;

public class FamilyTreeMemberDTO {
    private FamilyTreeDTO familyTree;
    private int familyTreeId;
    private String firstName;
    private int id;
    private String lastName;
    private String middleName = "";
    private Sex sex;

    public void setFamilyTree(FamilyTreeDTO familyTree) {
        this.familyTree = familyTree;
        if (familyTree == null) {
            familyTreeId = 0;
        } else {
            familyTreeId = familyTree.getId();
        }
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setMiddleName(String middleName) {
        if (middleName == null) {
            this.middleName = "";
        } else {
            this.middleName = middleName;
        }
    }

    public String getMiddleName() {
        return middleName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
