package com.study.patterns.observer;

import java.util.Enumeration;
import java.util.Vector;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public abstract class AbstractSubject implements Subject {
    private Vector<Observer> vector = new Vector<>();
    @Override
    public void add(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void del(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Enumeration<Observer> enumo = vector.elements();
        while (enumo.hasMoreElements()) {
            enumo.nextElement().update();
        }
    }

    @Override
    public abstract void operation();
}
