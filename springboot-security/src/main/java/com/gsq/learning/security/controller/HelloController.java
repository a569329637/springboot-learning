package com.gsq.learning.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/role")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String role() {
        return "role";
    }

}
