package com.test.util;

public class MinHeap {

    private final int[] data;

    public MinHeap(int[] data) {
        this.data = data;
        buildHeap();
    }

    private void buildHeap() {
        // 完全二叉树只有数组下标小于或等于 (data.length) / 2 - 1 的元素有孩子结点，遍历这些节点
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            // 对有孩子节点的元素heapify
            heapify(i);
        }
    }

    private void heapify(int i) {
        // 获取左右节点的数组下标
        int l = left(i);
        int r = right(i);

        // 临时遍历，表示 根节点、左节点、右节点 中最小值的节点的下标
        int smallest = i;

        // 存在左节点
        if (l < data.length && data[l] < data[i]) {
            smallest = l;
        }
        // 存在右节点
        if (r < data.length && data[r] < data[smallest]) {
            smallest = r;
        }

        // 左右节点值都 > 根节点，直接return
        if (i == smallest) {
            return;
        }
        swap(i, smallest);

        // 替换后左右子树会被影响，对影响的子树 heapify
        heapify(smallest);
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private int left(int i) {
        return ((i + 1) << 1) - 1;
    }

    private int right(int i) {
        return (i + 1) << 1;
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
        heapify(0);
    }
}
