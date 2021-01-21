package com.test.stream;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public interface MyStream<T> {

    /**
     * 映射lazy惰性求值
     */
    <R> MyStream<R> map(Function<T, R> mapper);

    /**
     * 扁平化 映射 lazy 惰性求值
     */
    <R> MyStream<R> flatMap(Function<T, ? extends MyStream<R>> mapper);

    /**
     * 过滤 lazy 惰性求值
     */
    MyStream<T> filter(Predicate<T> predicate);

    /**
     * 截断 lazy 惰性求值
     */
    MyStream<T> limit(int n);

    /**
     * 去重操作 lazy 惰性求值
     */
    MyStream<T> distinct();

    MyStream<T> peek(Consumer<? super T> action);

    void forEach(Consumer<? super T> action);

    <R> R reduce(R initVal, BiFunction<R, R, T> accumulator);

    <R, A> R collect(Collector<T, A, R> collector);

    T max(Comparator<T> comparator);

    T min(Comparator<T> comparator);

    boolean isEmptyStream();
    static <T> MyStream<T> empty() {
        return new MyStreamImpl.Builder<T>().isEnd(true).build();
    }
}
