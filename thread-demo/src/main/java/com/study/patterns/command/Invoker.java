package com.study.patterns.command;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action() {
        command.exec();
    }
}
