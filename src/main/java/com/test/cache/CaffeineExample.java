package com.test.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineExample {

    public static void main(String[] args) throws InterruptedException {
        LoadingCache<String, String> DES_CACHE = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS)
                .build(text -> text + " Hello " + System.currentTimeMillis());
        String key = "Peter";
        System.out.println(DES_CACHE.get(key));
        TimeUnit.SECONDS.sleep(5);
        System.out.println(DES_CACHE.get(key));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(DES_CACHE.get(key));
    }
}
