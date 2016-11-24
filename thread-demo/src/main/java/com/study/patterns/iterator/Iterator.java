package com.study.patterns.iterator;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public interface Iterator {
    Object previous();

    Object next();

    boolean hasNext();

    Object fist();
}
