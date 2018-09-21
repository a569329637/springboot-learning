package com.gsq.learning.security.controller;

import com.gsq.learning.security.domain.UserEntity;
import com.gsq.learning.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param user 注入当前登录的用户
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String users(@AuthenticationPrincipal User user, Model model) {
        System.out.println("当前登录的用户信息：");
        System.out.println("user = " + user);

        model.addAttribute("users", userService.getAll());
        model.addAttribute("title", "User List");
        return "users";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(UserEntity userEntity) {
        boolean ret = userService.insert(userEntity);
        if (ret) {
            return "redirect:";
        }
        return "redirect:register?error";
    }
}
