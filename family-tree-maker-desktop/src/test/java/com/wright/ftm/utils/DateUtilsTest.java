package com.wright.ftm.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateUtilsTest {
    @Test
    void testGetGetSqlDateReturnsSqlDateForLocalDate() throws Exception {
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
    void testGetSqlDateReturnsNullForNullLocalDate() throws Exception {
        assertNull(DateUtils.getSqlDate(null));
    }

    @Test
    void testGetLocalDateReturnLocalDateForSqlDate() throws Exception {
        int expectedYear = 1987;
        int expectedMonth = Calendar.SEPTEMBER;
        int expectedDay = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(expectedYear, expectedMonth, expectedDay);

        Date sqlDate = new Date(calendar.getTimeInMillis());

        LocalDate localDate = DateUtils.getLocalDate(sqlDate);

        assertEquals(expectedYear, localDate.getYear());
        assertEquals(expectedMonth, localDate.getMonth().getValue() - 1);
        assertEquals(expectedDay, localDate.getDayOfMonth());
    }

    @Test
    void testGetLocalDateReturnsNullForNullSqlDate() throws Exception {
        assertNull(DateUtils.getLocalDate(null));
    }
}
