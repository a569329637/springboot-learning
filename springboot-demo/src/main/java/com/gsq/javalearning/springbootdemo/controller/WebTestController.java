package com.gsq.javalearning.springbootdemo.controller;

import com.gsq.javalearning.springbootdemo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author guishangquan
 * @date 2018/8/22
 */
@Controller
@RequestMapping("/test")
public class WebTestController {

    @GetMapping("/getJson")
    @ResponseBody
    public User getJson() {
        User user = new User();
        user.setBirthday("2018-08-22");
        user.setName("a569329637");
        user.setAge(11);
        return user;
    }

    @PostMapping("/{id}")
    @ResponseBody
    public User postJson(@PathVariable(value = "id") String id, @RequestBody HashMap paramsMap) {
        System.out.println("id = " + id);
        System.out.println("paramsMap = " + paramsMap);
        System.out.println("paramsMap.get(\"a\") = " + paramsMap.get("a"));
        System.out.println("paramsMap.get(\"b\") = " + paramsMap.get("b"));
        System.out.println("paramsMap.get(\"c\") = " + paramsMap.get("c"));

        User user = new User();
        user.setId(123456L);
        user.setName("测试");
        return user;
    }

    @PostMapping("/post")
    public String postMethod(String a, String b, String c) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);

        return "redirect:/userList";
    }
}
