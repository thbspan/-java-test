package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;
import java.util.function.Predicate;

class FluxFilter<T> extends Flux<T> {
    final Flux<? extends T> source;
    final Predicate<? super T> predicate;

    public FluxFilter(Flux<T> flux, Predicate<? super T> predicate) {
        this.source = flux;
        this.predicate = predicate;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        source.subscribe(new FilterSubscriber<>(subscriber, predicate));
    }

    static class FilterSubscriber<T> implements Flow.Subscriber<T>, Flow.Subscription {
        final Flow.Subscriber<? super T> subscriber;
        final Predicate<? super T> predicate;
        Flow.Subscription subscription;
        long index;
        long limit;

        FilterSubscriber(Flow.Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            this.subscriber = subscriber;
            this.predicate = predicate;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscriber.onSubscribe(this);
        }

        @Override
        public void onNext(T item) {
            if (predicate.test(item)) {
                index++;
                subscriber.onNext(item);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            subscriber.onError(throwable);
        }

        @Override
        public void onComplete() {
            if (index == limit) {
                subscriber.onComplete();
            }
        }

        @Override
        public void request(long n) {
            this.limit = n;
            subscription.request(n);
        }

        @Override
        public void cancel() {
            subscription.cancel();
        }
    }
}
