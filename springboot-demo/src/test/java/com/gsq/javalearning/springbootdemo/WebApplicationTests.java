package com.gsq.javalearning.springbootdemo;

import com.gsq.javalearning.springbootdemo.controller.WebTestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author guishangquan
 * @date 2018/8/22
 */
@RunWith(SpringRunner.class)
// SpringBootTest 会注入 WebApplicationContext, SpringApplicationConfiguration 不会，需要 WebAppConfiguration 注解开启 web 上下文测试
@SpringBootTest(classes = SpringbootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    // BeforeClass 表示在类中的任意public static void方法执行之前执行
    // Before 表示在任意使用@Test注解标注的public void方法执行之前执行
    @Before
    public void setupMockMvc() {
        // 使用 Spring 来构建 mockMvc，可以包括一个或多个配置好的控制器，更接近真实环境
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        // 提供一个手动创建并配置的控制器
        //mockMvc = MockMvcBuilders
        //        .standaloneSetup(WebTestController.class)
        //        .build();
    }

    @Test
    public void testGetJson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test/getJson"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // get 请求，返回 json 数据
        String jsonString = mvcResult.getResponse().getContentAsString();
        System.out.println("jsonString = " + jsonString);
    }

    @Test
    public void testPostJson() throws Exception {
        // json 类型参数，返回 json 数据
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/test/{id}", 110)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"a\": 1, \"b\": \"abc\", \"c\": \"中文\"}")
                .accept(MediaType.APPLICATION_JSON);

        // 对返回数据进行校验
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("测试"))
                .andReturn();
    }

    @Test
    public void testPost() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/test/post")
                .param("a", "1")
                .param("b", "abc")
                .param("c", "中文");

        // 验证执行的控制器类型
        // 验证执行的控制器方法名
        // 验证视图
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.handler().handlerType(WebTestController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("postMethod"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userList"))
                .andReturn();

    }
}
