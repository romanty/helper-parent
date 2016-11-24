package com.study.patterns.observer;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public class MySubject extends AbstractSubject {
    @Override
    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }
}
