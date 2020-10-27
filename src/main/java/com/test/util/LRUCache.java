package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int cacheSize;

    private final Map<K, Entry<K, V>> cache;

    /**
     * 头元素
     */
    private Entry<K, V> first;
    /**
     * 尾元素
     */
    private Entry<K, V> last;

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new HashMap<>();
    }

    public void put(K key, V value) {
        // 判断是否存在该值
        Entry<K, V> entry = getEntry(key);

        if (entry == null) {
            if (cache.size() >= cacheSize) {
                cache.remove(key);
                removeLast();
            }
            entry = new Entry<>();
            entry.key = key;
        }
        entry.value = value;
        moveToFirst(entry);
        cache.put(key, entry);
    }

    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null) return null;
        moveToFirst(entry);
        return entry.value;
    }

    public void remove(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            if (entry.pre != null) {
                entry.pre.next = entry.next;
            }
            if (entry.next != null) {
                entry.next.pre = entry.pre;
            }
            if (entry == first) {
                first = entry.next;
            }
            if (entry == last) {
                last = entry.pre;
            }
        }
        cache.remove(key);
    }

    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    private void moveToFirst(Entry<K, V> entry) {
        if (entry == first) {
            return;
        }
        if (entry.pre != null) {
            entry.pre.next = entry.next;
        }
        if (entry.next != null) {
            entry.next.pre = entry.pre;
        }
        if (first == null || last == null) {
            first = last = entry;
            return;
        }

        entry.next = first;
        first.pre = entry;
        first = entry;
        entry.pre = null;
    }

    private Entry<K, V> getEntry(K key) {
        return cache.get(key);
    }

    static class Entry<K, V> {
        public Entry<K, V> pre;
        public Entry<K, V> next;
        public K key;
        public V value;
    }
}
