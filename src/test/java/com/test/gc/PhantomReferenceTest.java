package com.test.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import org.junit.jupiter.api.Test;

public class PhantomReferenceTest {
    private static final int M = 1024 * 1024;

    private static void printlnMemory(String tag) {
        Runtime runtime = Runtime.getRuntime();
        int M = PhantomReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory() / M + "M(free)/" + runtime.totalMemory() / M + "M(total)");
    }

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

    @Test
    public void test2() {
        PhantomReferenceTest.printlnMemory("1.原可用内存和总内存");
        byte[] object = new byte[10 * PhantomReferenceTest.M];
        PhantomReferenceTest.printlnMemory("2.实例化10M的数组后");

        //建立虚引用
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object, referenceQueue);

        PhantomReferenceTest.printlnMemory("3.建立虚引用后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());
        // 释放 object 强引用
        object = null;
        PhantomReferenceTest.printlnMemory("4.执行object = null;强引用断开后");

        System.gc();
        PhantomReferenceTest.printlnMemory("5.GC后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

        //断开虚引用
        phantomReference = null;
        System.gc();
        PhantomReferenceTest.printlnMemory("6.断开虚引用后GC");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

    }
}
