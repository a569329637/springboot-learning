package com.gsq.learning.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guishangquan
 * @date 2018/8/21
 */
@Configuration
@ConditionalOnClass(ExampleService.class)
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleServiceAutoConfigure {

    @Autowired
    private ExampleProperties exampleProperties;

    @Bean
    @ConditionalOnMissingBean // 当 spring context 没有这个 Bean 时
    @ConditionalOnProperty(prefix = "example.service", value = "enable", havingValue = "true")
    public ExampleService getExampleService() {
        return new ExampleService(exampleProperties.getPrefix(), exampleProperties.getSuffix());
    }
}
