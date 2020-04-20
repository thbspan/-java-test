package com.test.bytebuddy.agent;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class AgentToString {

    /**
     * java agent premain 函数
     */
    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.isAnnotatedWith(AgentAnnotation.class))
                .transform((builder, typeDescription, classLoader, module) -> builder.method(named("toString"))
                        .intercept(FixedValue.value("transformed"))).installOn(instrumentation);
    }
}
