package com.gsq.learning.starter;

/**
 * @author guishangquan
 * @date 2018/8/21
 */
public class ExampleService {
    private String prefix;
    private String suffix;

    public ExampleService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
