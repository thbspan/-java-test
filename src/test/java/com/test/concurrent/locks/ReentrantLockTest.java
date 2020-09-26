package com.test.concurrent.locks;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;


public class ReentrantLockTest {

    @Test
    public void testReentrant() {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 3; i++) {
            lock.lock();
        }

        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("unlock " + i);
            } finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void testFairLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);

        for (int i = 0; i < 5; i++) {
            new Thread(new FairThreadDemo(lock, i)).start();
        }

        // 仅仅为了让测试线程等待
        TimeUnit.SECONDS.sleep(5);
        //  公平锁：线程几乎是轮流获取到锁的
        //lock thread No：0
        //lock thread No：3
        //lock thread No：2
        //lock thread No：4
        //lock thread No：1
        //lock thread No：0
        //lock thread No：3
        //lock thread No：2
        //lock thread No：4
        //lock thread No：1

        //非公平锁：线程会重复获取锁
        //lock thread No：4
        //lock thread No：4
        //lock thread No：0
        //lock thread No：0
        //lock thread No：2
        //lock thread No：2
        //lock thread No：3
        //lock thread No：3
        //lock thread No：1
        //lock thread No：1
    }

    static class FairThreadDemo implements Runnable {
        int id;
        Lock lock;

        FairThreadDemo(Lock lock, int id) {
            this.lock = lock;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                try {
                    lock.lock();
                    System.out.println("lock thread No：" + id);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void testLockInterruptibly() throws InterruptedException {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        Thread thread = new Thread(new LockThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new LockThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread.start();
        thread1.start();
        thread.interrupt();//是第一个线程中断
        // 仅仅为了让测试线程等待
        TimeUnit.SECONDS.sleep(15);
    }

    static class LockThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;

        LockThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "正常结束!");
            }
        }
    }

    /**
     * 测试Condition：阻塞队列
     */
    @Test
    public void testCondition() throws InterruptedException {
        ConditionBlockingQueue<Integer> queue = new ConditionBlockingQueue<>(2);
        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.enqueue(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.dequeue();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    static class ConditionBlockingQueue<E> {
        int size;
        ReentrantLock lock = new ReentrantLock();
        Deque<E> list = new LinkedList<>();

        Condition fullCondition = lock.newCondition();
        Condition emptyCondition = lock.newCondition();

        public ConditionBlockingQueue(int size) {
            this.size = size;
        }

        public void enqueue(E e) throws InterruptedException {
            lock.lock();
            try {
                while (list.size() == size) {
                    fullCondition.await();
                }
                list.add(e);
                System.out.println("入队：" + e);
                emptyCondition.signal();
            } finally {
                lock.unlock();
            }
        }

        public E dequeue() throws InterruptedException {
            E e;
            lock.lock();
            try {
                while (list.size() == 0)//队列为空,在notEmpty条件上等待
                    emptyCondition.await();
                e = list.removeFirst();//出队:移除链表首元素
                System.out.println("出队：" + e);
                fullCondition.signal();//通知在notFull条件上等待的线程
                return e;
            } finally {
                lock.unlock();
            }
        }
    }
}
