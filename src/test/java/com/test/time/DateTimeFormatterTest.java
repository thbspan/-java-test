package com.test.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.junit.Test;

public class DateTimeFormatterTest {

    /**
     * yyyyMM 格式日期转化成 LocalDate 对象
     */
    @Test
    public void testFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        System.out.println(LocalDate.parse("202001", formatter));
    }
}
