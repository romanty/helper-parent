package com.study.thread.mutil;

import java.util.concurrent.RecursiveTask;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/18
 */
public class Calculator extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private int start;
    private int end;

    public Calculator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) < THRESHOLD) {
            for (int i = start; i<= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) >>> 1;

            Calculator left = new Calculator(start, middle);
            Calculator right = new Calculator(middle + 1, end);
            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }
}
