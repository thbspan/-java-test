package com.test;

import org.junit.Test;

public class AssertTest {

    @Test
    public void test() {
        boolean isSafe = true;
        assert isSafe : "not safe";
        System.out.println("assert test");
    }
}
