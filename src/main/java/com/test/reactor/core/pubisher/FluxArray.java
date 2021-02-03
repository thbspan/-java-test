package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;

class FluxArray<T> extends Flux<T> {
    private final T[] array;

    public FluxArray(T[] data) {
        this.array = data;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        try {
            subscriber.onSubscribe(new ArraySubscription<>(subscriber, array));
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }

    static final class ArraySubscription<T> implements Flow.Subscription {
        final Flow.Subscriber<? super T> subscriber;
        final T[] array;
        int index;
        boolean canceled;

        ArraySubscription(Flow.Subscriber<? super T> subscriber, T[] array) {
            this.subscriber = subscriber;
            this.array = array;
        }

        @Override
        public void request(long n) {
            if (canceled) {
                return;
            }
            final int length = array.length;
            for (int i = 0; i < n && index < length; i++) {
                subscriber.onNext(array[index++]);
            }
            if (index == length) {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            this.canceled = true;
        }
    }
}
