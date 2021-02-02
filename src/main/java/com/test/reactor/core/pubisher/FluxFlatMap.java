package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;
import java.util.function.Function;

class FluxFlatMap<T, V> extends Flux<T> {
    final Flux<? extends V> source;
    final Function<? super V, ? extends Flow.Publisher<? extends T>> mapper;

    public FluxFlatMap(Flux<? extends V> source, Function<? super V, ? extends Flow.Publisher<? extends T>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        source.subscribe(new FlatMapSubscriber<>(source, mapper, subscriber));
    }

    static final class FlatMapSubscriber<T, V> implements Flow.Subscriber<V>, Flow.Subscription {
        final Flux<? extends V> source;
        final Function<? super V, ? extends Flow.Publisher<? extends T>> mapper;
        final Flow.Subscriber<? super T> subscriber;

        public FlatMapSubscriber(Flux<? extends V> source, Function<? super V, ? extends Flow.Publisher<? extends T>> mapper, Flow.Subscriber<? super T> subscriber) {
            this.source = source;
            this.mapper = mapper;
            this.subscriber = subscriber;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscriber.onSubscribe(new FlatMapSubscription<>(source, subscriber, subscription));
        }

        @Override
        public void onNext(V item) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void request(long n) {

        }

        @Override
        public void cancel() {

        }
    }

    static final class FlatMapSubscription<T, V> implements Flow.Subscription {
        final Flux<? extends V> source;
        final Flow.Subscriber<? super T> subscriber;
        final Flow.Subscription subscription;
        int index;
        boolean canceled;

        FlatMapSubscription(Flux<? extends V> source, Flow.Subscriber<? super T> subscriber, Flow.Subscription subscription) {
            this.source = source;
            this.subscriber = subscriber;
            this.subscription = subscription;
        }

        @Override
        public void request(long n) {
            if (canceled) {
                return;
            }
            subscription.request(n);
        }

        @Override
        public void cancel() {
            canceled = true;
            subscription.cancel();
        }
    }
}
