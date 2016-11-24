package com.study.sort;

/**
 * TODO:
 * 选择排序，通过一次排序后将最小值放在数组最前面。冒泡排序是相邻元素比较和交换，选择排序则是整体选择，选出最小的直接和数组前面元素交换
 * @author yutong
 * @created 2016/4/25
 */
public class SelectSort {
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        int minIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    private static void swap(int[] arr, int i, int minIndex) {
        int temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
}
