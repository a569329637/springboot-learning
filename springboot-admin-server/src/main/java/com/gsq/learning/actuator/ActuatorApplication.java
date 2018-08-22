package com.gsq.learning.actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author guishangquan
 * @date 2018/8/22
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class ActuatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class);
    }
}
