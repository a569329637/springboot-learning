package com.gsq.learning.mybatis.service;

import com.gsq.learning.mybatis.dao.UserDao;
import com.gsq.learning.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
