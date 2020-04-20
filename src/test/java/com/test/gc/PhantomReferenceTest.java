package com.test.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import org.junit.Test;

public class PhantomReferenceTest {

    @Test
    public void test() {
        ReferenceQueue<Object> rq = new ReferenceQueue<>();
        Object obj = new Object();
        PhantomReference<Object> pr = new PhantomReference<>(obj, rq);
        System.out.println(pr.get());
        obj = null;
        System.gc();
        System.out.println(pr.get());
        @SuppressWarnings("unchecked")
        Reference<Object> r = (Reference<Object>) rq.poll();
        if (r != null) {
            System.out.println("test");
        }
    }
}
