package com.wright.ftm.dtos;

import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyTreeMemberDTO {
    private Date birthDate;
    private List<FamilyTreeMemberDTO> children = new ArrayList<>();
    private List<String> childIds = new ArrayList<>();
    private Date deathDate;
    private FamilyTreeDTO familyTree;
    private int familyTreeId;
    private String firstName;
    private int id;
    private String lastName;
    private String middleName = "";
    private String notes;
    private List<FamilyTreeMemberDTO> parents = new ArrayList<>();
    private List<String> parentIds = new ArrayList<>();
    private Sex sex;

    public void setFamilyTree(FamilyTreeDTO familyTree) {
        this.familyTree = familyTree;
        if (familyTree == null) {
            familyTreeId = 0;
        } else {
            familyTreeId = familyTree.getId();
        }
    }

    public String getFullName() {
        String fullName = firstName + " ";

        if (StringUtils.isNotEmpty(middleName)) {
            fullName += middleName + " ";
        }

        fullName += lastName;

        return fullName;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addParent(FamilyTreeMemberDTO parent) {
        List<FamilyTreeMemberDTO> matchingParents = parents.stream().filter(p -> p.getId() == parent.getId()).collect(Collectors.toList());
        if (parent != null && matchingParents.isEmpty()) {
            parents.add(parent);
            parentIds.clear();

            parent.addChild(this);
        }
    }

    public List<FamilyTreeMemberDTO> getParents() {
        return parents;
    }

    public String getParentIds() {
        if (parentIds.size() > 0) {
            return StringUtils.join(parentIds, ",");
        } else {
            List<String> pIds = parents.stream().map(p -> Integer.toString(p.getId())).collect(Collectors.toList());
            return String.join(",", pIds);
        }
    }

    public void addChild(FamilyTreeMemberDTO child) {
        List<FamilyTreeMemberDTO> matchingChildren = children.stream().filter(c -> c.getId() == child.getId()).collect(Collectors.toList());
        if (child != null && matchingChildren.isEmpty()) {
            children.add(child);
            childIds.clear();

            child.addParent(this);
        }
    }

    public void setParentIds(String parentIdsString) {
        if (parentIdsString != null) {
            parentIds.addAll(Arrays.asList(parentIdsString.split(",")));
        }
    }

    public void setChildIds(String childIdsString) {
        if (childIdsString != null) {
            childIds.addAll(Arrays.asList(childIdsString.split(",")));
        }
    }

    public String getChildIds() {
        if (childIds.size() > 0) {
            return StringUtils.join(childIds, ",");
        } else {
            List<String> cIds = children.stream().map(c -> Integer.toString(c.getId())).collect(Collectors.toList());
            return String.join(",", cIds);
        }
    }

    public List<FamilyTreeMemberDTO> getChildren() {
        return children;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
