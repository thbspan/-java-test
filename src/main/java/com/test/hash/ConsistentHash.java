package com.test.hash;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ConsistentHash<T> {
    /**
     * hash函数接口
     */
    private final IHashService hashService;
    /**
     * 每个机器节点关联的虚拟节点数量
     */
    private final int numberOfReplicas;
    /**
     * 环形虚拟节点
     */
    private final NavigableMap<Long, T> circle = new TreeMap<>();

    public ConsistentHash(IHashService hashService, int numberOfReplicas, Collection<T> nodes) {
        this.hashService = hashService;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashService.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashService.hash(node.toString() + i));
        }
    }

    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashService.hash(key);
        Map.Entry<Long, T> entry = circle.ceilingEntry(hash);
        return entry != null ? entry.getValue() : circle.firstEntry().getValue();
    }
}
