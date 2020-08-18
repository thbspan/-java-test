package com.test;

import org.junit.Test;

public class RuntimeTest {

    @Test
    public void testAvailableProcessors() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void testMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.freeMemory());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.maxMemory());
    }

    @Test
    public void testFinally() {
        System.out.println(sum());
    }

    public static int sum() {
        int a;
        try {
            a = 10 / 2;
            return a;
        } finally {
            a=4;
            return a;
        }
    }
}
