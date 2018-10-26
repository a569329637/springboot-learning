package com.gsq.learning.sharding;

import com.gsq.learning.sharding.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author guishangquan
 * @date 2018/10/17
 */
@SpringBootApplication
public class ShardingApplication {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(ShardingApplication.class, args)) {
            context.getBean(DemoService.class).demo();
        }
    }
}
