package com.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class CompletionServiceTest {
    private static final ExecutorService EXECUTOR_SERVICE =
            new ThreadPoolExecutor(4, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static final CompletionService<Long> COMPLETION_SERVICE = new ExecutorCompletionService<>(EXECUTOR_SERVICE);

    static class Sum {

        /**
         * 多线程并发计算
         */
        public static long sum1(int min, int max) throws InterruptedException, ExecutionException {
            if (min > max) {
                return 0L;
            }
            List<Callable<Long>> tasks = new ArrayList<>();
            while (min < max) {
                final int left = min;
                final int right = max;
                //分割任务，每个任务最多只相加1000个数
                Callable<Long> task = () -> {
                    long sum = 0;
                    int r = Math.min((left + 1000), right);
                    for (int i = left; i < r; i++) {
                        sum += i;
                    }
                    return sum;
                };
                tasks.add(task);
                min += 1000;
            }
            for (Callable<Long> task : tasks) {
                COMPLETION_SERVICE.submit(task);
            }
            EXECUTOR_SERVICE.shutdown();
            long sum = 0;
            for (int i = 0, size = tasks.size(); i < size; i++) {
                sum += COMPLETION_SERVICE.take().get();
            }
            return sum;
        }

    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        System.out.println(Sum.sum1(1, 100_0000));
    }
}
