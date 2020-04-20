package com.test.junit.category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class A {

    @Category(SlowTests.class)
    @Test
    public void b() {
        System.out.println("A.b");
    }
}
