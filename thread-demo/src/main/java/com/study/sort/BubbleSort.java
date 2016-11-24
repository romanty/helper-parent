package com.study.sort;

/**
 * TODO:
 * 冒泡排序，比较两个相邻元素，小的放前面。从数组尾开始便利，一次排序的结果为第一个最小。N次排序后结束
 *
 * @author yutong
 * @created 2016/4/25
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j-1]) {
                    swap(arr, j-1, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[j] = temp;
        arr[i] = arr[j];
    }
}
