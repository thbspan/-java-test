package com.test.thread;

import org.junit.jupiter.api.Test;

public class InheritableThreadLocalTest {

    @Test
    public void test() throws InterruptedException {
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set("test inheritable");

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("test thread local");
        Thread thread = new Thread(() -> {
            System.out.println(inheritableThreadLocal.get());
            System.out.println(threadLocal.get());
        });
        thread.start();
        thread.join();
    }
}
