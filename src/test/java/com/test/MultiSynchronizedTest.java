package com.test;

public class MultiSynchronizedTest {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Task1(), "task1");
        thread1.start();

        Thread thread2 = new Thread(new Task2(), "task2");
        thread2.start();
    }

    private static class Task1 implements Runnable {
        @Override
        public void run() {
            synchronized (lock1) {
                synchronized (lock2) {
                    try {
                        lock1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private static class Task2 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                lock1.notify();
            }
            synchronized (lock2) {
                System.out.println("Task2 obtain lock2");
            }

        }
    }
}
