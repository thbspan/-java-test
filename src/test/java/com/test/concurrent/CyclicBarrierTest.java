package com.test.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class CyclicBarrierTest {

    @Test
    public void test() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        //final  CyclicBarrier cb = new CyclicBarrier(3);//创建CyclicBarrier对象并设置3个公共屏障点
        final CyclicBarrier cb = new CyclicBarrier(3, () -> System.out.println("********我最先执行***********"));
        for (int i = 0; i < 3; i++) {
            Runnable runnable = () -> {
                try {
                    Thread.sleep((long) (Math.random() * 10_000));
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                    cb.await();//到此如果没有达到公共屏障点，则该线程处于等待状态，如果达到公共屏障点则所有处于等待的线程都继续往下运行

                    Thread.sleep((long) (Math.random() * 10_000));
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                    cb.await();    //这里CyclicBarrier对象又可以重用
                    Thread.sleep((long) (Math.random() * 10_000));
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                    cb.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
        service.awaitTermination(30, TimeUnit.SECONDS);
    }
}
