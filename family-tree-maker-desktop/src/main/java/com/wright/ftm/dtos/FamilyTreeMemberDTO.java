package com.wright.ftm.dtos;

import java.sql.Date;

public class FamilyTreeMemberDTO {
    private Date birthDate;
    private Date deathDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public int getFamilyTreeId() {
        return familyTreeId;
    }

    public FamilyTreeDTO getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTreeId(int familyTreeId) {
        this.familyTreeId = familyTreeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if (middleName == null) {
            this.middleName = "";
        } else {
            this.middleName = middleName;
        }
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
