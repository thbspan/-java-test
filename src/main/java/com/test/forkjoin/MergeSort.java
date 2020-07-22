package com.test.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSort {
    private final ForkJoinPool pool;

    public MergeSort(int parallelism) {
        pool = new ForkJoinPool(parallelism);
    }

    public void sort(int[] array) {
        pool.submit(new MergeSortTask(array, 0, array.length));
    }

    private static class MergeSortTask extends RecursiveAction {
        private static final int THRESHOLD = 8;
        private final int[] array;
        private final int low;
        private final int high;

        public MergeSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) {
                Arrays.sort(array, low, high);
            } else {
                int middle = low + ((high - low) >> 1);
                invokeAll(new MergeSortTask(array, low, middle), new MergeSortTask(array, middle, high));
                merge(middle);
            }
        }

        private void merge(int middle) {
            if (array[middle - 1] < array[middle]) {
                return;
            }
            int[] copy = new int[high - low];
            System.arraycopy(array, low, copy, 0, copy.length);

            // 索引做右边的值
            int copyHigh = high - low;
            // 右半部分的起点
            int copyMiddle = middle - low;
            for (int i = low, p = 0, q = copyMiddle; i < high; i++) {
                // q >= copyHigh :
                if (q >= copyHigh || (p < copyMiddle && copy[p] < copy[q])) {
                    array[i] = copy[p++];
                } else {
                    array[i] = copy[q++];
                }
            }
        }
    }
}
