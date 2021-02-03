package com.test.reactor.core.pubisher;

import java.util.concurrent.Flow;

import org.junit.jupiter.api.Test;

public class FluxTest {
    @Test
    public void testFluxArray() {
        Flux.just(1, 2, 3, 4, 5)
                .map(i -> i * i)
                .filter(i -> i > 0)
                .flatMap(i -> Flux.just(i + 3, i + 6, i + 9))
                .subscribe(new Flow.Subscriber<>() {
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        System.out.println("onSubscribe");
                        // 订阅时请求n个元素，运行过程中可以修改；n<数组的长度，不会有完成事件
                        subscription.request(15);
                    }

                    @Override
                    public void onNext(Integer item) {
                        System.out.println("onNext:" + item);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("onError");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}
