package com.test.util;

import org.junit.jupiter.api.Test;

public class LRUCacheTest {

    @Test
    public void test() {
        LRUCache<Character, Integer> lru = new LRUCache<>(6);
        String string = "abcdefghijklmn";
        for (int i = 0, size = string.length(); i < size; i++) {
            lru.put(string.charAt(i), i);
        }
        System.out.println("lru" + lru);
        System.out.println("h: " + lru.get('h'));
    }
}
