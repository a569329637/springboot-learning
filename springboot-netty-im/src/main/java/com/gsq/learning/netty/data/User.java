package com.gsq.learning.netty.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
@Setter
@Getter
@ToString
public class User {
    private String username;
    private String password;
    private String userId;
}
