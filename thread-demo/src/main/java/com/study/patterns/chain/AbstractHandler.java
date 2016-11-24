package com.study.patterns.chain;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public abstract class AbstractHandler {
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
