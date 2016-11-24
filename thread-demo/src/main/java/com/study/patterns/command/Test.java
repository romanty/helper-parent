package com.study.patterns.command;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class Test {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        MyCommand command = new MyCommand(receiver);
        Invoker invoker = new Invoker(command);

        invoker.action();
    }
}
