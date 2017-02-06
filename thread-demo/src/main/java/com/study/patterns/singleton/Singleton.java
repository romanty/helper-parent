package com.study.patterns.singleton;

/**
 * 单例模式
 *
 * @author yutong on 2017/1/4.
 */
public class Singleton {

    private static Singleton instance = null;

    private Singleton() {

    }

//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

//    public static Singleton getInstance() {
//        if (instance == null) {
//            synchronized (instance) {
//                if (instance == null) {
//                    instance = new Singleton();
//                }
//            }
//        }
//        return instance;
//    }

    private static class SingletonFactory{
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonFactory.instance;
    }

    /*
     * 如果该对象被用于序列化，可以保证对象在序列化前后保持一致
     */
    public Object readResolve() {
        return instance;
    }
}
