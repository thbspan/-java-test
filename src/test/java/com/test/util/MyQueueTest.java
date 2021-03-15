package com.test.util;

import org.junit.jupiter.api.Test;

public class MyQueueTest {
    @Test
    public void test() {
        MyQueue<Integer> queue = new MyQueue<>();
        queue.push(1);
        queue.push(2);
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.isEmpty());
    }
}
