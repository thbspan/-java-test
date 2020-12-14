package com.test.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache {
    private final int capacity;
    private int minFreq;

    private final Map<Integer, Node> cache;
    private final Map<Integer, LinkedHashSet<Node>> freqMap;

    public LFUCache(int capacity) {
        this.minFreq = 0;
        this.capacity = capacity;
        cache = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        } else {
            freqInc(node);
            return node.val;
        }
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        Node node = cache.get(key);
        if (node != null) {
            node.val = value;
            freqInc(node);
        } else {
            if (cache.size() == capacity) {
                Node deadNode = removeNode();
                cache.remove(deadNode.key);
            }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
        }
    }

    private void freqInc(Node node) {
        // 从原freq对应的链表里移除, 并更新min
        int freq = node.freq;
        LinkedHashSet<Node> set = freqMap.get(freq);
        set.remove(node);
        if (freq == minFreq && set.isEmpty()) {
            minFreq = freq + 1;
        }
        // 加入新freq对应的链表
        node.freq++;
        freqMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(node);
    }

    private void addNode(Node newNode) {
        freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(newNode);
        minFreq = 1;
    }

    private Node removeNode() {
        LinkedHashSet<Node> nodes = freqMap.get(minFreq);
        Node deadNode = nodes.iterator().next();
        nodes.remove(deadNode);
        return deadNode;
    }
}

class Node {
    int key;
    int val;
    int freq = 1;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}