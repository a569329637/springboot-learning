package com.gsq.javalearning.springbootdemo.controller;

import com.gsq.javalearning.springbootdemo.config.BookProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/8/19.
 */
@RestController
public class BookController {

    @Autowired
    BookProperties bookProperties;

    @GetMapping("/book")
    public String sayHello() {
        return "Hello， " + bookProperties.getWriter() + " is writing "
                + bookProperties.getName() + " ！";
    }
}
