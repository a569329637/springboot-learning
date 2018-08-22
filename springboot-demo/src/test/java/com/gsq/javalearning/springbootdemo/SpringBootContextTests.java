package com.gsq.javalearning.springbootdemo;

import com.gsq.javalearning.springbootdemo.config.BookProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author guishangquan
 * @date 2018/8/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 不仅会加载 Spring 上下文，还会加载 外部属性（application.yml），以及 Spring boot 其他特性
@SpringBootTest(classes = BookProperties.class)
// Spring boot 较新的版本取消了该注解
// @SpringApplicationConfiguration(classes = BookProperties.class)
public class SpringBootContextTests {

    @Autowired
    private BookProperties bookProperties;

    // 容器只注入了 BookProperties，注入 userRepository 会报错
    // @Autowired
    // private UserRepository userRepository;

    @Test
    public void testBookProperties() {
        System.out.println("bookProperties = " + bookProperties);
        Assert.assertEquals(bookProperties.getName(), "spring boot");
        Assert.assertEquals(bookProperties.getWriter(), "writer");
    }


}
