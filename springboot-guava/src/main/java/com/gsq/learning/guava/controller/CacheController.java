package com.gsq.learning.guava.controller;

import com.google.common.cache.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author guishangquan
 * @date 2019-06-06
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    @GetMapping("test1")
    public Object test1() {

        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        System.out.println("cache.getIfPresent(1) = " + cache.getIfPresent(1));
        System.out.println("cache.getIfPresent(2) = " + cache.getIfPresent(2));
        return "Success";
    }

    @GetMapping("test2")
    public Object test2() throws ExecutionException {

        LoadingCache<Object, Object> cache = CacheBuilder.newBuilder().build(new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object key) throws Exception {
                System.out.println("key = " + key);
                return "key-" + key;
            }
        });
        cache.put(1, "a");
        System.out.println("cache.getIfPresent(1) = " + cache.getIfPresent(1));
        System.out.println("cache.getIfPresent(2) = " + cache.getIfPresent(2));

        // 如果有缓存则返回；否则运算、缓存、然后返回
        Object value3 = cache.get(2, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "hello";
            }
        });
        System.out.println("value3 = " + value3);
        return "Success";
    }

    @GetMapping("test3")
    public Object test3() {
        // 基于容量回收
        Cache<Object, Object> cache = CacheBuilder
                .newBuilder()
                .maximumSize(2)
                .build();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        System.out.println("cache.asMap() = " + cache.asMap());
        //cache.asMap() = {3=c, 2=b}
        System.out.println("cache.getIfPresent(2) = " + cache.getIfPresent(2));
        //cache.getIfPresent(2) = b
        cache.put(4, "d");
        System.out.println("cache.asMap() = " + cache.asMap());
        //cache.asMap() = {2=b, 4=d}
        return "Success";
    }

    @GetMapping("test4")
    public Object test4() {
        // 基于权重回收，偶数代表20权重，所以只能放5个，通过这种方式可以控制内存
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().maximumWeight(100)
                .weigher(new Weigher<Integer, Integer>() {
                    @Override
                    public int weigh(Integer key, Integer value) {
                        if (value % 2 == 0) {
                            return 20;
                        } else {
                            return 5;
                        }
                    }
                }).build();
        // 放偶数
        for (int i = 0; i <= 20; i += 2) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
        cache.invalidateAll();
        for (int i = 1; i < 10; i += 1) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
        return "Success";
    }

    @GetMapping("test5")
    public Object test5() {
        // 基于时间回收，过期时间固定
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
        cache.put(1,1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
        return "Success";
    }

    @GetMapping("test6")
    public Object test6() {
        // 基于时间回收
        // 没有过期，再次访问，重置过期时间
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS).build();
        cache.put(1,1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.getIfPresent(1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
        return "Success";
    }

    @GetMapping("test7")
    public Object test7() {
        // 手动清除
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.invalidate("1");
        cache.invalidateAll();
        return "Success";
    }

    @GetMapping("test8")
    @ResponseBody
    public Object test8() {

//        CacheBuilder.recordStats()用来开启Guava Cache的统计功能。统计打开后，Cache.stats()方法会返回对象以提供如下统计信息：
//        hitRate()：缓存命中率；
//        averageLoadPenalty()：加载新值的平均时间，单位为纳秒；
//        evictionCount()：缓存项被回收的总数，不包括显式清除

        Cache<Object, Object> cache = CacheBuilder.newBuilder().recordStats().build();
        CacheStats stats = cache.stats();
        System.out.println("stats = " + stats);
        return "Success";
    }
}
