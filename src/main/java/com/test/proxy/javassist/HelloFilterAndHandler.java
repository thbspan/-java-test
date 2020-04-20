package com.test.proxy.javassist;


import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;

public class HelloFilterAndHandler implements MethodFilter, MethodHandler {

    private Object target;

    public HelloFilterAndHandler(Object target) {
        this.target = target;
    }

    @Override
    public boolean isHandled(Method m) {
        return !m.getDeclaringClass().equals(Object.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        System.out.println(target);
        System.out.println(self);
        System.out.println("self object equals:" + (self == target));
        System.out.println("self :" + self);
        System.out.println(self.getClass());
        System.out.println(proceed);
        System.out.println(thisMethod);

//        return thisMethod.invoke(target,args);
        return proceed.invoke(self, args);
    }

}
