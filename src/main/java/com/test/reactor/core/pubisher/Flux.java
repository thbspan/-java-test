package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Flux<T> implements Flow.Publisher<T> {

    public abstract void subscribe(Flow.Subscriber<? super T> s);

    public <V> Flux<V> map(Function<? super T, ? extends V> mapper) {
        return new FluxMap<>(this, mapper);
    }

    public Flux<T> filter(Predicate<? super T> predicate) {
        return new FluxFilter<>(this, predicate);
    }

    @SafeVarargs
    public static <T> Flux<T> just(T... data) {
        return new FluxArray<>(data);
    }
}
