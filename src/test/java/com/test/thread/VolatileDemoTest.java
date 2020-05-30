package com.test.thread;

public class VolatileDemoTest {

    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
//                System.out.println();

//                try {
//                    Thread.sleep(0);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                synchronized (VolatileDemoTest.class) {

                }
            }
        });
        t.start();
        Thread.sleep(1000);
        stop = true;
    }
}
