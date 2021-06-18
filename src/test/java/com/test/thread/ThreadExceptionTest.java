package com.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 测试捕获线程池异常
 */
public class ThreadExceptionTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("--------------------");
            System.out.println(t.getName());
            e.printStackTrace();
        });

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setUncaughtExceptionHandler((t, e) -> {
            System.out.println("====================");
            e.printStackTrace();
        }).build();
        ExecutorService executorService = Executors.newFixedThreadPool(2, threadFactory);

        executorService.execute(() -> {
            throw new RuntimeException("for test");
        });

        Thread.sleep(2000);
        executorService.shutdown();
    }
}

