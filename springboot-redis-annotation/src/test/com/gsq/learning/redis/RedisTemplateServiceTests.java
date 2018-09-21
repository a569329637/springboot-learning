package com.gsq.learning.redis;

import com.gsq.learning.redis.service.RedisTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guishangquan
 * @date 2018/9/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateServiceTests {

    @Autowired
    private RedisTemplateService redisTemplateService;

    @Test
    public void testStringRedisTemplateOpsForValue() {
        redisTemplateService.stringRedisTemplateOpsForValue();
    }

    @Test
    public void testRedisTemplateOpsForValue() {
        redisTemplateService.redisTemplateOpsForValue();
    }
}
