package com.study.sort;

/**
 * TODO:
 * 插入排序是通过找到合适位置插入元素来达到排序的目的的。
 * 从第二个元素开始遍历，和index位置往前比较，
 * @author yutong
 * @created 2016/4/25
 */
public class InsertSort {

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        for (int i = 1; i < arr.length; i++) { // 假设第一个元素位置正确
            int j = i;
            int target = arr[i]; // 待插入元素
            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = target;
        }
    }
}
