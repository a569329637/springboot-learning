package com.gsq.learning.redis.service;

import com.gsq.learning.redis.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Service
public class UserService {

    // 模拟数据库
    private HashMap<Long, User> userMap = new HashMap<>();

    public void save(User user) {
        userMap.put(user.getId(), user);
    }

    // sync 默认为 false, 当设置它为true时，只有一个线程的请求会去到数据库，其他线程都会等待直到缓存可用。这个设置可以减少对数据库的瞬间并发访问
    @Cacheable(value = "my_cache", sync = false, key = "'user:id:'+ #id")
    public User findById(Long id) {
        System.out.println("from database by id");
        return userMap.get(id);
    }

    // CachePut 需要有返回值, 直接更新 redis
    @CachePut(value = "my_cache", key = "'user:id:' + #user.id")
    public User update(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @CacheEvict(value = "my_cache", key = "'user:id:' + #user.id")
    public void delete(User user) {
        userMap.remove(user.getId());
    }

    // 模拟数据库
    private HashMap<String, User> userNameMap = new HashMap<>();

    @Cacheable(value = "my_cache", key = "'user:name:' + #name")
    public User findByName(String name) {
        System.out.println("from database by name");
        return userNameMap.get(name);
    }

    public void insert(User user) {
        userNameMap.put(user.getName(), user);
    }

    // 返回值为 null，不写 redis
    @Cacheable(value = "null_cache", key = "'id:' + #id", unless = "#result==null")
    public String nullCache(Long id) {
        return null;
    }

    // 通过方法生成 key
    @Cacheable(value = "key_gen", keyGenerator = "keyGenerator")
    public String keyGenerator(User user) {
        return "test";
    }
}
