package com.gsq.learning.mq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(RocketMqProperties.PREFIX)
public class RocketMqProperties {
    public static final String PREFIX = "rocketmq";
    private String namesrvAddr;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }
}
