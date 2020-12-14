package com.test.util;

import org.junit.jupiter.api.Test;

public class LFUCacheTest {

    @Test
    public void test() {
        LFUCache lFUCache = new LFUCache(2);
        lFUCache.put(1, 1);
        lFUCache.put(2, 2);

        System.out.println(lFUCache.get(1));      // 返回 1
        lFUCache.put(3, 3);   // 去除键 2
        System.out.println(lFUCache.get(2));      // 返回 -1（未找到）
        System.out.println(lFUCache.get(3));      // 返回 3
        lFUCache.put(4, 4);   // 去除键 1
        System.out.println(lFUCache.get(1));      // 返回 -1（未找到）
        System.out.println(lFUCache.get(3));      // 返回 3
        System.out.println(lFUCache.get(4));      // 返回 4
    }
}
