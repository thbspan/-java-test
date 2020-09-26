package com.test.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import org.junit.jupiter.api.Test;

public class WeakReferenceTest {

    @Test
    public void test() throws InterruptedException {
        ReferenceQueue<Object> rq = new ReferenceQueue<>();
        Object obj = new Object();
        WeakReference<Object> wr = new WeakReference<>(obj, rq);
        obj = null;
        System.out.println(wr.get() != null);
        System.gc();
        System.out.println(wr.get() != null);

    }
}
