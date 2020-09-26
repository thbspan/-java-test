package com.test.forkjoin;

import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 100;

    private final int start;
    private final int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        if (end - start <= THRESHOLD) {
            // 计算的任务已经足够小了
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 任务大于阈值，分裂成两个任务计算
            int middle = (start + end) / 2;

            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 合并子任务计算结果
            sum = leftTask.join() + rightTask.join();
        }

        return sum;
    }
}
