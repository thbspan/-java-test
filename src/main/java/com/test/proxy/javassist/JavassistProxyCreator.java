package com.test.proxy.javassist;

import com.test.proxy.ProxyCreator;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class JavassistProxyCreator<T extends MethodFilter & MethodHandler> implements ProxyCreator {

    private Object target;

    private T handler;

    public JavassistProxyCreator(Object target, T handler) {
        this.target = target;
        this.handler = handler;
    }

    @Override
    public Object getProxy() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(target.getClass());

        proxyFactory.setFilter(handler);
        Class<?> proxyClass = proxyFactory.createClass();
        Object instance = null;
        try {
            instance = proxyClass.newInstance();
            ((ProxyObject) instance).setHandler(handler);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }

}
