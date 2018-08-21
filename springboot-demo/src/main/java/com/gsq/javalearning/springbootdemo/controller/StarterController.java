package com.gsq.javalearning.springbootdemo.controller;

import com.gsq.learning.starter.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2018/8/21
 */
@RestController
public class StarterController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("starter")
    public String test() {
        String warp = "starter";
        if (null != exampleService) {
            warp = exampleService.wrap(" TTTTTT ");
        }
        return warp;
    }
}
