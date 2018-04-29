package com.wright.ftm.ui.utils;

import com.wright.ftm.dtos.FamilyTreeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FamilyTreeComparatorTest {
    private FamilyTreeComparator classToTest;
    private FamilyTreeDTO familyTreeDTO1 = new FamilyTreeDTO();
    private FamilyTreeDTO familyTreeDTO2 = new FamilyTreeDTO();

    @BeforeEach
    void setUp() throws Exception {
        classToTest = new FamilyTreeComparator();
    }

    @Test
    void testCompareReturnsNegative1WhenFamilyTreeName1IsAlphabeticallyBeforeFamilyTreeName2() throws Exception {
        familyTreeDTO1.setName("a");
        familyTreeDTO2.setName("B");

        assertEquals(-1, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsZeroWhenFamilyTreeName1IsAlphabeticallyTheSameAsFamilyTreeName2() throws Exception {
        familyTreeDTO1.setName("a");
        familyTreeDTO2.setName(familyTreeDTO1.getName());

        assertEquals(0, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsPositive1WhenFamilyTreeName1IsAlphabeticallyAfterFamilyTreeName2() throws Exception {
        familyTreeDTO1.setName("B");
        familyTreeDTO2.setName("a");

        assertEquals(1, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsPositive1WhenFamilyTree1IsNull() throws Exception {
        familyTreeDTO2.setName("B");

        assertEquals(1, classToTest.compare(null, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsPositive1WhenFirstObjectIsNotAFamilyTree() throws Exception {
        familyTreeDTO2.setName("B");

        assertEquals(1, classToTest.compare(new Object(), familyTreeDTO2));
    }

    @Test
    void testCompareReturnsNegative1WhenFamilyTree2IsNull() throws Exception {
        familyTreeDTO1.setName("a");

        assertEquals(-1, classToTest.compare(familyTreeDTO1, null));
    }

    @Test
    void testCompareReturnsNegative1WhenSecondObjectIsNotAFamilyTree() throws Exception {
        familyTreeDTO1.setName("a");

        assertEquals(-1, classToTest.compare(familyTreeDTO1, new Object()));
    }

    @Test
    void testCompareReturnsZeroWhenBothFamilyTreesAreNull() throws Exception {
        assertEquals(0, classToTest.compare(null, null));
    }

    @Test
    void testCompareReturnsZeroWhenBothObjectsAreNotFamilyTrees() throws Exception {
        assertEquals(0, classToTest.compare(new Object(), new Object()));
    }

    @Test
    void testCompareReturnsPositive1WhenFamilyTreeName1IsNull() throws Exception {
        familyTreeDTO2.setName("B");

        assertEquals(1, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsNegative1WhenFamilyTreeName2IsNull() throws Exception {
        familyTreeDTO1.setName("a");

        assertEquals(-1, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }

    @Test
    void testCompareReturnsZeroWhenBothFamilyTreeNamessAreNull() throws Exception {
        assertEquals(0, classToTest.compare(familyTreeDTO1, familyTreeDTO2));
    }
}
