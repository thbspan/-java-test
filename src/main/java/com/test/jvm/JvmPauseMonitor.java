package com.test.jvm;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * 能够监控jvm暂停时间
 */
public class JvmPauseMonitor implements Runnable {
    private static final long SLEEP_INTERVAL_MS = 500;
    private static final long WARN_THRESHOLD_MS = 200;

    @Override
    public void run() {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        while (true) {
            stopwatch.reset().start();
            try {
                Thread.sleep(SLEEP_INTERVAL_MS);
            } catch (InterruptedException ie) {
                return;
            }
            // 关键在这里，减去自己sleep的时间，大约等于系统暂停的时间（因为还有run方法执行其他代码的时间）
            long extraSleepTime = stopwatch.elapsed(TimeUnit.MILLISECONDS) - SLEEP_INTERVAL_MS;
            if (extraSleepTime > WARN_THRESHOLD_MS) {
                System.err.println("timeout exceeded");
            }
        }
    }
}
