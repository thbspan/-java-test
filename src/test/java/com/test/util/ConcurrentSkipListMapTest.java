package com.test.util;

import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.jupiter.api.Test;

/**
 * java 中内置的支持并发的跳跃表
 * 非并发情况下，可以使用TreeMap
 * 并发性相对较低时，可以使用 Collections.synchronizedSortedMap 包装 TreeMap
 * 并发程度低，数据量大时，ConcurrentHashMap 存取远大于ConcurrentSkipListMap
 * 数据量一定，并发程度高时，ConcurrentSkipListMap比ConcurrentHashMap效率更高
 */
public class ConcurrentSkipListMapTest {

    @Test
    public void test() {
        ConcurrentSkipListMap<String, Integer> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("Hello", 2);
        skipListMap.put("Jack", 4);
        skipListMap.put("java", 5);
        skipListMap.put("volatile", 5);
        System.out.println(skipListMap);
    }
}
