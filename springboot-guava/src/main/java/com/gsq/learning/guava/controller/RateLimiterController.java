package com.gsq.learning.guava.controller;

import com.gsq.learning.guava.core.aspect.RateLimitAop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2019-06-05
 */
@RestController
@RequestMapping("ratelimiter")
public class RateLimiterController {

    @GetMapping("get")
    @RateLimitAop
    public Object get() {
        return "success";
    }

}
