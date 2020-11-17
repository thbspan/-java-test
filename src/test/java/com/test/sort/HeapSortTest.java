package com.test.sort;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class HeapSortTest {

    private static void adjustMaxHeap(int[] array, int k, int size) {
        int leftChild = (k << 1) + 1;
        int rightChild = (k << 1) + 2;
        if (leftChild < size) {
            int max = k;
            if (array[leftChild] > array[max]) {
                max = leftChild;
            }

            if (rightChild < size && array[rightChild] > array[max]) {
                max = rightChild;
            }

            if (max != k) {
                int tmp = array[max];
                array[max] = array[k];
                array[k] = tmp;
                adjustMaxHeap(array, max, size);
            }
        }
    }

    private static void buildMaxHeap(int[] array) {
        //从最后一个非叶子节点开始调整
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustMaxHeap(array, i, array.length);
        }
    }

    /**
     * 排序前先构建最大堆，然后将堆顶和最后一个位置的数据交换位置，重新调整0处的堆
     */
    private static void sort(int[] array) {
        buildMaxHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            int tmp = array[i];
            array[i] = array[0];
            array[0] = tmp;
            adjustMaxHeap(array, 0, i);
        }
    }

    @Test
    public void testSort() {
        int[] arr = {4,8,5,3,13,11,9,2,7,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
