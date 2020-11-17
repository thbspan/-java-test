package com.test.sort;

import java.util.Arrays;

public class MaxHeap {
    private static final int INITIAL_CAPACITY = 16;
    private int[] queue = new int[INITIAL_CAPACITY];
    private int size;

    /**
     * 向堆中插入新元素
     */
    public void insert(int value) {
        int i = size;
        if (i >= queue.length) {
            grow();
        }
        size = i + 1;
        if (i == 0) {
            // 第一个元素
            queue[0] = value;
        } else {
            siftUpRecursion(i, value);
        }
    }

    /**
     * 删除堆中{@code index}处的节点
     * <br/>
     * 操作原理：当删除节点的数值时，原来的位置就会出现一个孔
     * 填充这个孔的方法就是：把最后的叶子的值赋给该孔，最后把该叶子删除
     */
    public void delete(int index) {
        int s = --size;
        // 最后的叶子
        int replacement = queue[s];
        // 删除最后的叶子(设置为初始值0)
        queue[s] = 0;
        if (s != index) { // 要删除的元素不是最后一个
            siftDownRecursion(index, replacement);
        }
    }

    private void siftDownRecursion(int index, int replacement) {
        int child;
        if ((index << 1 + 1) >= size) {
            // 没有左右孩子
            queue[index] = replacement;
        } else {
            if (index << 1 + 2 < size) {
                // 左右孩子都在
                // 左孩子索引
                child = index << 1 + 1;
                if (queue[child] < queue[child + 1]) {
                    // 取右孩子
                    child++;
                }
            } else {
                // 只有左孩子
                child = index << 2;
            }
            if (queue[child] > replacement) {
                queue[index] = queue[child];
                // 递归向下调用
                siftDownRecursion(child, replacement);
            }
        }
    }

    /**
     * 从底向上操作(递归)
     */
    private void siftUpRecursion(int index, int value) {
        if (index > 0) {
            // 父节点索引
            int parent = index / 2;

            int parentValue = queue[parent];

            // 如果父节点位置的数值比index位置的数值小，就交换二者的数值
            if (parentValue < value) {
                queue[index] = parent;
                siftUpRecursion(parent, value);
            } else {
                queue[index] = value;
            }
        }
    }

    private void grow() {
        int oldCapacity = queue.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // grow 50%
        if (newCapacity < 0) // overflow
            newCapacity = Integer.MAX_VALUE;
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private void swapValue(int i, int j) {
        int tmp = queue[i];
        queue[i] = queue[j];
        queue[j] = tmp;
    }
}
