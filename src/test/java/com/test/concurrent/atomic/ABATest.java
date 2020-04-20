package com.test.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.Test;

public class ABATest {
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(100, 0);

    @Test
    public void testABA() throws InterruptedException {

        Thread intT1 = new Thread(() -> {
            atomicInt.compareAndSet(100, 101);
            atomicInt.compareAndSet(101, 100);
        });

        Thread intT2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean c3 = atomicInt.compareAndSet(100, 101);
            System.out.println(c3); // true
        });

        intT1.start();
        intT2.start();

        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            // 等待1s后获取stamp
            int stamp = atomicStampedRef.getStamp();
            atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
            atomicStampedRef.compareAndSet(101, 100, stamp, stamp + 1);
        });

        Thread refT2 = new Thread(() -> {
            // 先stamp再等待2s（使stamp值失效）
            int stamp = atomicStampedRef.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
            // false
            System.out.println(c3);
        });

        refT1.start();
        refT2.start();

        refT1.join();
        refT2.join();
    }
}
