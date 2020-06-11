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
}
