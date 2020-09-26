package com.test.guava;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterTest {

    @SuppressWarnings("UnstableApiUsage")
    @Test
    public void testAcquire() {
        System.out.println(TimeUnit.SECONDS.toMillis(1));
        // 大约每秒产生一个令牌
        RateLimiter limiter = RateLimiter.create(1);

        for(int i = 1; i < 5; i = i+1 ) {
            // limiter.tryAcquire()
            // 以阻塞方式获取令牌，未获取到会sleep
            double waitTime = limiter.acquire(i);
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
        }
    }
}
