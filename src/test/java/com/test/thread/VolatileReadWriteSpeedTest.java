package com.test.thread;

import org.junit.jupiter.api.Test;

public class VolatileReadWriteSpeedTest {

    private static int AA = 10;
    private static volatile int BB = 10;

    @Test
    public void testRead() {
        int a = 0;
        int rep = 2000000000;
        long time, time2;

        time = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "start read test ...");
        for (int i = 0; i < rep; ++i) {
            a = AA;
        }
        time2 = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "finish read test : " + (time2 - time));

        time = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "start read volatile test ...");
        for (int i = 0; i < rep; ++i) {
            a = BB;
        }
        time2 = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "finish read volatile test : " + (time2 - time));

        System.out.println("TEST_VOLATILE " + "" + a);
    }

    @Test
    public void testWrite() {
        int a = 0;
        int rep = 2000000000;
        long time, time2;

        time = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "start write test ...");
        for (int i = 0 ; i < rep ; ++i) {
            AA = i;
        }
        time2 = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE " + "finish write test : " + (time2 - time));

        time = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE "+ "start write volatile test ...");
        for (int i = 0 ; i < rep ; ++i) {
            BB = i;
        }
        time2 = System.currentTimeMillis();
        System.out.println("TEST_VOLATILE "+ "finish write volatile test : " + (time2 - time));

        System.out.println("TEST_VOLATILE "+ "" + AA + "," + BB);
    }
}
