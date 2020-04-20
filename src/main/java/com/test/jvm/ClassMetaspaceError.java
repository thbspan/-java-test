package com.test.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ClassMetaspaceError {

    /**
     * 设置jvm参数
     * -Xms10M -Xmx10M -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
     */
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    public static class OOMObject {

    }
}
