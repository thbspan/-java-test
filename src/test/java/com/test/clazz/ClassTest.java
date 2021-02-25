package com.test.clazz;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ClassTest {

    @Test
    public void testPrimitive() throws ClassNotFoundException {
        Class<?> x = int.class;
        System.out.println(x.getName());
        System.out.println(x.getSimpleName());
        System.out.println(x.getTypeName());
        System.out.println(ClassTest[].class);
        System.out.println(Class.forName("[Lcom.test.clazz.ClassTest;"));
        System.out.println(x);
        System.out.println(int[].class);
        // 不支持jdk11
        // System.out.println(Reflection.getCallerClass());
        System.out.println(this.getClass().getClassLoader().loadClass("[I;"));
        // System.out.println(Reflection.getCallerClass().getClassLoader().loadClass("[I"));
        System.out.println(Thread.currentThread().getContextClassLoader().loadClass("[I"));
        System.out.println(ClassLoader.getSystemClassLoader().loadClass("[I"));
    }

    @Test
    public void testSigners() {
        System.out.println(Arrays.toString(this.getClass().getSigners()));
    }

    @Test
    public void testClassLoad() {
        new Child();
    }

    @Test
    public void testClassArrayNames() {
        // [I
        System.out.println(int[].class.getName());
        // [B
        System.out.println(byte[].class.getName());
        // [C
        System.out.println(char[].class.getName());
        // [J
        System.out.println(long[].class.getName());
    }
}
