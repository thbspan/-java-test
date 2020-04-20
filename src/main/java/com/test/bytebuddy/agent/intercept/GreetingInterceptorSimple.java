package com.test.bytebuddy.agent.intercept;

public class GreetingInterceptorSimple {

    public Object greet(Object argument) {
        return "Hello from " + argument;
    }
}
