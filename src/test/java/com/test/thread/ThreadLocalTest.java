package com.test.thread;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testGet() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        System.out.println(threadLocal.get());
    }
}
