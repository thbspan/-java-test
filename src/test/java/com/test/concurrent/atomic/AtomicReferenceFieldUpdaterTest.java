package com.test.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtomicReferenceFieldUpdaterTest {

    @Test
    public void test() {
        Cat cat = new Cat();
        Assertions.assertEquals("cat1", cat.name);
        Cat.updater.compareAndSet(cat, cat.name, "cat2");
        Assertions.assertEquals("cat2", cat.name);
    }

    static class Cat {
        static final AtomicReferenceFieldUpdater<Cat, String> updater = AtomicReferenceFieldUpdater.newUpdater(Cat.class, String.class, "name");
        private volatile String name = "cat1";
    }
}
