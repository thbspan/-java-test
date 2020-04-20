package com.test.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class LockSupportTest {

    /**
     * 测试 LockSupport.park 是否会响应线程中断
     */
    @Test
    public void testThreadInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            long end = 0;

            int count = 0;
            while ((end - start) <= 1000) {
                count++;
                end = System.currentTimeMillis();
            }
            System.out.println("after 1 second.count=" + count);
            LockSupport.park();
            System.out.println("thread over." + Thread.currentThread().isInterrupted());
        });
        thread.setDaemon(false);
        thread.start();

        Thread.sleep(3000);
        thread.interrupt();
        System.out.println("test over");
    }
}
