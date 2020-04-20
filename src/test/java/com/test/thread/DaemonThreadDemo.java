package com.test.thread;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import org.junit.Test;

public class DaemonThreadDemo {
    public static class MyDaemon implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程第" + i + "次执行");
            }
            Thread subThread = new Thread(() -> {
                // 守护线程中fork出的子线程依然是守护线程
                System.out.println("子线程执行,当前线程是否守护线程" + Thread.currentThread().isDaemon());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
//            subThread.setDaemon(false);
            subThread.start();
        }
    }

    @Test
    public void DaemonThreadTest() throws InterruptedException {
        Pattern pattern = Pattern.compile("^\\d{6}$");
        System.out.println(pattern.matcher("1234567").matches());
        Thread thread = new Thread(new MyDaemon());
        // setDaemon(true)设置线程为守护线程
        thread.setDaemon(true);
        thread.start();
        // isDaemon()方法查看当前线程是不是守护线程
        if (thread.isDaemon()) {
            System.out.println("当前执行的是守护线程");
        } else {
            System.out.println("当前执行的是非守护线程");
        }

        Thread.sleep(2000);
    }

}
