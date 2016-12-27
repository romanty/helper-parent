package com.study.delayed;

/**
 * @author yutong on 2016/12/27.
 */
public class Pair<K, V> {
    private K first;
    private V second;

    public Pair() {}

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }
}
