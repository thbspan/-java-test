package com.test.concurrent;

/**
 * 伪共享测试
 * <p>
 * 参考 https://www.jianshu.com/p/defb9f9af5d3
 */
public class FalseShareTest implements Runnable {

    public static final int NUM_THREADS = 4;
    public static final long ITERATIONS = 50L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs;

    public static long SUM_TIME = 0L;

    public FalseShareTest(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            longs = new VolatileLong[NUM_THREADS];

            for (int j = 0; j < longs.length; j++) {
                longs[j] = new VolatileLong();
            }
            long start = System.nanoTime();
            runTest();
            long end = System.nanoTime();
            SUM_TIME += end - start;
        }
        System.out.println("avg cost time:" + SUM_TIME / 10 / 1000000.0 + " ms");
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseShareTest(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    /**
     * 64 bytes = 8 bytes (object reference) + 6 * 8 bytes (padded long) + 8 bytes (a long value)
     */
    static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;     // 注释此行，结果区别很大; 使用填充，会避免伪共享，速度更快
    }
}
