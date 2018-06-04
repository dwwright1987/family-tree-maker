package com.wright.ftm.utils;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    public static LocalDate getLocalDate(Date sqlDate) {
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }

    public static Date getSqlDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }
}
