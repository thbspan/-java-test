package com.test.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class LinkedHashMapTest {

    @Test
    public void test() {
        Map<String, Object> map = new LinkedHashMap<>(8, 0.75f, true);
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        System.out.println(map);

//        map.put("3", "3.1");
        System.out.println(map.get("2"));
//        System.out.println(map.get("1"));
//        System.out.println(map.get("2"));
        map.forEach((key, value) -> System.out.println("Key:" + key + ", Value:" + value));
    }
}
