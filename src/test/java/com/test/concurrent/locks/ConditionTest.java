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
        Condition condition = lock.newCondition();
        int maxSize = 10;

        Producer producer = new Producer(queue, maxSize, lock, condition);
        Consumer consumer = new Consumer(queue, maxSize, lock, condition);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Producer implements Runnable {
    private Queue<String> queue;
    private int maxSize;
    private Lock lock;
    private Condition condition;

    public Producer(Queue<String> queue, int maxSize, Lock lock, Condition condition) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
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
                        condition.await();
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
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

class Consumer implements Runnable {
    private Queue<String> queue;
    private int maxSize;
    private Lock lock;
    private Condition condition;

    public Consumer(Queue<String> queue, int maxSize, Lock lock, Condition condition) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("The consumer queue is empty, please wait first");
                    try {
                        condition.await();
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
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}


