package com.test;

import org.junit.Test;

public class RuntimeTest {

    @Test
    public void testAvailableProcessors() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
