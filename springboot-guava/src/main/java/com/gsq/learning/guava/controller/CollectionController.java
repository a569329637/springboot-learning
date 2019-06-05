package com.gsq.learning.guava.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-06-05
 */
@RestController
@RequestMapping("collection")
public class CollectionController {

    @GetMapping("immutable")
    public Object immutable() {

        // 不可以接收null值
        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c", "a");
        System.out.println("immutableSet = " + immutableSet);
        // 可以接收null值，对jdk集合类进行包装
        //Collections.unmodifiableSet();

        return "Success";
    }

    @GetMapping("multi")
    public Object multi() {
        // multimap
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("key1", "value1");
        multimap.put("key1", "value2");
        multimap.put("key1", "value2");
        multimap.put("key2", "value2");
        System.out.println("multimap = " + multimap);
        List<String> list = multimap.get("key1");
        System.out.println("list = " + list);

        // multiset
        HashMultiset<Object> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("a");
        System.out.println("multiset = " + multiset);
        System.out.println("multiset.size() = " + multiset.size());

//        HashMap HashMultiset
//        TreeMap TreeMultiset
//        LinkedHashMap LinkedHashMultiset
//        ConcurrentHashMap ConcurrentHashMultiset
//        ImmutableMap ImmutableMultiset

        return "Success";
    }
}
