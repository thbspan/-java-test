package com.test.clazz;

import java.util.Arrays;

import org.junit.Test;

public class ClazzTest {

    @Test
    public void testPrimitive() throws ClassNotFoundException {
        Class<?> x = int.class;
        System.out.println(x.getName());
        System.out.println(x.getSimpleName());
        System.out.println(x.getTypeName());
        System.out.println(ClazzTest[].class);
        System.out.println(Class.forName("[Lcom.test.clazz.ClazzTest;"));
        System.out.println(x);
        System.out.println(int[].class);
//        System.out.println(Reflection.getCallerClass());
//        System.out.println(this.getClass().getClassLoader().loadClass("[I;"));
//        System.out.println(Reflection.getCallerClass().getClassLoader().loadClass("[I"));
//        System.out.println(Thread.currentThread().getContextClassLoader().loadClass("[I"));
//        System.out.println(ClassLoader.getSystemClassLoader().loadClass("[I"));
    }

    @Test
    public void testSigners() {
        System.out.println(Arrays.toString(this.getClass().getSigners()));
    }
}
