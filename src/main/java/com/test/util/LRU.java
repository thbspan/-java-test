package com.test.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU Cache
 */
public class LRU<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = -5385431870820642257L;

    private static final int MAX_ENTRIES = 6;
    public LRU(int initialCapacity,
               float loadFactor,
               boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当map中的数据量大于指定的缓存个数的时候，删除最老的数据
        return size() > MAX_ENTRIES;
    }
}
