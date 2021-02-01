package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;
import java.util.function.Predicate;

class FluxFilter<T> extends Flux<T>{
    final Flux<? extends T> source;
    final Predicate<? super T> predicate;
    public FluxFilter(Flux<T> flux, Predicate<? super T> predicate) {
        this.source = flux;
        this.predicate = predicate;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        source.subscribe(new Flow.Subscriber<T>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onNext(T item) {
                if (predicate.test(item)) {
                    subscriber.onNext(item);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        });
    }
}
