package com.test.bytebuddy;

import org.junit.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWroldTest {

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        System.out.println(dynamicType);
        String str = dynamicType.newInstance().toString();

        System.out.println(str);
    }
}
