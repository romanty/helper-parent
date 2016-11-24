package com.study.patterns.iterator;

/**
 * TODO:
 *
 * @author: yutong
 * @date: 2016/7/26
 */
public class Test {
    public static void main(String[] args) {
        Collection collection = new MyCollection();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
