package com.gsq.javalearning.springbootdemo;

import com.gsq.javalearning.springbootdemo.config.BookProperties;
import com.gsq.javalearning.springbootdemo.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2018/8/19.
 */
// SpringRunner 继承了 SpringJUnit4ClassRunner，并且没有做任何扩展
@RunWith(SpringRunner.class)
// 会加载整个 spring boot
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Autowired
    private BookProperties bookProperties;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testBookProperties() {
        Assert.assertEquals(bookProperties.getName(), "spring boot");
        Assert.assertEquals(bookProperties.getWriter(), "writer");
    }

    @Test
    public void testBookController() {
        System.out.println("userRepository = " + userRepository);
    }
}
