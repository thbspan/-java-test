package com.test.util;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

public class PriorityQueueTest {

    @Test
    public void test() {
        Queue<Integer> queue = new PriorityQueue<>(8);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            queue.add(random.nextInt(100));
        }
        // 不是按照优先级顺序
        queue.forEach(System.out::println);
        for (int i = 0; i < 10; i++) {
            // 按优先级顺序取出元素
            System.out.print(queue.poll() + " ");
        }
        System.out.println();
    }
}
