package com.study.patterns.iterator;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public interface Collection {

    Iterator iterator();

    Object get(int i);

    int size();
}
