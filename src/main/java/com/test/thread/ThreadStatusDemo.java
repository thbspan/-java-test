package com.test.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStatusDemo {

    public static void main(String[] args) {
        Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "waiting-thread-object").start();

        new Thread(LockSupport::park, "waiting-thread-parking").start();

        new Thread(() -> LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(200)), "timed-waiting-thread-parking").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "timed-waiting-thread-sleep").start();

        BlockingClass blockingClass = new BlockingClass();
        new Thread(blockingClass::test, "blocked-thread-1").start();
        new Thread(blockingClass::test, "blocked-thread-2").start();

        new Thread(blockingClass::testLock, "locked-waiting-thread-1").start();
        new Thread(blockingClass::testLock, "locked-waiting-thread-2").start();
    }

    static class BlockingClass {

        private ReentrantLock lock = new ReentrantLock();

        public void test() {
            synchronized (this) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void testLock() {
            try {
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
