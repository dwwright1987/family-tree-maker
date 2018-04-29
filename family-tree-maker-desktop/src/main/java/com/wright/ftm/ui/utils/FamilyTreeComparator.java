package com.wright.ftm.ui.utils;

import com.wright.ftm.dtos.FamilyTreeDTO;

import java.util.Comparator;

public class FamilyTreeComparator implements Comparator {
    private static final int EQUAL_VALUE = 0;
    private static final int GREAT_THAN_VALUE = 1;
    private static final int LESS_THAN_VALUE = -1;

    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof FamilyTreeDTO) && !(o2 instanceof FamilyTreeDTO)) {
          return EQUAL_VALUE;
        } else if (!(o1 instanceof FamilyTreeDTO)) {
            return GREAT_THAN_VALUE;
        } else if (!(o2 instanceof FamilyTreeDTO)) {
            return LESS_THAN_VALUE;
        } else {
            String familyTreeName1 = ((FamilyTreeDTO) o1).getName();
            String familyTreeName2 = ((FamilyTreeDTO) o2).getName();
            if (familyTreeName1 == null && familyTreeName2 == null) {
                return EQUAL_VALUE;
            } else if (familyTreeName1 == null) {
                return GREAT_THAN_VALUE;
            } else if (familyTreeName2 == null) {
                return LESS_THAN_VALUE;
            } else {
                return familyTreeName1.compareToIgnoreCase(familyTreeName2);
            }
        }
    }
}
