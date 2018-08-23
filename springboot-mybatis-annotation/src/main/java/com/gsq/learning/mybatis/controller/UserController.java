package com.gsq.learning.mybatis.controller;

import com.gsq.learning.mybatis.domain.User;
import com.gsq.learning.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findByName")
    public User findByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }
}
