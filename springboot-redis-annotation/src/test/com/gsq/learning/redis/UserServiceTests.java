package com.gsq.learning.redis;

import com.gsq.learning.redis.domain.User;
import com.gsq.learning.redis.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setId(123456L);
        user.setName("用户1");
        user.setAge(12);
        user.setBirthday("2018-02-03");
    }

    @Test
    public void testRedisAnnotation() {
        userService.save(user);

        System.out.println("1");
        User u1 = userService.findById(user.getId());
        System.out.println("u1 = " + u1);

        System.out.println("2");
        User u2 = userService.findById(user.getId());
        System.out.println("u2 = " + u2);

        System.out.println(3);
        user.setBirthday("2010-01-01");
        user.setAge(10);
        userService.update(user);
        User u3 = userService.findById(user.getId());
        System.out.println("u3 = " + u3);

        userService.delete(user);
    }

    @Test
    public void testChineseKey() {
        userService.insert(user);

        System.out.println("1");
        User u1 = userService.findByName(user.getName());
        System.out.println("u1 = " + u1);

        System.out.println("2");
        User u2 = userService.findByName(user.getName());
        System.out.println("u2 = " + u2);
    }

    @Test
    public void testNullCache() {
        String nullCache = userService.nullCache(11L);
        System.out.println("nullCache = " + nullCache);
    }

    @Test
    public void testKeyGenerator() {
        String keyGenerator = userService.keyGenerator(user);
        System.out.println("keyGenerator = " + keyGenerator);
    }
}
