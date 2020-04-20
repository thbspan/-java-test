package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyCreator implements ProxyCreator, InvocationHandler {
    private Object target;

    public JdkProxyCreator(Object target) {
        assert target != null;
        // 没有判断父类是否实现了某个接口
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new IllegalArgumentException("target class don`t implement any interface");
        }
        this.target = target;
    }

    @Override
    public Object getProxy() {
        Class<?> clazz = target.getClass();
        // 生成代理对象
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method " + method + " invoke before");
        Object result = method.invoke(target, args);
        System.out.println("method " + method + " invoke after");
        return result;
    }
}
