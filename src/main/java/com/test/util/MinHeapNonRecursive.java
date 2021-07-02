package com.test.util;

public class MinHeapNonRecursive {
    private final int[] data;

    public MinHeapNonRecursive(int[] data) {
        this.data = data;
        buildHeap();
    }

    private void buildHeap() {
        // 完全二叉树只有数组下标小于或等于 (data.length) / 2 - 1 的元素有孩子结点，遍历这些节点
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            // 对有孩子节点的元素heapify
            adjustHeap(i);
        }
    }

    private void adjustHeap(int k) {
        int smallestValue = data[k];
        for (int i = left(k); i < data.length; i = left(i)) {
            if (i + 1 < data.length && data[i] > data[i + 1]) {
                // 取右孩子节点的下标
                i++;
            }
            if (smallestValue <= data[i]) {
                break;
            } else {
                // 根节点 > 较小者
                data[k] = data[i];
                k = i;
            }
        }
        data[k] = smallestValue;
    }

    private int left(int i) {
        return ((i + 1) << 1) - 1;
    }

    /**
     * 获取对中的最小的元素，根元素
     */
    public int getRoot() {
        return data[0];
    }

    /**
     * 替换根元素，重新 heapify
     */
    public void setRoot(int root) {
        data[0] = root;
        adjustHeap(0);
    }
}
