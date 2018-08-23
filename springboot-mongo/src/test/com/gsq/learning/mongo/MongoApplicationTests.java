package com.gsq.learning.mongo;

import com.gsq.learning.mongo.dao.UserDao;
import com.gsq.learning.mongo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(11);
        user.setName("guishangquan");
        user.setBirthday("2020-02-02");
        userDao.insert(user);
    }

    @Test
    public void testInsertAll() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; ++ i) {
            User user = new User();
            user.setAge(11 + i);
            user.setName("user_" + i);
            user.setBirthday("2020-02-02");
            users.add(user);
        }
        userDao.insertAll(users);
    }

    @Test
    public void testGetUser() {
        User user = userDao.getUser("5b7e85d004eaf0a8cc7bc28e");
        System.out.println("user = " + user);
    }

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        System.out.println("users = " + users);
        System.out.println("users.size() = " + users.size());
    }

    @Test
    public void testFindByPage() {
        Pageable pageable = PageRequest.of(0, 5);
        List<User> byPage = userDao.findByPage(pageable);
        System.out.println("byPage = " + byPage);
        System.out.println("byPage.size() = " + byPage.size());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId("5b7e85d004eaf0a8cc7bc28e");
        user.setAge(100);
        user.setName("user_update");
        user.setBirthday("2020-02-10");
        userDao.update(user);
    }

    @Test
    public void testRemove() {
        userDao.remove("5b7e85d004eaf0a8cc7bc28e");
    }
}
