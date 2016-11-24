package com.study.patterns.chain;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/27
 */
public class MyHandler extends AbstractHandler implements Handler {

    private String name;

    public MyHandler(String name) {
        this.name = name;
    }

    @Override
    public void operation() {
        System.out.println(name + " deal");
        if (getHandler() != null) {
            getHandler().operation();
        }
    }
}
