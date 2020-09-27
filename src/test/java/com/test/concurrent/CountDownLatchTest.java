package com.test.concurrent;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

public class CountDownLatchTest {
    static class BossThread extends Thread {
        private final CountDownLatch countDownLatch;

        BossThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Boss is in waiting room. All have " + countDownLatch.getCount() + " people");
            try {
                //Boss等待
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Everyone is here. The meeting begins!");
        }
    }

    static class EmployeeThread extends Thread {
        private final CountDownLatch countDownLatch;

        EmployeeThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.printf("Staff %s arrived in the meeting room\n", getName());
            countDownLatch.countDown();
        }
    }

    @Test
    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        new BossThread(countDownLatch).start();

        for (int i = 0; i < countDownLatch.getCount(); i++) {
            EmployeeThread employeeThread = new EmployeeThread(countDownLatch);
            employeeThread.setName(Character.toString((char) ('A' + i)));
            employeeThread.start();
        }
    }
}
