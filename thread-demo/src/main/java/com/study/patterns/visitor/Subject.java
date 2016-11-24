package com.study.patterns.visitor;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public interface Subject {
    void accept(Visitor visitor);

    String getSubject();
}
