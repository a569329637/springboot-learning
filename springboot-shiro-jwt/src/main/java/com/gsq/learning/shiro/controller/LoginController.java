package com.gsq.learning.shiro.controller;

import com.gsq.learning.shiro.auth.subject.JwtSubject;
import com.gsq.learning.shiro.utils.JsonWebTokenUtil;
import com.gsq.learning.shiro.vo.Message;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/loginpage")
    public String loginpage() {
        return "this is login page";
    }

    @GetMapping("/logininfo")
    public Object logininfo() {
        Subject subject = SecurityUtils.getSubject();
        if (subject instanceof JwtSubject) {
            return ((JwtSubject) subject).getJwtAccount();
        }
        return "logininfo";
    }

    @PostMapping("/login")
    public Message login(String username, String password) {

        long period = 36000L; // 10 个小时
        String issuer = "token-server";
        String roles = "role1,role2";
        String perms = "user:create,user:update";

        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), username, issuer,
                period, roles, perms, SignatureAlgorithm.HS512);

        logger.info("登录成功 username={}, jwt={}", username, jwt);
        return new Message().ok(200, "成功").addData("jwt", jwt);
    }
}
