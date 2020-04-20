package com.test.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HelloInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if ("say".equals(method.getName())) {
            System.out.println("before say");
            // 调用父类中的方法
            Object result = methodProxy.invokeSuper(o, args);
            System.out.println("after say");
            return result;
        }
        return methodProxy.invokeSuper(o, args);
    }

}
