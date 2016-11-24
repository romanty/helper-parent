package com.study.patterns.visitor;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class MyVisitor implements Visitor {
    @Override
    public void visit(Subject subject) {
        System.out.println("visit the subject = [" + subject.getSubject() + "]");
    }
}
