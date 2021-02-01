package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;
import java.util.function.Function;

class FluxMap<T, V> extends Flux<V> {
    final Flux<? extends T> source;
    final Function<? super T, ? extends V> mapper;

    public FluxMap(Flux<? extends T> source, Function<? super T, ? extends V> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super V> subscriber) {
        source.subscribe(new MapSubscriber<>(subscriber, mapper));
    }

    static final class MapSubscriber<T, V> implements Flow.Subscriber<T> {
        final Flow.Subscriber<? super V> subscriber;
        final Function<? super T, ? extends V> mapper;
        boolean done;

        public MapSubscriber(Flow.Subscriber<? super V> subscriber, Function<? super T, ? extends V> mapper) {
            this.subscriber = subscriber;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscriber.onSubscribe(new MapSubscription<>(subscription, mapper));
        }

        @Override
        public void onNext(T item) {
            if (done) {
                return;
            }
            subscriber.onNext(mapper.apply(item));
        }

        @Override
        public void onError(Throwable throwable) {
            if (done) {
                return;
            }
            done = true;
            subscriber.onError(throwable);
        }

        @Override
        public void onComplete() {
            if (done) {
                return;
            }
            done = true;
            subscriber.onComplete();
        }
    }

    static final class MapSubscription<T, V> implements Flow.Subscription {
        final Flow.Subscription subscription;
        final Function<? super T, ? extends V> mapper;

        MapSubscription(Flow.Subscription subscription, Function<? super T, ? extends V> mapper) {
            this.subscription = subscription;
            this.mapper = mapper;
        }

        @Override
        public void request(long n) {
            subscription.request(n);
        }

        @Override
        public void cancel() {
            subscription.cancel();
        }
    }
}
