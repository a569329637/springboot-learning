package com.gsq.learning.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guishangquan
 * @date 2018/8/21
 */
@ConfigurationProperties("example.service")
public class ExampleProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
