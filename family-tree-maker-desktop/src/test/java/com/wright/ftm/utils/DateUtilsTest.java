package com.wright.ftm.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateUtilsTest {
    @Test
    void getGetSqlDateReturnsSqlDateForLocaDate() throws Exception {
        int expectedYear = 1987;
        int expectedMonth = 9;
        int expectedDay = 1;

        LocalDate localDate = LocalDate.of(expectedYear, expectedMonth, expectedDay);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getSqlDate(localDate));

        assertEquals(expectedYear, calendar.get(Calendar.YEAR));
        assertEquals(expectedMonth, calendar.get(Calendar.MONTH) + 1);
        assertEquals(expectedDay, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void getSqlDateReturnsNullForNullLocalDate() throws Exception {
        assertNull(DateUtils.getSqlDate(null));
    }
}
