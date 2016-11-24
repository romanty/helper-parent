package com.study.thread.mutil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/18
 */
public class CalculatorTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new Calculator(1, 10000));
        try {
            System.out.println("result = [" + result.get() + "]");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
