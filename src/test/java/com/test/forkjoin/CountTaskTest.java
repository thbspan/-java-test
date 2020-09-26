package com.test.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.jupiter.api.Test;

public class CountTaskTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        CountTask countTask = new CountTask(1, 10000);
        ForkJoinTask<Integer> result = ((ForkJoinPool) Executors.newWorkStealingPool()).submit(countTask);
        System.out.println(result.get());
    }
}
