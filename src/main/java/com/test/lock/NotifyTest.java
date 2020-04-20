package com.test.lock;

import java.util.concurrent.TimeUnit;

public class NotifyTest {
    private final Object object;
    private final Object test;

    public NotifyTest(Object object) {
        this.object = object;
        this.test = new Object();
    }

    public NotifyTest(Object object, Object test) {
        this.object = object;
        this.test = test;
    }
    public void testWait() throws InterruptedException {
        synchronized (object) {
            test.wait();
        }

    }

    public void testNotify() {
        synchronized (object) {
            test.notify();
        }
    }

    public static void main(String[] args) {
        Object object = new Object();
        // wait notify对象没有锁定：报错
        NotifyTest test = new NotifyTest(object);
        // wait notify之前对象已经被锁定：正常
        // NotifyTest test = new NotifyTest(object, object);
        Thread t1 = new Thread(() -> {
            try {
                test.testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 exit");
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 start notify");
            test.testNotify();
        });
        t2.start();
    }
}
