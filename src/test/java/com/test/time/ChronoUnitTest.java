package com.test.time;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class ChronoUnitTest {

    /**
     * 计算两个时间相差的月份
     */
    @Test
    public void testBetweenMonth() {
        LocalDate one = LocalDate.of(2020, 5, 30);
        LocalDate two = LocalDate.of(2020, 4, 30);

        System.out.println(ChronoUnit.MONTHS.between(two, one));// 1

        LocalDate three = LocalDate.of(2020, 5, 29);

        System.out.println(ChronoUnit.MONTHS.between(two, three));// 0

        YearMonth left = YearMonth.of(2020, 9);
        YearMonth right = YearMonth.of(2020, 8);
        System.out.println(ChronoUnit.MONTHS.between(right, left));// 0
    }
}
