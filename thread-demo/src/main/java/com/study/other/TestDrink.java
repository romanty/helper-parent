package com.study.other;

/**
 * @author yutong on 2016/11/21.
 */
public class TestDrink {
    public static void main(String[] args) {
        int bots = count(10, 3, 0);
        System.out.println(bots);
        System.out.println(exchange(bots, 3, 0));
    }

    private static int exchange(int bots, int con, int count) {

        if (bots < con) {
            count = 0;
        } else if (bots == con) {
            count = 1;
        } else {
            if (bots / con + bots % con >= con) {
                count = bots / con + exchange(bots / con + bots % con, con, count);
            } else {
                count = bots / con;
            }
        }
        return count;
    }

    private static int count(int people, int con, int count) {
        int i = 0;
        while (true) {
            int total = i + exchange(i, con, count);
            if (total>=people) {
                break;
            }
            i ++;
        }
        return i;
    }
}
