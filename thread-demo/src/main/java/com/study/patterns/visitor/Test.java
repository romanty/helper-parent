package com.study.patterns.visitor;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class Test {
    public static void main(String[] args) {
        Visitor visitor = new MyVisitor();
        Subject subject = new MySubject();
        subject.accept(visitor);
    }
}
