package com.study.delayed;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author yutong on 2016/12/27.
 */
public class Cache<K, V> {

    private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());

    private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<>();

    private DelayQueue<DelayItem<Pair<K, V>>> q = new DelayQueue<>();

    private Thread daemonThread;

    public Cache() {
        Runnable daemonTask = new Runnable() {
            @Override
            public void run() {
                daemonCheck();
            }
        };

        daemonThread = new Thread(daemonTask);
        daemonThread.setDaemon(true);
        daemonThread.setName("Cache Daemon");
        daemonThread.start();
    }

    private void daemonCheck() {
        LOGGER.info("cache service started.");
        for (;;) {
            try {
                DelayItem<Pair<K, V>> delayItem = q.take();
                if (delayItem != null) {
                    //超时对象处理
                    Pair<K, V> pair =  delayItem.getItem();
                    LOGGER.info("cache key : " + pair.getFirst() + " is remove.");
                    cacheObjMap.remove(pair.getFirst(), pair.getSecond()); //compare and remove
                } else {
                    LOGGER.info("cache is null.");
                }
            } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
                break;
            }
        }
        LOGGER.info("cache service stopped.");
    }

    public void put(K key, V value, long time, TimeUnit unit) {
        V oldValue = cacheObjMap.put(key, value);
        if (oldValue != null) {
            q.remove(key);
        }

        long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
        q.put(new DelayItem<>(new Pair<>(key, value), nanoTime));
    }

    public V get(K key) {
        return cacheObjMap.get(key);
    }

    public V remove(K key) {
        V removeObj = cacheObjMap.remove(key);
        if (removeObj != null) {
            q.remove(key);
        }
        return removeObj;
    }

    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, String> cache = new Cache<>();
//        Cache<Integer, String> cache2 = new Cache<>();
        for (int i = 0; i < 100; i++) {
            cache.put(1 + i, "aaaa"+ i, 3 + i*2, TimeUnit.SECONDS);
        }
//        for (int i = 99; i >= 0; i--) {
//            cache2.put(i + 1, "aaaa"+ i, 3 + (99-i)*2, TimeUnit.SECONDS);
//        }

        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000 * 2);
            {
                String str = cache.get(1 + i);
                if (str == null) break;
                cache.remove(100-i);
                System.out.println(str + "， current count：" + cache.cacheObjMap.size());

//                String str2 = cache2.get(100 - i);
//                System.out.println(str2 + "， cache current count：" + cache2.cacheObjMap.size());
            }
        }
    }
}
