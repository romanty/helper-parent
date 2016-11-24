package com.study.sort;

/**
 * TODO:
 * 堆排序算法的实现，以大顶堆为例
 *
 * @author yutong
 * @created 2016/4/25
 */
public class HeapSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        for (int i = arr.length / 2; i >= 0; i--) {
            heapAdjust(arr, i, arr.length - 1);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapAdjust(arr, 0, i - 1);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void heapAdjust(int[] arr, int start, int end) {
        int temp = arr[start];
        for (int i = 2 * start + 1; i <= end; i *= 2) {
            // 左右孩子的节点分别为2*i+1，2*i+2
            // 选择左右孩子较小的坐标
            if (i < end && arr[i] < arr[i + 1]) {
                i++;
            }
            if (temp >= arr[i]) {
                break;// 已经成大顶堆，=保持稳定性
            }
            arr[start] = arr[i]; // 将子节点上移
            start = i; // 下一轮筛选
        }
        arr[start] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {49,38,65,97,76,13,27,49};
        sort(arr);
    }
}
