package com.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static class CyclicPrint implements Runnable {
        private final CyclicBarrier headBarrier;
        private final CyclicBarrier tailBarrier;
        private final AtomicInteger index;
        private final int max;

        public CyclicPrint(CyclicBarrier headBarrier, CyclicBarrier tailBarrier, AtomicInteger index, int max) {
            this.headBarrier = headBarrier;
            this.tailBarrier = tailBarrier;
            this.index = index;
            this.max = max;
        }

        @Override
        public void run() {
            do {
                try {
                    headBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 3; i++) {
                    System.out.printf("%s：%d\n", Thread.currentThread().getName(), index.incrementAndGet());
                }
                try {
                    tailBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            } while (index.get() < max);
        }
    }

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        CyclicBarrier barrier1 = new CyclicBarrier(2);
        CyclicBarrier barrier2 = new CyclicBarrier(2);
        CyclicBarrier barrier3 = new CyclicBarrier(2);

        new Thread(new CyclicPrint(barrier1, barrier2, count, 3), "线程1").start();
        new Thread(new CyclicPrint(barrier2, barrier3, count, 6), "线程2").start();
        new Thread(new CyclicPrint(barrier3, barrier1, count, 9), "线程3").start();

        try {
            barrier1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
