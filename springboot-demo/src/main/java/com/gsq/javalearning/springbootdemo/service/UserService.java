package com.gsq.javalearning.springbootdemo.service;

import com.gsq.javalearning.springbootdemo.domain.User;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/20
 */
public interface UserService {

    List<User> findAll();

    User insertByUser(User user);

    User update(User user);

    User delete(Long id);

    User findById(Long id);
}
