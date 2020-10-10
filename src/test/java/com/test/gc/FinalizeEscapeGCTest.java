package com.test.gc;

import org.junit.jupiter.api.Test;

public class FinalizeEscapeGCTest {
    public static FinalizeEscapeGCTest SAVE_HOOK;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGCTest.SAVE_HOOK = this;
    }

    @Test
    public void test() throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGCTest();
        // 对象第一次尝试拯救自己
        saveSelf();
        // 第二次尝试拯救自己
        saveSelf();
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGCTest();
        // 对象第一次尝试拯救自己
        saveSelf();
        // 第二次尝试拯救自己
        saveSelf();
    }

    private static void saveSelf() throws InterruptedException {
        SAVE_HOOK = null;
        // 因为Finalizer方法优先级很低， 暂停一会儿， 以等待它
        Thread.sleep(1000L);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
