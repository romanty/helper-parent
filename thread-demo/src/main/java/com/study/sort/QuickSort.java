package com.study.sort;


/**
 * TODO:
 * 快速排序，思路是右指针找比基准数小的，左指针找比基准数大的，交换之。交换后基数值左边都是小于基数，右边都大于基数
 * 然后分治，基数左边和的右边的再进行快速排序
 * @author yutong
 * @created 2016/4/25
 */
public class QuickSort {
    public static int partition(int[] arr, int left, int right) {
        int pivotKey = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= pivotKey) {
                right--;
            }
            arr[left] = arr[right]; //把小的移动到左边
            while (left < right && arr[left] <= pivotKey) {
                left++;
            }
            arr[right] = arr[left]; //把大的移动到右边
        }
        arr[left] = pivotKey;
        return left;
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right)
            return;
        int pivotPos = partition(arr, left, right);
        quickSort(arr, left, pivotPos - 1);
        quickSort(arr, pivotPos + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 6, 4};
        sort(arr);
    }
}
