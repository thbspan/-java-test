package com.test.concurrent.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        Lock lock = new ReentrantLock();
        Condition putCondition = lock.newCondition();
        Condition takeCondition = lock.newCondition();
        int maxSize = 10;

        Producer producer = new Producer(queue, maxSize, lock, putCondition, takeCondition);
        Consumer consumer = new Consumer(queue, maxSize, lock, putCondition, takeCondition);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Producer implements Runnable {
    private final Queue<String> queue;
    private final int maxSize;
    private final Lock lock;
    private final Condition putCondition;
    private final Condition takeCondition;

    public Producer(Queue<String> queue, int maxSize, Lock lock, Condition putCondition, Condition takeCondition) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.putCondition = putCondition;
        this.takeCondition = takeCondition;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            i++;
            lock.lock();
            try {
                while (queue.size() == maxSize) {
                    System.out.println("The producer queue is full, please wait first");
                    try {
                        putCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("produce message：" + i);
                queue.add("Msg" + i);
                takeCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

class Consumer implements Runnable {
    private final Queue<String> queue;
    private final int maxSize;
    private final Lock lock;
    private final Condition putCondition;
    private final Condition takeCondition;

    public Consumer(Queue<String> queue, int maxSize, Lock lock, Condition putCondition, Condition takeCondition) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.putCondition = putCondition;
        this.takeCondition = takeCondition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("The consumer queue is empty, please wait first");
                    try {
                        takeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("consumed message：" + queue.remove());
                putCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}


