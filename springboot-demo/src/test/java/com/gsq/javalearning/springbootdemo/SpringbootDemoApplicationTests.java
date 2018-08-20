package com.gsq.javalearning.springbootdemo;

import com.gsq.javalearning.springbootdemo.config.BookProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2018/8/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Autowired
    private BookProperties bookProperties;

    @Test
    public void testBookProperties() {
        Assert.assertEquals(bookProperties.getName(), "spring boot");
        Assert.assertEquals(bookProperties.getWriter(), "writer");
    }
}
