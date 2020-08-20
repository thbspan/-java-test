package com.test.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

    public static class FunnyPrint implements Runnable {
        private static final int DEFAULT_COUNT = 5;
        private final BlockingQueue<Integer> before;
        private final BlockingQueue<Integer> after;

        private final int max;

        public FunnyPrint(BlockingQueue<Integer> before, BlockingQueue<Integer> after, int max) {
            this.before = before;
            this.after = after;
            this.max = max;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int index = before.take();
                    if (index >= max) {
                        // 通知后续等待任务退出
                        after.offer(index);
                        break;
                    }
                    for (int i = 0; i < DEFAULT_COUNT; i++) {
                        System.out.printf("线程%s，打印%d\n", Thread.currentThread().getName(), ++index);
                    }
                    after.put(index);
                }
            } catch (InterruptedException e) {
                System.out.println("线程中断退出");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue1 = new SynchronousQueue<>();
        BlockingQueue<Integer> blockingQueue2 = new SynchronousQueue<>();
        BlockingQueue<Integer> blockingQueue3 = new SynchronousQueue<>();
        BlockingQueue<Integer> blockingQueue4 = new SynchronousQueue<>();
        BlockingQueue<Integer> blockingQueue5 = new SynchronousQueue<>();
        new Thread(new FunnyPrint(blockingQueue1, blockingQueue2, 1000), "1").start();
        new Thread(new FunnyPrint(blockingQueue2, blockingQueue3, 1000), "2").start();
        new Thread(new FunnyPrint(blockingQueue3, blockingQueue4, 1000), "3").start();
        new Thread(new FunnyPrint(blockingQueue4, blockingQueue5, 1000), "4").start();
        new Thread(new FunnyPrint(blockingQueue5, blockingQueue1, 1000), "5").start();
        blockingQueue1.put(0);

    }
}
