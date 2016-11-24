package com.study.patterns.observer;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new MySubject();
        subject.add(new Observer1());
        subject.add(new Observer2());

        subject.operation();
    }
}
