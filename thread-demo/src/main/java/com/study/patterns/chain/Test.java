package com.study.patterns.chain;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class Test {
    public static void main(String[] args) {
        MyHandler handler1 = new MyHandler("handler1");
        MyHandler handler2 = new MyHandler("handler2");
        MyHandler handler3 = new MyHandler("handler3");

        handler1.setHandler(handler2);
        handler2.setHandler(handler3);

        handler1.operation();
    }
}
