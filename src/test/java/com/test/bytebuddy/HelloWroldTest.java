package com.test.bytebuddy;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWroldTest {

    @Test
    public void test() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        System.out.println(dynamicType);
        String str = dynamicType.getDeclaredConstructor().newInstance().toString();

        System.out.println(str);
    }
}
