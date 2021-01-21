package com.test.stream;

@FunctionalInterface
public interface EvalFunction<T> {

    MyStream<T> apply();
}
