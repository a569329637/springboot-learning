package com.gsq.learning.shiro.auth.exception;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtException extends RuntimeException {

    private String type;

    public JwtException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
