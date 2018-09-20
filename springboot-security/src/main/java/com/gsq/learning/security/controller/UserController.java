package com.gsq.learning.security.controller;

import com.gsq.learning.security.domain.UserEntity;
import com.gsq.learning.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/users")
    public String users(Model model) {
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
