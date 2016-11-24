package com.study.patterns.observer;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public interface Subject {

    void add(Observer observer);

    void del(Observer observer);

    void notifyObservers();

    void operation();
}
