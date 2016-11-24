package com.study.patterns.observer;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public class Observer1 implements Observer {
    @Override
    public void update() {
        System.out.println("The observer1 had received.");
    }
}
