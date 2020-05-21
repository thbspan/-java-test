package com.test.concurrent.queue;

import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

public class SynchronousQueueTest {

    @Test
    public void testOffer() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        System.out.println(queue.offer("A"));
        System.out.println(queue.offer("B"));
    }
}
