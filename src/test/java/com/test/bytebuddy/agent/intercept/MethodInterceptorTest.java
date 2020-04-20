package com.test.bytebuddy.agent.intercept;

import java.util.function.Function;

import org.junit.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MethodInterceptorTest {

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void simpleTest() throws IllegalAccessException, InstantiationException {
        Class<? extends Function> dynamicType = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptorSimple()))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().apply("Byte Buddy"));
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void complexTest() throws IllegalAccessException, InstantiationException {
        Class<? extends Function> dynamicType = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptorComplex()))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().apply("Byte Buddy"));
    }

    @Test
    public void complex2Test() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(MethodDelegation.to(new GreetingInterceptorComplex()))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().toString());
    }
}
