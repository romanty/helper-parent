package com.study.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * TODO:
 *
 * @author yutong
 * @version 1.0
 * Created on 2016/4/15
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
