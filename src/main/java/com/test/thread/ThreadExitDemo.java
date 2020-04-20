package com.test.thread;

import java.util.concurrent.TimeUnit;

public class ThreadExitDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Runtime.getRuntime().exit(0);
            // 同样会调用上面的方法
//            System.exit(0);

        }).start();
    }
}
