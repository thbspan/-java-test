package com.test.cglib;

import org.junit.jupiter.api.Test;
import org.mockito.cglib.core.KeyFactory;

public class KeyFactoryTest {

    @Test
    public void testKeyCreate() {
        IntStringKey intStringKey = (IntStringKey) KeyFactory.create(IntStringKey.class);
        Object instance = intStringKey.newInstance(2, "34");
        System.out.println(instance);
    }
}
