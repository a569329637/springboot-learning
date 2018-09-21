package com.gsq.learning.redis.service;

import com.gsq.learning.redis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/21
 */
@Service
public class RedisTemplateService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void stringRedisTemplateOpsForValue() {
        String key1 = "StringRedisTemplate:opsForValue1";
        String value1 = "StringRedisTemplate:opsForValue1";
        System.out.println(key1 + " -> " + value1);
        stringRedisTemplate.opsForValue().set(key1, value1);
        String v1 = stringRedisTemplate.opsForValue().get(key1);
        System.out.println(key1 + " == " + v1);

        // 中文
        String key2 = "StringRedisTemplate:opsForValue2";
        String value2 = "测试值-value";
        System.out.println(key2 + " -> " + value2);
        stringRedisTemplate.opsForValue().set(key2, value2);
        String v2 = stringRedisTemplate.opsForValue().get(key2);
        System.out.println(key2 + " == " + v2);
    }

    public void redisTemplateOpsForValue() {
        String key1 = "RedisTemplate:opsForValue1";
        String value1 = "RedisTemplate:opsForValue1";
        System.out.println(key1 + " -> " + value1);
        redisTemplate.opsForValue().set(key1, value1);
        String v1 = stringRedisTemplate.opsForValue().get(key1);
        System.out.println(key1 + " == " + v1);

        // 中文
        String key2 = "RedisTemplate:opsForValue2";
        String value2 = "测试值-value";
        System.out.println(key2 + " -> " + value2);
        redisTemplate.opsForValue().set(key2, value2);
        Object v2 = redisTemplate.opsForValue().get(key2);
        System.out.println(key2 + " == " + v2);

        // 对象
        String key3 = "RedisTemplate:opsForValue3";
        Map<String, Object> value3 = new HashMap<>();
        value3.put("key1", "value1");
        value3.put("key2", "值");
        value3.put("key3", 123456);
        value3.put("key4", 654321L);
        value3.put("key5", false);
        value3.put("key6", new Date());
        value3.put("key6", 123.98765f);
        System.out.println(key3 + " -> " + value3);
        redisTemplate.opsForValue().set(key3, value3);
        Object v3 = redisTemplate.opsForValue().get(key3);
        System.out.println(key3 + " == " + v3);

        // 对象
        String key4 = "RedisTemplate:opsForValue4";
        User value4 = new User();
        value4.setBirthday("2018-09-21");
        value4.setName("中文名");
        value4.setAge(12);
        value4.setId(90L);
        System.out.println(key4 + " -> " + value4);
        redisTemplate.opsForValue().set(key4, value4);
        Object v4 = redisTemplate.opsForValue().get(key4);
        System.out.println(key4 + " == " + v4);
    }
}
