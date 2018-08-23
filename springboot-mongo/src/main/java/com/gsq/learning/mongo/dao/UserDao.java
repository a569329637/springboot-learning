package com.gsq.learning.mongo.dao;

import com.gsq.learning.mongo.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
public interface UserDao {
    List<User> findAll();

    User getUser(String id);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(String id);

    List<User> findByPage(Pageable pageable);
}
