package com.test.bytebuddy.agent.intercept;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class GreetingInterceptorComplex {

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments, @Origin Method method) {
        System.out.println("called:" + method.getName());
        if (allArguments != null && allArguments.length > 0) {
            return "Hello from " + Arrays.toString(allArguments);
        }
        return "Hello from " + method.getName();
    }
}
