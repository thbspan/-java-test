package com.test.junit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JunitTest {

    @Test
    public void testAdd() {
        System.out.println(getExecutingMethodName());
    }

    @Test
    public void testDivide() {
        System.out.println(getExecutingMethodName());
    }

    @Test
    public void testMultiply() {
        System.out.println(getExecutingMethodName());
    }

    /**
     * 获取当前正在执行的方法名称
     */
    private static String getExecutingMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
