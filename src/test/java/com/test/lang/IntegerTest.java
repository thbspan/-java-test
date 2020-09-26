package com.test.lang;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

public class IntegerTest {

    @Test
    public void testBox() {
        Integer a = 1, b = 2;

        System.out.printf("swap before:a=%d,b=%d%s", a, b, System.lineSeparator());
        swapByReflect(a, b);
        System.out.printf("swap after:a=%d,b=%d%s", a, b, System.lineSeparator());
    }

    private void swap(Integer a, Integer b) {
        Integer tmp = a;
        a = b;
        b = tmp;
        System.out.printf("swap in:a=%d,b=%d%s", a, b, System.lineSeparator());
    }

    private void swapByReflect(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int tmp = a;
            // Integer tmp = new Integer(a);
            System.out.println("tmp:" + tmp);
            field.set(a, b);
            field.set(b, tmp);
            // field.setInt(b, tmp); 避免装箱操作
            // 使用tmp的自动装箱，输出 swap in:a=2,b=2？由于int包装的原因，Integer.value(tmp) == Integer.value(1) == 2，因为 field.set(a, b)；
            // 不使用自动装箱，且没有用到Integer缓存
            System.out.printf("swap in:a=%d,b=%d%s", a, b, System.lineSeparator());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
