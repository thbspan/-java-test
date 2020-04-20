package com.test.time;

import java.time.LocalDate;

import org.junit.Test;

public class LocalDateTest {

    @Test
    public void test() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        System.out.println(now.minusYears(1));
    }
}
