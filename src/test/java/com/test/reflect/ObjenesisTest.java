package com.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import sun.reflect.ReflectionFactory;

public class ObjenesisTest {

    @Test
    public void testInstanceClass() {
        Objenesis objenesis = new ObjenesisStd(true);
        InstanceExample example = objenesis.newInstance(InstanceExample.class);

        example.show();
    }

    /**
     * ObjenesisStd 底层实现
     * <br/>
     * 反序列化不调用构造函数实例化对象原理
     */
    @Test
    public void testInstanceClassOrigin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
        Constructor<Object> objectConstructor = Object.class.getConstructor((Class<?>[]) null);
        Constructor<?> constructor = reflectionFactory.newConstructorForSerialization(InstanceExample.class, objectConstructor);
        constructor.setAccessible(true);
        InstanceExample example = (InstanceExample) constructor.newInstance();
        example.show();
    }
}
