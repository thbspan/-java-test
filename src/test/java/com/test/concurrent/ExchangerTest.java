package com.test.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    @Test
    public void testExchange() throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Producer("Producer", exchanger).start();
        new Consumer("Consumer", exchanger).start();
        TimeUnit.SECONDS.sleep(7);
    }

    static class Producer extends Thread {
        private final Exchanger<Integer> exchanger;

        Producer(String name, Exchanger<Integer> exchanger) {
            super(name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    int data = i;
                    System.out.println(getName() + " 交换前:" + data);
                    data = exchanger.exchange(data);
                    System.out.println(getName() + " 交换后:" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private final Exchanger<Integer> exchanger;

        Consumer(String name, Exchanger<Integer> exchanger) {
            super(name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            int data = 0;
            while (true) {
                System.out.println(getName() + " 交换前:" + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + " 交换后:" + data);
            }
        }
    }
}
