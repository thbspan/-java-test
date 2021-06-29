package com.test.util;

import org.junit.jupiter.api.Test;

public class TopKTest {

    @Test
    public void test() {
        int[] data = {56, 275, 12, 6, 45, 478, 41, 1236, 456, 12, 546, 45};
        // 获取Top5
        int top = 5;
        int[] top5 = topK(data, top);
        for (int i = 0; i < top; i++) {
            System.out.println(top5[i]);
        }
    }

    private int[] topK(int[] data, int k) {
        int[] arrays = new int[k];
        System.arraycopy(data, 0, arrays, 0, k);
        MinHeap heap = new MinHeap(arrays);
        // 从k开始，遍历data
        for (int i = k; i < data.length; i++) {
            int root = heap.getRoot();

            // 当数据大于堆中最小的数（根节点）时，替换堆中的根节点，再转换成堆
            if (data[i] > root) {
                heap.setRoot(data[i]);
            }
        }
        return arrays;
    }
}
