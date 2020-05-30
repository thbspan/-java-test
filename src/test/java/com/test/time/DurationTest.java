package com.test.time;

import java.time.Duration;

import org.junit.Test;

public class DurationTest {

    @Test
    public void test() {
        Duration fromDays = Duration.ofDays(1);
        System.out.println(fromDays.toSeconds()); // 86400
        System.out.println(fromDays.getSeconds()); // 86400
        System.out.println(fromDays.getNano()); // 0
    }

    @Test
    public void testParse() {
        Duration fromChar1 = Duration.parse("P1DT1H10M10.5S");
        Duration fromChar2 = Duration.parse("PT10M");
        System.out.println(fromChar1);
        System.out.println(fromChar2);
    }
}
