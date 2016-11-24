package com.study.patterns.command;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class MyCommand implements Command {
    private Receiver receiver;

    public MyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exec() {
        receiver.action();
    }
}
