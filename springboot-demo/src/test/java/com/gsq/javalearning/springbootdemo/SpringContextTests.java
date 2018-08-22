package com.gsq.javalearning.springbootdemo;

import com.gsq.javalearning.springbootdemo.config.BookProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author guishangquan
 * @date 2018/8/21
 */
// 会加载 spring context 上下文
@RunWith(SpringJUnit4ClassRunner.class)
// 指定如何加载 spring 上下文，把 BookProperties 加入 spring context 上下文
@ContextConfiguration(classes = BookProperties.class)
// 指定如何加载 spring 上下文，把 applicationContext.xml 加入 spring context 上下文
// @ContextConfiguration(locations = "../applicationContext.xml")
// 指定如何加载 spring 上下文，把 spring1.xml 和 spring2.xml 加入 spring context 上下文
// @ContextConfiguration(locations = { "classpath*:/spring1.xml", "classpath*:/spring2.xml" })
public class SpringContextTests {

    @Autowired
    private BookProperties bookProperties;

    @Test
    public void test() {
        // 由于没有加载属性，所以 bookProperties = BookProperties{name='${demo.book.name}', writer='${demo.book.writer}'}
        System.out.println("bookProperties = " + bookProperties);
    }
}
