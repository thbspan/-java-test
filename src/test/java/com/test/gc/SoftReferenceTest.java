package com.test.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import org.junit.jupiter.api.Test;

public class SoftReferenceTest {

    @Test
    public void test() throws InterruptedException {
        ReferenceQueue<Object> rq = new ReferenceQueue<>();
        Object obj = new Object();
        SoftReference<Object> sf = new SoftReference<>(obj, rq);
        System.out.println(sf.get() != null);
        obj = null;
        System.gc();
        System.out.println(sf.get() != null);
        System.out.println(rq.remove(10_000));
    }
}
