package com.gsq.javalearning.springbootdemo.service.impl;

import com.gsq.javalearning.springbootdemo.domain.User;
import com.gsq.javalearning.springbootdemo.repository.UserRepository;
import com.gsq.javalearning.springbootdemo.service.UserService;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/20
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.logger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        logger.info("查找所有用户");
        return userRepository.findAll();
    }

    @Override
    public User insertByUser(User user) {
        logger.info("新增用户：" + user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        logger.info("编辑用户：" + user);
        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        logger.info("删除用户：" + id);
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        logger.info("查找用户：" + id);
        return userRepository.findById(id).get();
    }
}
