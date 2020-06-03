package com.test.util;

import org.junit.Test;

public class LRUTest {

    @Test
    public void test() {
        LRU<Character, Integer> lru = new LRU<>(16, 0.75f, true);

        String string = "abcdefghijklmn";
        for (int i = 0, size = string.length(); i < size; i++) {
            lru.put(string.charAt(i), i);
        }
        System.out.println("lru" + lru);
        System.out.println("h: " + lru.get('h'));
    }
}
