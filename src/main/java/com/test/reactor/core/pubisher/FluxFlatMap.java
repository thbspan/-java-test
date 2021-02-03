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
        Flow.Subscription subscription;
        int index;
        boolean done;
        long limit;

        public FlatMapSubscriber(Flux<? extends V> source, Function<? super V, ? extends Flow.Publisher<? extends T>> mapper, Flow.Subscriber<? super T> subscriber) {
            this.source = source;
            this.mapper = mapper;
            this.subscriber = subscriber;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            // 继续向上传播
            subscriber.onSubscribe(this);
        }

        @Override
        public void onNext(V item) {
            if (done) {
                return;
            }
            Flow.Publisher<? extends T> publisher = mapper.apply(item);
            publisher.subscribe(new Flow.Subscriber<T>() {
                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    long n;
                    if ((n = limit - index) > 0) {
                        subscription.request(n);
                    } else {
                        done = true;
                    }
                }

                @Override
                public void onNext(T item) {
                    subscriber.onNext(item);
                    index++;
                }

                @Override
                public void onError(Throwable throwable) {
                    subscriber.onError(throwable);
                }

                @Override
                public void onComplete() {
                    // 由外层循环触发完成事件，这里内部循环的完成事件忽略
                }
            });
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
