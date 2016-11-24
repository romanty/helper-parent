package com.study.patterns.visitor;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class MySubject implements Subject {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getSubject() {
        return "love";
    }
}
